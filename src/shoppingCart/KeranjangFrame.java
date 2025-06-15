package shoppingCart;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import model.User;
import transaction.DataTransaksi;
import transaction.Transaksi;

public class KeranjangFrame extends JFrame {
    private KeranjangBelanja keranjang;
    private JPanel itemPanel;
    private JLabel totalLabel;
    private User customer;

    public KeranjangFrame(KeranjangBelanja keranjang, User customer) {
        this.keranjang = keranjang;
        this.customer = customer;

        setTitle("Keranjang Belanja");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(itemPanel), BorderLayout.CENTER);

        totalLabel = new JLabel("Total: Rp 0");
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(totalLabel, BorderLayout.NORTH);

        JButton orderButton = new JButton("Order Sekarang");
        orderButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(orderButton, BorderLayout.SOUTH);

        orderButton.addActionListener(e -> {
            if (keranjang.getDaftarItem().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Keranjang Anda masih kosong.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (KeranjangItem item : keranjang.getDaftarItem()) {
                if (item.getProduk().getStok() < item.getJumlah()) {
                    JOptionPane.showMessageDialog(this, "Stok tidak cukup untuk produk " + item.getProduk().getNama(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // --- KUNCI PERBAIKAN ADA DI SINI ---
            // Buat SALINAN BARU dari daftar item keranjang.
            // new ArrayList<>(...) memastikan kita membuat objek list baru yang terpisah.
            List<KeranjangItem> itemUntukTransaksi = new ArrayList<>(keranjang.getDaftarItem());
            
            // Gunakan SALINAN tersebut untuk membuat objek transaksi.
            Transaksi transaksiBaru = new Transaksi(itemUntukTransaksi, "Diproses");
            // ------------------------------------

            // Simpan transaksi yang aman ini ke daftar global.
            DataTransaksi.tambahTransaksi(transaksiBaru);
            
            // Sekarang kita bisa dengan aman mengurangi stok dan membersihkan keranjang asli.
            for (KeranjangItem item : itemUntukTransaksi) {
                item.getProduk().kurangiStok(item.getJumlah());
            }
            keranjang.getDaftarItem().clear();

            JOptionPane.showMessageDialog(this, "Order berhasil! Transaksi Anda telah dicatat.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        });

        updateDisplay();
        setVisible(true);
    }
    
    private void updateDisplay() {
        itemPanel.removeAll();
        for (KeranjangItem item : keranjang.getDaftarItem()) {
            JPanel panel = new JPanel(new GridLayout(1, 3));
            panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            panel.add(new JLabel(item.getProduk().getNama()));
            panel.add(new JLabel("Jumlah: " + item.getJumlah()));
            panel.add(new JLabel(String.format("Subtotal: Rp %,.0f", item.getTotalHarga())));
            itemPanel.add(panel);
        }
        totalLabel.setText(String.format("Total Belanja: Rp %,.0f", keranjang.getTotalKeranjang()));
        itemPanel.revalidate();
        itemPanel.repaint();
    }
}