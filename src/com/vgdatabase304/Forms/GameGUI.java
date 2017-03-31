package com.vgdatabase304.Forms;

import com.vgdatabase304.Adaptors.GameAdaptor;
import com.vgdatabase304.Adaptors.VGTagAdaptor;
import com.vgdatabase304.Adaptors.VGTagGameAdaptor;
import com.vgdatabase304.Structures.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Skyline on 2017-03-28.
 */
public class GameGUI {
    private JButton backButton;
    private JPanel mainPanel;
    private JTextField gameName;
    private JButton gameReviews;
    private JComboBox listOfTags;
    private JTextField textField2;
    private JList platformList;
    private JLabel releaseList;
    private JButton attachTagToGame;
    private JFrame f;
    private List<VGTag> tags;

    public GameGUI(Game game, RegisteredUser user) {
        f = new JFrame("GameGUI");
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //Set name
        setName(game);

        //Populate tags
        populateTags();

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });

        attachTagToGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    VGTagGameAdaptor.addTagGameToDatabase(new VGTag((String) listOfTags.getSelectedItem()), game, user);
                    listOfTags.setSelectedIndex(0);
                } catch (SQLException err) {

                }

            }
        });

        f.setVisible(true);
        f.setContentPane(mainPanel);
        f.pack();
    }

    private void setName(Game game) {
        try {
            gameName.setText(GameAdaptor.getName(game));
        } catch (SQLException e) {
            System.out.println("Game name cannot be set");
        }
    }

    private void populateTags() {
        try {
            tags = VGTagAdaptor.getAllTags();
            for (VGTag tag: tags) {
                listOfTags.addItem(tag.getTagName());
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving tags");
        }
    }
}
