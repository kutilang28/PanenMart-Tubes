package main;

import model.*;
import order.*;
import product.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class Dashboard_Warehouse extends JFrame {
    private JTable productTable;
    private DefaultTableModel productTableModel;
    private JTable orderTable;
    private DefaultTableModel orderTableModel;

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
        orderTableModel = new DefaultTableModel(new Object[]{"Order ID", "Customer", "Tanggal", "Total", "Status"}, 0);
        orderTable = new JTable(orderTableModel);
        loadOrderData();

        orderPanel.add(new JScrollPane(orderTable), BorderLayout.CENTER);
        tabbedPane.addTab("Daftar Order", orderPanel);

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private void loadProductData() {
        productTableModel.setRowCount(0);
        for (Produk p : DataProduk.getDaftarProduk()) {
            String kategori = (p instanceof TanamanHias) ? "Tanaman Hias" : "Bibit Tanaman";
            productTableModel.addRow(new Object[]{p.getProdukID(), p.getNama(), p.getHarga(), p.getStok(), kategori});
        }
    }

    private void loadOrderData() {
        orderTableModel.setRowCount(0);
        for (Order order : DataOrder.getOrderList()) {
            orderTableModel.addRow(new Object[]{
                order.getOrderID(),
                order.getCustomer().getName(),
                order.getTanggalOrder().toString(),
                order.getTotalHarga(),
                order.getStatus().toString()
            });
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
    	this.dispose();
    	LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }
}
