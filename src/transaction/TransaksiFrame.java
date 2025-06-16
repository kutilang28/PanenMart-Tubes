package transaction;

import model.User;
import moneyFormat.MoneyFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class TransaksiFrame extends JFrame {
    private User currentUser;
    private JTable transaksiTable;
    private JTable detailTable;
    private DefaultTableModel transaksiTableModel;
    private DefaultTableModel detailTableModel;

    public TransaksiFrame(User currentUser) {
        this.currentUser = currentUser;

        setTitle("Daftar Transaksi Saya");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Transaksi Anda", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        headerLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(headerLabel, BorderLayout.NORTH);

        // Tabel transaksi
        String[] kolomTransaksi = {"ID Transaksi", "Total Harga", "Status", "Tanggal"};
        transaksiTableModel = new DefaultTableModel(kolomTransaksi, 0);
        transaksiTable = new JTable(transaksiTableModel);
        JScrollPane transaksiScroll = new JScrollPane(transaksiTable);

        // Tabel detail
        String[] kolomDetail = {"Nama Produk", "Jumlah", "Harga Satuan", "Subtotal"};
        detailTableModel = new DefaultTableModel(kolomDetail, 0);
        detailTable = new JTable(detailTableModel);
        JScrollPane detailScroll = new JScrollPane(detailTable);

        // Panel split
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, transaksiScroll, detailScroll);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);

        // Load transaksi
        loadTransaksi();

        // Listener untuk menampilkan detail saat transaksi diklik
        transaksiTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = transaksiTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String idTransaksi = (String) transaksiTableModel.getValueAt(selectedRow, 0);
                    tampilkanDetailTransaksi(idTransaksi);
                }
            }
        });

        setVisible(true);
    }

    private void loadTransaksi() {
        transaksiTableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        List<Transaksi> daftar = DataTransaksi.getTransaksiByCustomerID(currentUser.getUserID());
        for (Transaksi t : daftar) {
            transaksiTableModel.addRow(new Object[]{
                t.getTransaksiID(),
                MoneyFormat.rupiah(t.getTotalHarga()),
                t.getStatus().name(),
                sdf.format(t.getTanggalTransaksi())
            });
        }
    }

    private void tampilkanDetailTransaksi(String idTransaksi) {
        detailTableModel.setRowCount(0);

        List<Transaksi> daftar = DataTransaksi.getTransaksiByCustomerID(currentUser.getUserID());
        for (Transaksi t : daftar) {
            if (t.getTransaksiID().equals(idTransaksi)) {
                for (TransaksiItem item : t.getItems()) {
                    detailTableModel.addRow(new Object[]{
                        item.getProduk().getNama(),
                        item.getJumlah(),
                        MoneyFormat.rupiah(item.getProduk().getHarga()),
                        MoneyFormat.rupiah(item.getSubtotal())
                    });
                }
                break;
            }
        }
    }
}
