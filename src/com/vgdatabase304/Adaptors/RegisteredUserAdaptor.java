package com.vgdatabase304.Adaptors;

import com.vgdatabase304.Exceptions.InstanceNotFoundException;
import com.vgdatabase304.Structures.Ranking;
import com.vgdatabase304.Structures.RegisteredUser;
import com.vgdatabase304.Utils.ConnectionManager;
import sun.security.jca.GetInstance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class RegisteredUserAdaptor {

    protected static ResultSet rs;
    protected static Statement stmt;

    public static RegisteredUser addRegisteredUserToDatabase(String username, String email, String password) throws SQLException {
        System.out.println("before stmt");
        stmt = ConnectionManager.getStatement();
        System.out.println("after statement");
        try {
            System.out.println("before update");
            stmt.executeUpdate("INSERT INTO RegisteredUser (UserName, Email, Password) VALUES ('" +
                    username + "', '" +
                    email + "', '" +
                    password + "')"
            );
            System.out.println("after update");
        } catch (SQLException e) {
            System.out.println("exception thrown: " + e);
        }
        System.out.println("before return");
        return new RegisteredUser(username);
    }



    public static String getPassword(RegisteredUser user) throws SQLException {
        stmt = ConnectionManager.getStatement();
        String sql = "SELECT PASSWORD FROM REGISTEREDUSER WHERE USERNAME='" + user.getUsername() + "'";
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            return rs.getString("PASSWORD");
        } else {
            throw new InstanceNotFoundException("No record found in REGISTEREDUSER for " + user.getUsername());
        }
    }

    public static void setPassword(RegisteredUser user, String password) throws SQLException {
        stmt = ConnectionManager.getStatement();
        stmt.executeUpdate("UPDATE REGISTEREDUSER " +
                "SET PASSWORD = '" + password +
                "' WHERE USERNAME = '" + user.getUsername() + "'"
        );
    }

    public static String getEmail(RegisteredUser user) throws SQLException {
        stmt = ConnectionManager.getStatement();
        String sql = "SELECT EMAIL FROM REGISTEREDUSER WHERE USERNAME='" + user.getUsername() + "'";
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            return rs.getString("EMAIL");
        } else {
            throw new InstanceNotFoundException("No record found in REGISTEREDUSER for " + user.getUsername());
        }
    }

    public static void setEmail(RegisteredUser user, String email) throws SQLException {
        stmt = ConnectionManager.getStatement();
        stmt.executeUpdate("UPDATE REGISTEREDUSER " +
                "SET EMAIL = '" + email +
                "' WHERE USERNAME = '" + user.getUsername() + "'"
        );
    }

    public static List<RegisteredUser> searchForRegisteredUser(String searchQuery) throws SQLException {
        stmt = ConnectionManager.getStatement();
        List<RegisteredUser> users = new ArrayList<>();
        rs = stmt.executeQuery("SELECT USERNAME FROM REGISTEREDUSER WHERE USERNAME LIKE '" + searchQuery + "'");
        while (rs.next()) {
            users.add(new RegisteredUser(rs.getString("USERNAME")));
        }
        if (users.size() > 0) {
            return users;
        } else {
            throw new InstanceNotFoundException("No users found with name including '" + searchQuery + "'");
        }
    }

    public static Ranking getUsersReviewRanking(RegisteredUser user) throws SQLException {
        stmt = ConnectionManager.getStatement();
        int personalCount;
        int totalCount;
        rs = stmt.executeQuery("SELECT COUNT(*) FROM CREATEREVIEW WHERE USERNAME='" + user.getUsername() + "'");
        if (rs.next()) {
            personalCount = rs.getInt(1);
        } else {
            personalCount = 0;
        }

        rs = stmt.executeQuery("SELECT MAX(COUNT) FROM (SELECT COUNT(*) AS COUNT FROM CREATEREVIEW GROUP BY USERNAME)");
        if (rs.next()) {
            totalCount = rs.getInt(1);
        } else {
            totalCount = 1;
        }

        return new Ranking(personalCount, totalCount);
    }
}
