package main;

import model.*;
import moneyFormat.MoneyFormat;
import transaction.*;
import product.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Dashboard_Warehouse extends JFrame {
    private JTable productTable, orderTable, itemTable;
    private DefaultTableModel productTableModel, orderTableModel, itemTableModel;
    private JButton ubahStatusButton;

    public Dashboard_Warehouse(User warehouseUser) {
        setTitle("Dashboard Warehouse");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel Produk
        JPanel productPanel = new JPanel(new BorderLayout(10, 10));
        productTableModel = new DefaultTableModel(new Object[]{"ID", "Nama", "Harga", "Stok", "Kategori"}, 0);
        productTable = new JTable(productTableModel);
        loadProductData();

        JPanel productButtonPanel = new JPanel();
        JButton addButton = new JButton("Tambah Produk");
        JButton editButton = new JButton("Edit Produk");
        JButton deleteButton = new JButton("Hapus Produk");
        JButton loggout = new JButton("Log Out");

        productButtonPanel.add(addButton);
        productButtonPanel.add(editButton);
        productButtonPanel.add(deleteButton);
        productButtonPanel.add(loggout);

        addButton.addActionListener(e -> new FormProdukFrame(productTableModel, null));
        editButton.addActionListener(e -> editSelectedProduct());
        deleteButton.addActionListener(e -> deleteSelectedProduct());
        loggout.addActionListener(e -> loggout());

        productPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);
        productPanel.add(productButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Kelola Produk", productPanel);

        // Panel Order
        JPanel orderPanel = new JPanel(new BorderLayout(10, 10));

        orderTableModel = new DefaultTableModel(new Object[]{"Transaksi ID", "Customer", "Tanggal", "Total", "Status"}, 0);
        orderTable = new JTable(orderTableModel);
        loadOrderData();

        // Tabel item detail
        itemTableModel = new DefaultTableModel(new Object[]{"Produk", "Jumlah", "Harga Satuan", "Total"}, 0);
        itemTable = new JTable(itemTableModel);

        orderTable.getSelectionModel().addListSelectionListener(e -> tampilkanDetailTransaksi());

        // Tombol ubah status
        ubahStatusButton = new JButton("Ubah Status");
        ubahStatusButton.setEnabled(false);
        ubahStatusButton.addActionListener(e -> ubahStatusTransaksi());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(new JScrollPane(itemTable), BorderLayout.CENTER);
        bottomPanel.add(ubahStatusButton, BorderLayout.SOUTH);

        orderPanel.add(new JScrollPane(orderTable), BorderLayout.CENTER);
        orderPanel.add(bottomPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Daftar Order", orderPanel);
        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private void loadProductData() {
        productTableModel.setRowCount(0);
        for (Produk p : DataProduk.getDaftarProduk()) {
            String kategori = (p instanceof TanamanHias) ? "Tanaman Hias" : "Bibit Tanaman";
            productTableModel.addRow(new Object[]{p.getProdukID(), p.getNama(), MoneyFormat.rupiah(p.getHarga()), p.getStok(), kategori});
        }
    }

    private void loadOrderData() {
        orderTableModel.setRowCount(0);
        for (Transaksi t : DataTransaksi.getSemuaTransaksi()) {
            orderTableModel.addRow(new Object[]{
                t.getTransaksiID(),
                t.getCustomer().getName(),
                t.getTanggalTransaksi(),
                MoneyFormat.rupiah(t.getTotalHarga()),
                t.getStatus()
            });
        }
    }

    private void tampilkanDetailTransaksi() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            String transaksiID = (String) orderTableModel.getValueAt(selectedRow, 0);
            Transaksi transaksi = DataTransaksi.cariTransaksiByID(transaksiID);
            if (transaksi == null) return;

            // Tampilkan item
            itemTableModel.setRowCount(0);
            for (TransaksiItem item : transaksi.getItems()) {
                itemTableModel.addRow(new Object[]{
                    item.getProduk().getNama(),
                    item.getJumlah(),
                    MoneyFormat.rupiah(item.getProduk().getHarga()),
                    MoneyFormat.rupiah(item.getSubtotal())
                });
            }

            // Atur status tombol
            if (transaksi.getStatus() == TransaksiStatus.SELESAI) {
                ubahStatusButton.setEnabled(false);
            } else {
                ubahStatusButton.setEnabled(true);
            }
        } else {
            itemTableModel.setRowCount(0);
            ubahStatusButton.setEnabled(false);
        }
    }

    private void ubahStatusTransaksi() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            String transaksiID = (String) orderTableModel.getValueAt(selectedRow, 0);
            Transaksi transaksi = DataTransaksi.cariTransaksiByID(transaksiID);
            if (transaksi == null) return;

            // Ubah status
            if (transaksi.getStatus() == TransaksiStatus.DIPROSES) {
                transaksi.setStatus(TransaksiStatus.DIANTAR);
            } else if (transaksi.getStatus() == TransaksiStatus.DIANTAR) {
                transaksi.setStatus(TransaksiStatus.SELESAI);
                ubahStatusButton.setEnabled(false);
            }

            // Refresh tabel
            loadOrderData();
            tampilkanDetailTransaksi();
        }
    }

    private void editSelectedProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            String idProduk = (String) productTableModel.getValueAt(selectedRow, 0);
            Produk produk = DataProduk.cariProdukByID(idProduk);
            if (produk != null) {
                new FormProdukFrame(productTableModel, produk);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih produk yang akan diedit.");
        }
    }

    private void deleteSelectedProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            String idProduk = (String) productTableModel.getValueAt(selectedRow, 0);
            DataProduk.hapusProduk(idProduk);
            productTableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Pilih produk yang akan dihapus.");
        }
    }

    private void loggout() {
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginPage().setVisible(true);
        }
    }
}
