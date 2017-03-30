package com.vgdatabase304.Forms;
import com.vgdatabase304.Adaptors.RegisteredUserAdaptor;
import com.vgdatabase304.Structures.Platform;
import com.vgdatabase304.Structures.RegisteredUser;
import com.vgdatabase304.Utils.ConnectionManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login extends JFrame {
    private JButton loginButton;
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton createAccountButton;
    private JTextField emailField;
    private JLabel emailLabel;
    private static UserSelfProfile profile;



    public Login() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String event = e.getActionCommand();
                System.out.println(event);
                String username, password;
                username = textField1.getText();
                password = passwordField1.getText();
                System.out.println(username);
                System.out.println(password);
                RegisteredUser user = new RegisteredUser(username);
                try {
                    String existingPassword = RegisteredUserAdaptor.getPassword(user);
                    System.out.println(existingPassword);
                    if (existingPassword.equals(password)) {
                        System.out.println("Username exists setup profile");
                        profile.setAccount(user);
                        profile.setVisible(true);
                    } else {
                        System.out.println("Username and password do not match.");
                    }
                } catch (SQLException err) {
                    System.out.println("Username does not exist");
                }

            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!emailField.isVisible()) {
                    emailField.setVisible(true);
                    emailLabel.setVisible(true);
                } else {
                    String username, password, email;
                    username = textField1.getText();
                    password = passwordField1.getText();
                    email = emailField.getText();
                    System.out.println(username);
                    System.out.println(password);
                    try {
                        System.out.println("try to add user to database");
                        RegisteredUser user = RegisteredUserAdaptor.addRegisteredUserToDatabase(username, email, password);
                        System.out.println("user successfully added");
                    } catch (SQLException err) {
                        System.out.println("An error occurred: " + err.getMessage());
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        try {
            ConnectionManager.initConnection("ora_s8h0b", "a57723158");
        } catch (SQLException e) {
            System.out.println("Unable to make connection to database (" + e.getMessage() + ")");
        }
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        profile = new UserSelfProfile();
    }
}
