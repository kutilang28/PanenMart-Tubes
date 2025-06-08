package admin;

import model.*;
import javax.swing.*;
import java.awt.*;

public class FormAdminRegisterFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JButton simpanButton, batalButton;
    private Runnable onSelesai;

    public FormAdminRegisterFrame(Runnable onSelesai) {
        this.onSelesai = onSelesai;

        setTitle("Tambah Akun Admin");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);
        add(panel);

        JLabel titleLabel = new JLabel("Tambah Admin Baru");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        titleLabel.setBounds(80, 10, 200, 25);
        panel.add(titleLabel);

        JLabel nameLabel = new JLabel("Nama:");
        nameLabel.setBounds(30, 50, 80, 25);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 50, 180, 25);
        panel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 85, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(120, 85, 180, 25);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 120, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 120, 180, 25);
        panel.add(passwordField);

        simpanButton = new JButton("Simpan");
        simpanButton.setBounds(60, 165, 100, 30);
        panel.add(simpanButton);

        batalButton = new JButton("Batal");
        batalButton.setBounds(170, 165, 100, 30);
        panel.add(batalButton);

        simpanButton.addActionListener(e -> simpanAdminBaru());
        batalButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void simpanAdminBaru() {
        String nama = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cek email sudah dipakai
        for (User user : DataUser.userList) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                JOptionPane.showMessageDialog(this, "Email sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Admin newAdmin = new Admin(nama, email, password);
        DataUser.userList.add(newAdmin);

        JOptionPane.showMessageDialog(this, "Admin berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        if (onSelesai != null) {
            onSelesai.run();
        }
    }
}
