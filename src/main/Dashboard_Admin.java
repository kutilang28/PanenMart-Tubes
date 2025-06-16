package main;

import admin.FormAdminRegisterFrame;
import feedback.DataFeedback;
import feedback.Feedback;
import feedback.LayananFeedback;
import feedback.SaranFitur;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;
import moneyFormat.MoneyFormat;
import product.*;
import transaction.*;

public class Dashboard_Admin extends JFrame {
    private JTable userTable, produkTable, orderTable, feedbackTable;
    private DefaultTableModel userTableModel, produkTableModel, orderTableModel, feedbackTableModel;

    public Dashboard_Admin() {
        setTitle("Dashboard Admin - E-Marketplace");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel atas (logout)
        JPanel panelAtas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel welcomeLabel = new JLabel("Selamat Datang, Admin!");
        JButton logoutButton = new JButton("Log Out");
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> logout());
        panelAtas.add(welcomeLabel);
        panelAtas.add(logoutButton);
        add(panelAtas, BorderLayout.NORTH);

        // Tab utama
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Data User", createUserPanel());
        tabbedPane.addTab("Data Produk", createProdukPanel());
        tabbedPane.addTab("Data Transaksi", createOrderPanel());
        tabbedPane.addTab("Data Feedback", createFeedbackPanel());
        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] kolom = {"ID", "Nama", "Email", "Role"};
        userTableModel = new DefaultTableModel(kolom, 0);
        userTable = new JTable(userTableModel);
        JScrollPane scroll = new JScrollPane(userTable);
        panel.add(scroll, BorderLayout.CENTER);

        JButton tambahAdminButton = new JButton("Tambah Admin");
        JButton hapusUserButton = new JButton("Hapus User");

        tambahAdminButton.addActionListener(e -> new FormAdminRegisterFrame(this::loadUserData));
        hapusUserButton.addActionListener(e -> hapusSelectedUser());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(tambahAdminButton);
        buttonPanel.add(hapusUserButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        loadUserData();
        return panel;
    }

    private void loadUserData() {
        userTableModel.setRowCount(0);
        for (User u : DataUser.userList) {
            String role = u instanceof Admin ? "Admin" : u instanceof Customer ? "Customer" : "Warehouse";
            userTableModel.addRow(new Object[]{u.getUserID(), u.getName(), u.getEmail(), role});
        }
    }

    private void hapusSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            String email = (String) userTableModel.getValueAt(selectedRow, 2);
            String role = (String) userTableModel.getValueAt(selectedRow, 3);

            if (role.equals("Admin")) {
                long jumlahAdmin = DataUser.userList.stream().filter(u -> u instanceof Admin).count();
                if (jumlahAdmin <= 1) {
                    JOptionPane.showMessageDialog(this, "Tidak dapat menghapus admin terakhir.");
                    return;
                }
            }

            int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus user?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                DataUser.hapusUser(email);
                loadUserData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih user yang ingin dihapus.");
        }
    }

    private JPanel createProdukPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] kolom = {"ID", "Nama", "Harga", "Stok", "Kategori"};
        produkTableModel = new DefaultTableModel(kolom, 0);
        produkTable = new JTable(produkTableModel);
        JScrollPane scroll = new JScrollPane(produkTable);
        panel.add(scroll, BorderLayout.CENTER);

        JButton hapusProdukButton = new JButton("Hapus Produk");
        hapusProdukButton.addActionListener(e -> hapusSelectedProduk());
        panel.add(hapusProdukButton, BorderLayout.SOUTH);

        loadProdukData();
        return panel;
    }

    private void loadProdukData() {
        produkTableModel.setRowCount(0);
        for (Produk p : DataProduk.getDaftarProduk()) {
            String kategori = p instanceof TanamanHias ? "Tanaman Hias" : "Bibit Tanaman";
            produkTableModel.addRow(new Object[]{p.getProdukID(), p.getNama(), MoneyFormat.rupiah(p.getHarga()), p.getStok(), kategori});
        }
    }

    private void hapusSelectedProduk() {
        int selectedRow = produkTable.getSelectedRow();
        if (selectedRow >= 0) {
            String produkID = (String) produkTableModel.getValueAt(selectedRow, 0);
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus produk?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                DataProduk.hapusProduk(produkID);
                loadProdukData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih produk yang ingin dihapus.");
        }
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] kolom = {"ID Transaksi", "Customer", "Total", "Status", "Tanggal"};
        orderTableModel = new DefaultTableModel(kolom, 0);
        orderTable = new JTable(orderTableModel);
        JScrollPane scroll = new JScrollPane(orderTable);
        panel.add(scroll, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadOrderData());
        panel.add(refreshButton, BorderLayout.SOUTH);

        loadOrderData();
        return panel;
    }

    private void loadOrderData() {
        orderTableModel.setRowCount(0);
        List<Transaksi> semuaTransaksi = DataTransaksi.getSemuaTransaksi();

        for (Transaksi t : semuaTransaksi) {
            orderTableModel.addRow(new Object[]{
                t.getTransaksiID(),
                t.getCustomer().getName(),
                MoneyFormat.rupiah(t.getTotalHarga()),
                t.getStatus().name(),
                t.getTanggalTransaksi()
            });
        }

    }
     private JPanel createFeedbackPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        feedbackTableModel = new DefaultTableModel(new Object[]{"ID Feedback", "Customer", "Tanggal", "Kategori", "Detail"}, 0){
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        feedbackTable = new JTable(feedbackTableModel);
        feedbackTable.setRowHeight(30);
        feedbackTable.getColumnModel().getColumn(4).setPreferredWidth(400); // Perlebar kolom detail

        panel.add(new JScrollPane(feedbackTable), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh Data Feedback");
        refreshButton.addActionListener(e -> loadFeedbackData());
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        loadFeedbackData(); // Muat data saat panel pertama kali dibuat
        return panel;
    }
    
    private void loadFeedbackData() {
        feedbackTableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        for (Feedback fb : DataFeedback.getSemuaFeedback()) {
            String kategori = "N/A";
            String detail = "N/A";

            if (fb instanceof LayananFeedback lf) {
                kategori = "Layanan";
                detail = "Jenis: " + lf.getJenisLayanan() + " | Rating: " + lf.getTingkatPelayanan() + "/10";
            } else if (fb instanceof SaranFitur sf) {
                kategori = "Saran Fitur";
                detail = "Judul: " + sf.getJudulFitur() + " | Deskripsi: " + sf.getDeskripsiFitur();
            }

            feedbackTableModel.addRow(new Object[]{
                fb.getFeedbackID(),
                fb.getCustomer().getName(),
                sdf.format(fb.getTanggal()),
                kategori,
                detail
            });
        }
    }


    private void logout() {
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginPage().setVisible(true);
        }
    }
}
