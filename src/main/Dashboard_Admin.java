package main;

import model.*;
import order.*;
import product.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import admin.FormAdminRegisterFrame;

import java.awt.*;

public class Dashboard_Admin extends JFrame {
    private JTable userTable, produkTable, orderTable;
    private DefaultTableModel userTableModel, produkTableModel, orderTableModel;

    public Dashboard_Admin() {
        setTitle("Dashboard Admin - E-Marketplace");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel atas (untuk logout)
        JPanel panelAtas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel welcomeLabel = new JLabel("Selamat Datang, Admin!");
        JButton logoutButton = new JButton("Log Out");
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> logout());
        panelAtas.add(welcomeLabel);
        panelAtas.add(logoutButton);
        add(panelAtas, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab User
        tabbedPane.addTab("Data User", createUserPanel());

        // Tab Produk
        tabbedPane.addTab("Data Produk", createProdukPanel());

        // Tab Order
        tabbedPane.addTab("Data Order", createOrderPanel());

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

        tambahAdminButton.addActionListener(e -> new FormAdminRegisterFrame(() -> loadUserData()));
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
            String email = (String) userTableModel.getValueAt(selectedRow, 2); // kolom ke-2 adalah Email
            String role = (String) userTableModel.getValueAt(selectedRow, 3); // kolom ke-3 adalah Role

            if (role.equals("Admin")) {
                // Hitung jumlah admin yang ada
                long jumlahAdmin = DataUser.userList.stream()
                        .filter(u -> u instanceof Admin)
                        .count();

                if (jumlahAdmin <= 1) {
                    JOptionPane.showMessageDialog(this, "Tidak dapat menghapus admin terakhir. Minimal harus ada 1 admin.");
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
            produkTableModel.addRow(new Object[]{p.getProdukID(), p.getNama(), p.getHarga(), p.getStok(), kategori});
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

        String[] kolom = {"Order ID", "Customer", "Total", "Status", "Tanggal"};
        orderTableModel = new DefaultTableModel(kolom, 0);
        orderTable = new JTable(orderTableModel);
        JScrollPane scroll = new JScrollPane(orderTable);
        panel.add(scroll, BorderLayout.CENTER);

        loadOrderData();
        return panel;
    }

    private void loadOrderData() {
        orderTableModel.setRowCount(0);
        for (Order o : DataOrder.getOrderList()) {
            orderTableModel.addRow(new Object[]{
                o.getOrderID(),
                o.getCustomer().getName(),
                o.getTotalHarga(),
                o.getStatus(),
                o.getTanggalOrder()
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
