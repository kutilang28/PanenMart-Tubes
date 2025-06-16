package feedback;

import model.User;
import javax.swing.*;
import java.awt.*;

public class FeedbackFrame extends JFrame {
    private User currentUser;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JComboBox<String> kategoriComboBox;

    // Komponen untuk form Layanan
    private JTextField jenisLayananField;
    private JSpinner tingkatLayananSpinner;

    // Komponen untuk form Saran Fitur
    private JTextField judulFiturField;
    private JTextArea deskripsiFiturArea;

    public FeedbackFrame(User currentUser) {
        this.currentUser = currentUser;

        setTitle("Form Feedback");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel Atas untuk memilih kategori
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Pilih Kategori Feedback:"));
        String[] kategori = {"Feedback Pelayanan", "Saran Fitur"};
        kategoriComboBox = new JComboBox<>(kategori);
        topPanel.add(kategoriComboBox);
        add(topPanel, BorderLayout.NORTH);

        // Panel utama dengan CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(createLayananPanel(), "Feedback Pelayanan");
        cardPanel.add(createSaranFiturPanel(), "Saran Fitur");
        add(cardPanel, BorderLayout.CENTER);

        // Tombol Kirim
        JButton kirimButton = new JButton("Kirim Feedback");
        add(kirimButton, BorderLayout.SOUTH);

        // Listener
        kategoriComboBox.addActionListener(e -> cardLayout.show(cardPanel, (String) kategoriComboBox.getSelectedItem()));
        kirimButton.addActionListener(e -> kirimFeedback());

        setVisible(true);
    }

    private JPanel createLayananPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Jenis Pelayanan:"));
        jenisLayananField = new JTextField();
        panel.add(jenisLayananField);

        panel.add(new JLabel("Tingkat Pelayanan (1-10):"));
        // Spinner menjamin input hanya angka dalam rentang yang ditentukan
        SpinnerModel spinnerModel = new SpinnerNumberModel(5, 1, 10, 1);
        tingkatLayananSpinner = new JSpinner(spinnerModel);
        panel.add(tingkatLayananSpinner);

        return panel;
    }

    private JPanel createSaranFiturPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topSubPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        topSubPanel.add(new JLabel("Judul Fitur:"));
        judulFiturField = new JTextField();
        topSubPanel.add(judulFiturField);
        
        panel.add(topSubPanel, BorderLayout.NORTH);
        
        panel.add(new JLabel("Deskripsi Fitur:"), BorderLayout.WEST); // Label tidak pas, kita atasi dengan panel terpisah
        
        deskripsiFiturArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(deskripsiFiturArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void kirimFeedback() {
        try {
            String kategori = (String) kategoriComboBox.getSelectedItem();

            if ("Feedback Pelayanan".equals(kategori)) {
                String jenis = jenisLayananField.getText().trim();
                if (jenis.isEmpty()) {
                    throw new IllegalArgumentException("Jenis pelayanan tidak boleh kosong.");
                }
                int tingkat = (int) tingkatLayananSpinner.getValue();
                
                LayananFeedback fb = new LayananFeedback(currentUser, jenis, tingkat);
                DataFeedback.tambahFeedback(fb);

            } else if ("Saran Fitur".equals(kategori)) {
                String judul = judulFiturField.getText().trim();
                String deskripsi = deskripsiFiturArea.getText().trim();
                if (judul.isEmpty() || deskripsi.isEmpty()) {
                    throw new IllegalArgumentException("Judul dan deskripsi fitur tidak boleh kosong.");
                }
                
                SaranFitur fb = new SaranFitur(currentUser, judul, deskripsi);
                DataFeedback.tambahFeedback(fb);
            }

            JOptionPane.showMessageDialog(this, "Terima kasih! Feedback Anda telah berhasil dikirim.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan tak terduga: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}