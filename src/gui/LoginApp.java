package gui;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.*; // Import kelas-kelas model
import interfaces.Login; // Import interface

public class LoginApp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    // Data user sederhana (simulasi database)
    private User[] users = {
            new Admin("admin", "admin@panenmart.com", "admin123"),
            new Customer("customer", "customer@panenmart.com", "cust123", "08123456789", "Jl. Contoh No. 1"),
            new Warehouse("warehouse", "warehouse@panenmart.com", "ware123", "0876543210")
    };

    public LoginApp() {
        setTitle("PanenMart Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 280);
        setLocationRelativeTo(null); // Menempatkan jendela di tengah layar

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null); // Menggunakan layout null untuk penempatan manual
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("Selamat Datang di PanenMart");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(10, 20, 364, 25);
        contentPane.add(lblTitle);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblUsername.setBounds(70, 70, 80, 25);
        contentPane.add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(170, 70, 150, 25);
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setBounds(70, 110, 80, 25);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(170, 110, 150, 25);
        contentPane.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        loginButton.setBounds(170, 160, 70, 30);
        contentPane.add(loginButton);

        JButton cancelButton = new JButton("Exit");
        cancelButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        cancelButton.setBounds(250, 160, 70, 30);
        contentPane.add(cancelButton);

        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(10, 200, 364, 25);
        contentPane.add(messageLabel);

        // --- Event Listener untuk Login ---
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                User loggedInUser = null;

                // Mencari user yang cocok berdasarkan username
                for (User user : users) {
                    if (user.getName().equals(username)) {
                        loggedInUser = user;
                        break;
                    }
                }

                
                
                
                if (loggedInUser != null) {
                    // Polymorphism: memanggil metode login() yang diimplementasikan oleh User (melalui interface Loginable)
                    if (loggedInUser.login(password)) {
                        // Menampilkan pesan sukses dan peran user
                        messageLabel.setText("Login Berhasil! Selamat datang, " + loggedInUser.getName() + " (" + loggedInUser.getRole() + ").");
                        messageLabel.setForeground(new Color(0, 128, 0)); // Warna hijau
                        
                        // Di sini Anda bisa menambahkan logika untuk membuka jendela aplikasi utama
                        JOptionPane.showMessageDialog(LoginApp.this, "Anda berhasil login sebagai " + loggedInUser.getRole() + "!");
                        dispose(); // Menutup jendela login setelah berhasil
                    } else {
                        messageLabel.setText("Password salah!");
                        messageLabel.setForeground(new Color(255, 0, 0)); // Warna merah
                    }
                } else {
                    messageLabel.setText("Username tidak ditemukan!");
                    messageLabel.setForeground(new Color(255, 0, 0)); // Warna merah
                }
            }
        });

        // --- Event Listener untuk Exit ---
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Keluar dari aplikasi
            }
        });
    }

    public static void main(String[] args) {
        // Jalankan aplikasi GUI di Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    LoginApp frame = new LoginApp();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}