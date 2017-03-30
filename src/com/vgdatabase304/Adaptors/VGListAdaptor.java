package com.vgdatabase304.Adaptors;

import com.vgdatabase304.Exceptions.*;
import com.vgdatabase304.Structures.*;
import com.vgdatabase304.Utils.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VGListAdaptor {

    private static Statement stmt;
    private static ResultSet rs;

    public static VGList addListToDatabase(String listName, RegisteredUser user) throws SQLException {
        stmt = ConnectionManager.getStatement();
        rs = stmt.executeQuery("SELECT Max(LISTID) FROM LIST");
        int listID = 0;
        if (rs.next()) {
            listID = rs.getInt(1);
        }
        listID++;

        String sql = "INSERT INTO LIST (listID, name, username) VALUES (" +
                listID + ", '" +
                listName + "', '" +
                user.getUsername() + "'";
        stmt.executeUpdate(sql);
        return new VGList(listID);
    }

    public static void removeListFromDatabase(VGList list) throws SQLException {
        stmt = ConnectionManager.getStatement();
        String sql = "DELETE FROM LIST WHERE " +
                "LISTID=" + list.getListID();
        stmt.executeUpdate(sql);
    }

    public static void setListID(VGList list, int listID) throws SQLException {
        stmt = ConnectionManager.getStatement();
        stmt.executeUpdate("UPDATE LIST " +
                "SET LISTID=" + listID +
                " WHERE LISTID=" + list.getListID()
        );
        list.setListID(listID);
    }

    public static String getListName(VGList list) throws SQLException {
        stmt = ConnectionManager.getStatement();
        String sql = "SELECT NAME FROM LIST WHERE LISTID=" + list.getListID();
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            return rs.getString(1);
        } else {
            throw new InstanceNotFoundException("No record found in LIST for " + list.getListID());
        }
    }

    public static void setListName(VGList list, String listName) throws SQLException {
        stmt = ConnectionManager.getStatement();
        stmt.executeUpdate("UPDATE LIST " +
                "SET NAME='" + listName +
                "' WHERE listID=" + list.getListID()
        );
    }

    public static RegisteredUser getUserName(VGList list) throws SQLException {
        stmt = ConnectionManager.getStatement();
        String sql = "SELECT USERNAME FROM LIST WHERE LISTID=" + list.getListID();
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            return new RegisteredUser(rs.getString(1));
        } else {
            throw new InstanceNotFoundException("No record found in LIST for " + list.getListID());
        }
    }

    public static void setUserName(VGList list, RegisteredUser userName) throws SQLException {
        stmt = ConnectionManager.getStatement();
        stmt.executeUpdate("UPDATE LIST " +
                "SET USERNAME='" + userName.getUsername() +
                "' WHERE listID=" + list.getListID()
        );
    }

    public static List<VGList> getAllListsByUser(RegisteredUser user) throws SQLException {
        stmt = ConnectionManager.getStatement();
        List<VGList> listOfVGLists = new ArrayList<>();
        rs = stmt.executeQuery("SELECT LISTID FROM LIST WHERE USERNAME='" + user.getUsername() + "'");
        while (rs.next()) {
            listOfVGLists.add(new VGList(rs.getInt("LISTID")));
        }
        if (listOfVGLists.size() > 0) {
            return listOfVGLists;
        } else {
            throw new InstanceNotFoundException("No lists found for user " + user.getUsername());
        }

    }


}
