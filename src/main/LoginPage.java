package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

import model.*;

public class LoginPage extends JFrame{
    private final JPanel panel;
    private final Font fontTitle = new Font("Tahoma", Font.BOLD, 18);
    private final Font fontText = new Font("Tahoma", Font.PLAIN, 14);
    private final Font fontButton = new Font("Tahoma", Font.BOLD, 12);

    private boolean loggedInStatus = false;
    private User foundUser = null;
    private User loggedInUser = null;

    public LoginPage(){
        setTitle("PanenMart Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 280);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(null);
        setContentPane(panel);

        // Title
        JLabel title = new JLabel("Selamat Datang di PanenMart");
        title.setFont(fontTitle);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(10, 20, 364, 25);
        panel.add(title);

        // Username input
        JLabel username = new JLabel("Username: ");
        username.setFont(fontText);
        username.setBounds(70, 70, 80, 25);
        panel.add(username);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(170, 70, 150, 25);
        usernameField.setColumns(10);
        panel.add(usernameField);

        // Password input
        JLabel password = new JLabel("Password: ");
        password.setFont(fontText);
        password.setBounds(70, 110, 80, 25);
        panel.add(password);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(170, 110, 150, 25);
        passwordField.setColumns(10);
        panel.add(passwordField);

        // message warning
        JLabel message = new JLabel("");
        message.setFont(fontText);
        message.setForeground(Color.RED);
        message.setBounds(70, 135, 250, 25);
        panel.add(message);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(fontButton);
        loginButton.setBounds(170, 160, 70, 30);
        panel.add(loginButton);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(fontButton);
        registerButton.setBounds(250, 160, 70, 30);
        panel.add(registerButton);


        // --- Event Listener untuk Login ---
        loginButton.addActionListener((ActionEvent e) -> {
            char[] passwordChar = passwordField.getPassword();

            String usernameInput = usernameField.getText();
            String passwordInput = new String(passwordChar);

            for (User user : DataUser.userList) {
                if (user.getName().equals(usernameInput)) {
                    foundUser = user;
                    break;
                }
            }

            if (foundUser != null && foundUser.login(passwordInput)) {
            	this.dispose();
            	
                loggedInUser = foundUser;
                String role = loggedInUser.getRole();
                
                if (role.equals("customer")) {
//                    new Customer_Dashboard().setVisible(true);
                } else if (role.equals("warehouse")) {
//                    new Warehouse_Dashboard().setVisible(true);
                } else if (role.equals("admin")) {
//                    new Admin_Dashboard().setVisible(true);
                }
                
            } else {
                message.setText("Username atau password salah");
            }
        });
    }

    public User getUser() {
        return loggedInUser;
    }
}
