package transaction;

import javax.swing.*;
import java.awt.*;

public class TransaksiFrame extends JFrame {
    public TransaksiFrame() {
        setTitle("Riwayat Transaksi");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        for (Transaksi transaksi : DataTransaksi.daftarTransaksi) {
            textArea.append("Transaksi ID: " + transaksi.getTransaksiID() + "\n");
            textArea.append("Status: " + transaksi.getStatus() + "\nProduk:\n");
            for (var p : transaksi.getDaftarProduk()) {
                textArea.append("- " + p.getNama() + " (Rp " + p.getHarga() + ")\n");
            }
            textArea.append("-----\n");
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
