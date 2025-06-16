// Dashboard_Customer.java
package main;

import feedback.FeedbackFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import model.*;
import moneyFormat.MoneyFormat;
import product.*;
import shoppingCart.*;
import transaction.*;

public class Dashboard_Customer extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField searchField;
    private JPanel productPanel;
    private List<Produk> productList;
    private List<Produk> filteredList;
    private KeranjangBelanja keranjang;
    private User currentUser;

    public Dashboard_Customer(User userLogin) {
        this.currentUser = userLogin;

        setTitle("Dashboard Customer - E-Marketplace");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout(10, 10));

        keranjang = new KeranjangBelanja();

        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        JButton cartButton = new JButton("Keranjang");
        JButton transaksiButton = new JButton("Transaksi");
        JButton feedbackButton = new JButton("Feedback");
        JButton profileButton = new JButton("Profil");
        JButton loggout = new JButton("Log Out");

        searchButton.addActionListener((ActionEvent e) -> performSearch());
        cartButton.addActionListener((ActionEvent e) -> new KeranjangFrame(keranjang, getCurrentUser()));
        transaksiButton.addActionListener(e -> new TransaksiFrame(getCurrentUser()));
        feedbackButton.addActionListener(e -> new FeedbackFrame(currentUser));
        profileButton.addActionListener((ActionEvent e) -> new ProfilFrame(getCurrentUser()));
        loggout.addActionListener((ActionEvent e) -> loggout());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(searchButton);
        buttonPanel.add(cartButton);
        buttonPanel.add(transaksiButton);
        buttonPanel.add(feedbackButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(loggout);

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(buttonPanel, BorderLayout.EAST);
        add(searchPanel, BorderLayout.NORTH);

        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(0, 3, 15, 15));
        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        productPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        productList = DataProduk.getDaftarProduk();
        filteredList = new ArrayList<>(productList);
        updateProductDisplay();

        setVisible(true);
    }

    private void performSearch() {
        String keyword = searchField.getText().trim().toLowerCase();
        filteredList.clear();

        for (Produk p : productList) {
            if (p.getNama().toLowerCase().contains(keyword)) {
                filteredList.add(p);
            }
        }

        updateProductDisplay();
    }

    private void updateProductDisplay() {
        productPanel.removeAll();
        for (Produk produk : filteredList) {
            productPanel.add(createProductCard(produk));
        }
        productPanel.revalidate();
        productPanel.repaint();
    }

    private JPanel createProductCard(Produk produk) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        card.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("<html><b>" + produk.getNama() + "</b></html>", SwingConstants.CENTER);
        JLabel priceLabel = new JLabel(MoneyFormat.rupiah(produk.getHarga()), SwingConstants.CENTER);

        String kategori = produk instanceof TanamanHias ? "Tanaman Hias" : "Bibit Tanaman";
        JLabel kategoriLabel = new JLabel(kategori, SwingConstants.CENTER);

        JButton detailButton = new JButton("Beli");
        detailButton.addActionListener(e -> new DetailProdukFrame(produk, keranjang, getCurrentUser()));

        card.add(nameLabel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.add(priceLabel);
        centerPanel.add(kategoriLabel);
        centerPanel.setBackground(Color.WHITE);

        card.add(centerPanel, BorderLayout.CENTER);
        card.add(detailButton, BorderLayout.SOUTH);

        return card;
    }

    private void loggout() {
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginPage().setVisible(true);
        }
    }

    public void checkoutLangsung(Produk produk, int jumlah) {
        Transaksi transaksi = new Transaksi(currentUser);
        transaksi.addItem(new TransaksiItem(produk, jumlah));
        transaksi.setStatus(TransaksiStatus.DIPROSES);

        DataTransaksi.tambahTransaksi(getCurrentUser().getUserID(),transaksi);
        JOptionPane.showMessageDialog(this, "Pembelian berhasil! Cek status di menu Transaksi.");
    }

    public void checkoutDariKeranjang() {
        if (keranjang.getDaftarItem().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keranjang kosong!");
            return;
        }

        Transaksi transaksi = new Transaksi(getCurrentUser());
        for (KeranjangItem item : keranjang.getDaftarItem()) {
            transaksi.addItem(new TransaksiItem(item.getProduk(), item.getJumlah()));
        }
        transaksi.setStatus(TransaksiStatus.DIPROSES);

        DataTransaksi.tambahTransaksi(getCurrentUser().getUserID(),transaksi);
        keranjang.kosongkanKeranjang();

        JOptionPane.showMessageDialog(this, "Checkout berhasil! Silakan cek menu Transaksi.");
    }

    public User getCurrentUser() {
        return currentUser;
    }
} 
