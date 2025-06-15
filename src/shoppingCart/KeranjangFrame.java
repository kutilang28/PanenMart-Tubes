package shoppingCart;

import javax.swing.*;
import java.awt.*;
import model.User;
import moneyFormat.MoneyFormat;
import product.Produk;
import transaction.*;

public class KeranjangFrame extends JFrame {
    private KeranjangBelanja keranjang;
    private JPanel itemPanel;
    private JLabel totalLabel;
    private User currentUser;

    public KeranjangFrame(KeranjangBelanja keranjang, User currentUser) {
        this.keranjang = keranjang;
        this.currentUser = currentUser;

        setTitle("Keranjang Belanja");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(itemPanel);
        add(scrollPane, BorderLayout.CENTER);

        totalLabel = new JLabel("Total: Rp 0");
        add(totalLabel, BorderLayout.NORTH);

        JButton orderButton = new JButton("Order");
        orderButton.addActionListener(e -> prosesOrder());

        add(orderButton, BorderLayout.SOUTH);

        updateDisplay();
        setVisible(true);
    }

    private void prosesOrder() {
        if (keranjang.getDaftarItem().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keranjang kosong!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (KeranjangItem item : keranjang.getDaftarItem()) {
            Produk p = item.getProduk();
            int jumlah = item.getJumlah();
            if (p.getStok() < jumlah) {
                JOptionPane.showMessageDialog(this,
                        "Stok tidak cukup untuk produk " + p.getNama(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Transaksi transaksi = new Transaksi(currentUser);
        for (KeranjangItem item : keranjang.getDaftarItem()) {
            Produk p = item.getProduk();
            int jumlah = item.getJumlah();
            p.kurangiStok(jumlah);
            transaksi.addItem(new TransaksiItem(p, jumlah));
        }

        DataTransaksi.tambahTransaksi(currentUser.getUserID(), transaksi);
        keranjang.kosongkanKeranjang();
        JOptionPane.showMessageDialog(this, "Order berhasil! Transaksi tersimpan.");
        updateDisplay();
    }

    private void updateDisplay() {
        itemPanel.removeAll();

        for (KeranjangItem item : keranjang.getDaftarItem()) {
            Produk produk = item.getProduk();
            int jumlah = item.getJumlah();

            JPanel panel = new JPanel(new GridLayout(1, 3));
            panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            panel.add(new JLabel(produk.getNama()));
            panel.add(new JLabel("Jumlah: " + jumlah));
            panel.add(new JLabel("Subtotal: " + MoneyFormat.rupiah(produk.getHarga() * jumlah)));

            itemPanel.add(panel);
        }

        totalLabel.setText("Total: Rp " + keranjang.getTotalKeranjang());

        itemPanel.revalidate();
        itemPanel.repaint();
    }
}
