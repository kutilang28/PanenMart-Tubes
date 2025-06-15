package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import model.*;
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
    private User currentUser; // Field ini penting

    public Dashboard_Customer(User userLogin) {
        this.currentUser = userLogin; // Simpan pengguna yang login

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
        JButton profileButton = new JButton("Profil");
        JButton loggout = new JButton("Log Out");

        searchButton.addActionListener((ActionEvent e) -> performSearch());
        
        // --- BAGIAN PENTING ---
        // Saat tombol keranjang diklik, kirim data 'keranjang' dan 'currentUser'
        cartButton.addActionListener((ActionEvent e) -> new KeranjangFrame(keranjang, currentUser));
        // --- AKHIR BAGIAN PENTING ---

        transaksiButton.addActionListener(e -> new TransaksiFrame());
        profileButton.addActionListener((ActionEvent e) -> new ProfilFrame(currentUser));
        loggout.addActionListener((ActionEvent e) -> loggout());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(searchButton);
        buttonPanel.add(cartButton);
        buttonPanel.add(transaksiButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(loggout);
        
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(buttonPanel, BorderLayout.EAST);
        add(searchPanel, BorderLayout.NORTH);

        productPanel = new JPanel(new GridLayout(0, 3, 15, 15));
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

    // Metode lain (performSearch, updateProductDisplay, createProductCard, loggout) tidak perlu diubah
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
        JLabel priceLabel = new JLabel("Rp " + String.format("%,.0f", produk.getHarga()), SwingConstants.CENTER);

        String kategori = produk instanceof TanamanHias ? "Tanaman Hias" : "Bibit Tanaman";
        JLabel kategoriLabel = new JLabel(kategori, SwingConstants.CENTER);

        JButton detailButton = new JButton("Beli");
        detailButton.addActionListener(e -> new DetailProdukFrame(produk, keranjang, currentUser));

        // Susun ulang supaya tombol di bawah
        card.add(nameLabel, BorderLayout.NORTH);

        // Panel tengah untuk harga dan kategori
        JPanel centerPanel = new JPanel(new GridLayout(2,1));
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
}