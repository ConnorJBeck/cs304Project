package com.vgdatabase304.Forms;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by Skyline on 2017-03-29.
 */
public class SearchResults {
    private JPanel panel1;
    private JLabel results;
    private JButton backButton;
    private JList resultList;

    public SearchResults() {
        resultList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });
    }
}