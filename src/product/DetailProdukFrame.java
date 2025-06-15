package product;

import javax.swing.*;
import java.awt.*;
import model.User;
import shoppingCart.KeranjangBelanja;
import transaction.*;

public class DetailProdukFrame extends JFrame {
    private Produk produk;
    private KeranjangBelanja keranjang;
    private User currentUser;

    public DetailProdukFrame(Produk produk, KeranjangBelanja keranjang, User currentUser) {
        this.produk = produk;
        this.keranjang = keranjang;
        this.currentUser = currentUser;

        setTitle("Detail Produk - " + produk.getNama());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        infoPanel.add(new JLabel("Nama: " + produk.getNama()));
        infoPanel.add(new JLabel("Harga: Rp " + produk.getHarga()));
        infoPanel.add(new JLabel("Stok: " + produk.getStok()));

        JTextField jumlahField = new JTextField("1");
        infoPanel.add(new JLabel("Jumlah:"));
        infoPanel.add(jumlahField);

        add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton beliSekarangBtn = new JButton("Beli Sekarang");
        JButton tambahKeranjangBtn = new JButton("Tambahkan ke Keranjang");

        beliSekarangBtn.addActionListener(e -> {
            try {
                int jumlah = Integer.parseInt(jumlahField.getText());
                if (jumlah <= 0) throw new NumberFormatException();
                if (jumlah > produk.getStok()) {
                    JOptionPane.showMessageDialog(this, "Stok tidak cukup!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kurangi stok
                produk.kurangiStok(jumlah);

                // Simpan ke transaksi
                Transaksi transaksi = new Transaksi(currentUser);
                transaksi.addItem(new TransaksiItem(produk, jumlah));
                DataTransaksi.tambahTransaksi(currentUser.getUserID(), transaksi);

                JOptionPane.showMessageDialog(this, "Pembelian berhasil dan transaksi tersimpan!");
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Masukkan jumlah yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        tambahKeranjangBtn.addActionListener(e -> {
            try {
                int jumlah = Integer.parseInt(jumlahField.getText());
                if (jumlah <= 0) throw new NumberFormatException();
                if (jumlah > produk.getStok()) {
                    JOptionPane.showMessageDialog(this, "Stok tidak cukup!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                keranjang.tambahProduk(produk, jumlah);
                JOptionPane.showMessageDialog(this, "Produk ditambahkan ke keranjang!");
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Masukkan jumlah yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(beliSekarangBtn);
        buttonPanel.add(tambahKeranjangBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
