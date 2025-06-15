package transaction;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import product.Produk; 
import shoppingCart.KeranjangItem;

public class TransaksiFrame extends JFrame {
    
    private JTextArea textArea; 

    public TransaksiFrame() {
        setTitle("Riwayat Transaksi");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setMargin(new Insets(5, 5, 5, 5));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(); 
        JButton refreshButton = new JButton("Muat Ulang Data");
        refreshButton.addActionListener(e -> muatUlangDataTransaksi());
        bottomPanel.add(refreshButton);
        add(bottomPanel, BorderLayout.SOUTH);

        muatUlangDataTransaksi();
        setVisible(true);
    }

    private void muatUlangDataTransaksi() {
        // Langsung akses ArrayList statis dari DataTransaksi
        List<Transaksi> daftarTransaksi = DataTransaksi.daftarTransaksi;

        textArea.setText("");

        if (daftarTransaksi.isEmpty()) {
            textArea.setText("Belum ada riwayat transaksi.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Transaksi transaksi : daftarTransaksi) {
            sb.append("ID Transaksi: ").append(transaksi.getTransaksiID()).append("\n");
            sb.append("Status      : ").append(transaksi.getStatus()).append("\n");
            sb.append("Produk Dibeli:\n");
            for (KeranjangItem item : transaksi.getItems()) {
                Produk p = item.getProduk(); // Ambil produk dari item
                int jumlah = item.getJumlah(); // Ambil jumlah dari item

                // Tampilkan nama produk, jumlah, dan harga
                sb.append(String.format("  - %-20s (Jumlah: %d) @ Rp %,.0f\n", p.getNama(), jumlah, p.getHarga()));
            }
            sb.append("----------------------------------------\n");
        }
        textArea.setText(sb.toString());
    }
}