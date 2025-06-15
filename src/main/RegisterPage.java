package main;

import model.*;

import javax.swing.*;
import java.awt.*;

public class RegisterPage extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField nameField, emailField, phoneField, addressField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    private JLabel phoneLabel, addressLabel;
    private JButton registerButton;

    public RegisterPage() {
        setTitle("Register - PanenMart");
        setSize(400, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        JLabel titleLabel = new JLabel("Form Register");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setBounds(130, 20, 200, 30);
        panel.add(titleLabel);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 70, 100, 25);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(140, 70, 200, 25);
        panel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 110, 100, 25);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(140, 110, 200, 25);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 150, 100, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 150, 200, 25);
        panel.add(passwordField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(30, 190, 100, 25);
        panel.add(roleLabel);

        // HANYA Customer & Warehouse
        String[] roles = {"Customer", "Warehouse"};
        roleCombo = new JComboBox<>(roles);
        roleCombo.setBounds(140, 190, 200, 25);
        panel.add(roleCombo);

        // Phone
        phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(30, 230, 100, 25);
        panel.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(140, 230, 200, 25);
        panel.add(phoneField);

        // Address (khusus Customer)
        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(30, 270, 100, 25);
        panel.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(140, 270, 200, 25);
        panel.add(addressField);

        registerButton = new JButton("Register");
        registerButton.setBounds(140, 320, 100, 30);
        panel.add(registerButton);

        updateFieldsVisibility();
        roleCombo.addActionListener(e -> updateFieldsVisibility());
        registerButton.addActionListener(e -> registerUser());
    }

    private void updateFieldsVisibility() {
        String role = roleCombo.getSelectedItem().toString();
        if (role.equalsIgnoreCase("Customer")) {
            phoneLabel.setVisible(true);
            phoneField.setVisible(true);
            addressLabel.setVisible(true);
            addressField.setVisible(true);
        } else if (role.equalsIgnoreCase("Warehouse")) {
            phoneLabel.setVisible(true);
            phoneField.setVisible(true);
            addressLabel.setVisible(false);
            addressField.setVisible(false);
        }
    }

    private void registerUser() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = roleCombo.getSelectedItem().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name, Email, dan Password wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (User user : DataUser.userList) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                JOptionPane.showMessageDialog(this, "Email sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (role.equalsIgnoreCase("Customer")) {
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
            if (phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Phone dan Address wajib diisi untuk Customer!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Customer newCustomer = new Customer(name, email, password, phone, address);
            DataUser.userList.add(newCustomer);

        } else if (role.equalsIgnoreCase("Warehouse")) {
            String phone = phoneField.getText().trim();
            if (phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Phone wajib diisi untuk Warehouse!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Warehouse newWarehouse = new Warehouse(name, email, password, phone);
            DataUser.userList.add(newWarehouse);
        } else {
            JOptionPane.showMessageDialog(this, "Role tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Registrasi berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();

        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }
}
