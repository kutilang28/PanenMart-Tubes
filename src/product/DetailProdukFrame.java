package product;

import javax.swing.*;
import java.awt.*;
import model.User;
import moneyFormat.MoneyFormat;
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
        setSize(450, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Warna dan Font
        Color background = new Color(245, 250, 255);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 20);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        containerPanel.setBackground(background);

        JLabel titleLabel = new JLabel(produk.getNama());
        titleLabel.setFont(titleFont);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        containerPanel.add(titleLabel);

        containerPanel.add(Box.createVerticalStrut(10));

        // Info Produk
        JPanel infoCard = new JPanel();
        infoCard.setLayout(new BoxLayout(infoCard, BoxLayout.Y_AXIS));
        infoCard.setBackground(Color.WHITE);
        infoCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        infoCard.add(makeInfoLabel("Harga: Rp " + MoneyFormat.rupiah(produk.getHarga()), labelFont));
        infoCard.add(makeInfoLabel("Stok: " + produk.getStok(), labelFont));

        if (produk instanceof TanamanHias) {
            TanamanHias th = (TanamanHias) produk;
            infoCard.add(makeInfoLabel("Include Pot: " + (th.isPotInclude() ? "Ya" : "Tidak"), labelFont));
            infoCard.add(makeInfoLabel("Warna Bunga: " + th.getWarnaBunga(), labelFont));
        } else if (produk instanceof BibitTanaman) {
            BibitTanaman bt = (BibitTanaman) produk;
            infoCard.add(makeInfoLabel("Include Pot: " + (bt.isPotInclude() ? "Ya" : "Tidak"), labelFont));
            infoCard.add(makeInfoLabel("Masa Panen: " + bt.getMasaPanen() + " hari", labelFont));
        }

        containerPanel.add(infoCard);
        containerPanel.add(Box.createVerticalStrut(15));

        // Input Jumlah
        JPanel jumlahPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jumlahPanel.setOpaque(false);
        JLabel jumlahLabel = new JLabel("Jumlah:");
        JTextField jumlahField = new JTextField("1", 5);
        jumlahPanel.add(jumlahLabel);
        jumlahPanel.add(jumlahField);
        containerPanel.add(jumlahPanel);

        add(containerPanel, BorderLayout.CENTER);

        // Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton beliSekarangBtn = new JButton("Beli Sekarang");
        JButton tambahKeranjangBtn = new JButton("➕ Tambah ke Keranjang");

        buttonPanel.add(beliSekarangBtn);
        buttonPanel.add(tambahKeranjangBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Aksi tombol
        beliSekarangBtn.addActionListener(e -> {
            try {
                int jumlah = Integer.parseInt(jumlahField.getText());
                if (jumlah <= 0) throw new NumberFormatException();
                if (jumlah > produk.getStok()) {
                    JOptionPane.showMessageDialog(this, "Stok tidak cukup!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                produk.kurangiStok(jumlah);
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

        setVisible(true);
    }

    private JLabel makeInfoLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
        return label;
    }
}
