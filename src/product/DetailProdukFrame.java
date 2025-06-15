package product;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import model.User; // Import User
import shoppingCart.KeranjangBelanja;
import shoppingCart.KeranjangItem; // Import KeranjangItem
import transaction.DataTransaksi;   // Import DataTransaksi
import transaction.Transaksi;       // Import Transaksi
public class DetailProdukFrame extends JFrame {
    private Produk produk;
    private KeranjangBelanja keranjang;
    private User customer;
    public DetailProdukFrame(Produk produk, KeranjangBelanja keranjang, User customer) {
        this.produk = produk;
        this.keranjang = keranjang;
        this.customer = customer; 

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

        JPanel jumlahPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jumlahPanel.add(new JLabel("Jumlah:"));
        JTextField jumlahField = new JTextField("1", 5);
        jumlahPanel.add(jumlahField);
        infoPanel.add(jumlahPanel);

        add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton beliSekarangBtn = new JButton("Beli Sekarang");
        JButton tambahKeranjangBtn = new JButton("Tambahkan ke Keranjang");

        beliSekarangBtn.addActionListener(e -> {
            try {
                int jumlah = Integer.parseInt(jumlahField.getText());
                if (jumlah <= 0) {
                    JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (jumlah > produk.getStok()) {
                    JOptionPane.showMessageDialog(this, "Stok tidak cukup!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 1. Buat item tunggal untuk transaksi ini
                KeranjangItem itemBeli = new KeranjangItem(produk, jumlah);
                
                // 2. Buat list baru dan masukkan item tersebut
                List<KeranjangItem> itemsToBuy = new ArrayList<>();
                itemsToBuy.add(itemBeli);

                // 3. Buat objek transaksi baru menggunakan list tersebut
                Transaksi transaksiBaru = new Transaksi(itemsToBuy, "Diproses");

                // 4. Simpan transaksi ke daftar global (atau file, sesuai implementasi terakhir)
                DataTransaksi.tambahTransaksi(transaksiBaru);
                
                // 5. Kurangi stok produk
                produk.kurangiStok(jumlah);
                
                JOptionPane.showMessageDialog(this, "Pembelian berhasil! Transaksi telah dicatat.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                this.dispose(); // Tutup jendela setelah berhasil

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Masukkan jumlah yang valid (angka)!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        tambahKeranjangBtn.addActionListener(e -> {
            try {
                int jumlah = Integer.parseInt(jumlahField.getText());
                if (jumlah <= 0) {
                    JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (jumlah > produk.getStok()) {
                    JOptionPane.showMessageDialog(this, "Stok tidak cukup!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                keranjang.tambahProduk(produk, jumlah);
                JOptionPane.showMessageDialog(this, "Produk berhasil ditambahkan ke keranjang!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Masukkan jumlah yang valid (angka)!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(beliSekarangBtn);
        buttonPanel.add(tambahKeranjangBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
