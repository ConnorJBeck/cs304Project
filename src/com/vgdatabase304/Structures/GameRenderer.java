package com.vgdatabase304.Structures;

import com.vgdatabase304.Adaptors.GameAdaptor;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;


public class GameRenderer extends CellRenderer implements ListCellRenderer<Object> {

    // This is the only method defined by ListCellRenderer.
    // We just reconfigure the JLabel each time we're called.
    @Override
    public Component getListCellRendererComponent(
            JList<?> list,           // the list
            Object value,            // value to display
            int index,               // cell index
            boolean isSelected,      // is the cell selected
            boolean cellHasFocus)   // does the cell have focus
    {
        String s;
        try {
            s = GameAdaptor.getName((Game) value);
        } catch (SQLException err) {
            s = "Game could not be found";
        }

        return super.setVariables(list, s, index, isSelected, cellHasFocus);
    }
}
