package com.vgdatabase304.Structures;
import java.sql.SQLException;

public class VGList {

    private int listID;

    public VGList(int listID) throws SQLException {
        this.listID = listID;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }
}
