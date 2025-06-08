package main;

import javax.swing.*;

import model.*;
import product.*;
import shoppingCart.*;
import transaction.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

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

        // 🔍 Panel Search & Navigasi
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        JButton cartButton = new JButton("Keranjang");
        JButton transaksiButton = new JButton("Transaksi");
        JButton profileButton = new JButton("Profil");
        JButton loggout = new JButton("Log Out");

        searchButton.addActionListener((ActionEvent e) -> performSearch());
        cartButton.addActionListener((ActionEvent e) -> new KeranjangFrame(keranjang));
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

        // Panel Produk
        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(0, 3, 15, 15));
        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        productPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Ambil dummy produk
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
        JLabel priceLabel = new JLabel("Rp " + String.format("%.0f", produk.getHarga()), SwingConstants.CENTER);

        // Buat label kategori manual berdasar instance class produk
        String kategori = "";
        if (produk instanceof TanamanHias) {
            kategori = "Tanaman Hias";
        } else if (produk instanceof BibitTanaman) {
            kategori = "Bibit Tanaman";
        }
        JLabel kategoriLabel = new JLabel(kategori, SwingConstants.CENTER);

        JButton detailButton = new JButton("Beli");
        detailButton.addActionListener(e -> new DetailProdukFrame(produk, keranjang));

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
    	this.dispose();
    	
    	LoginPage login = new LoginPage();
    	login.setVisible(true);
    }
}
