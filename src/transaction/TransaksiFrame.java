package transaction;

import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class TransaksiFrame extends JFrame {
    private User currentUser;
    private JTable table;
    private DefaultTableModel tableModel;

    public TransaksiFrame(User currentUser) {
        this.currentUser = currentUser;

        setTitle("Daftar Transaksi Saya");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Kolom tabel
        String[] kolom = {"ID Transaksi", "Total Harga", "Status", "Tanggal"};
        tableModel = new DefaultTableModel(kolom, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        loadTransaksi();

        setVisible(true);
    }

    private void loadTransaksi() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (Transaksi t : DataTransaksi.getTransaksiByCustomerID(currentUser.getUserID())) {
            Object[] row = {
                t.getTransaksiID(),
                t.getTotalHarga(),
                t.getStatus(),
                sdf.format(t.getTanggalTransaksi())
            };
            tableModel.addRow(row);
        }
    }
}
