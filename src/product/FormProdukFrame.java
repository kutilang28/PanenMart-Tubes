package product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import idGenerator.IDGenerator;

import java.awt.*;
import java.awt.event.ActionEvent;

public class FormProdukFrame extends JFrame {
    private JTextField namaField, hargaField, stokField;
    private JCheckBox potCheckBox;
    private JTextField warnaField, panenField;
    private JComboBox<String> kategoriBox;

    private DefaultTableModel tableModel;
    private Produk produkEdit;

    public FormProdukFrame(DefaultTableModel productTableModel, Produk produk) {
        this.tableModel = productTableModel;
        this.produkEdit = produk;

        setTitle(produk == null ? "Tambah Produk" : "Edit Produk");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0, 2, 10, 10));

        kategoriBox = new JComboBox<>(new String[]{"Tanaman Hias", "Bibit Tanaman"});
        namaField = new JTextField();
        hargaField = new JTextField();
        stokField = new JTextField();
        potCheckBox = new JCheckBox("Termasuk Pot");
        warnaField = new JTextField();
        panenField = new JTextField();

        add(new JLabel("Kategori:"));
        add(kategoriBox);
        add(new JLabel("Nama Produk:"));
        add(namaField);
        add(new JLabel("Harga:"));
        add(hargaField);
        add(new JLabel("Stok:"));
        add(stokField);
        add(new JLabel("Pot Termasuk:"));
        add(potCheckBox);
        add(new JLabel("Warna Bunga:"));
        add(warnaField);
        add(new JLabel("Masa Panen (hari):"));
        add(panenField);

        JButton simpanButton = new JButton("Simpan");
        add(simpanButton);

        JButton batalButton = new JButton("Batal");
        add(batalButton);

        // Event
        simpanButton.addActionListener(this::simpanProduk);
        batalButton.addActionListener(e -> dispose());

        kategoriBox.addActionListener(e -> toggleInputFields());

        if (produk != null) {
            isiFormProduk(produk);
        } else {
            toggleInputFields();
        }

        setVisible(true);
    }

    private void toggleInputFields() {
        String kategori = (String) kategoriBox.getSelectedItem();
        boolean isHias = kategori.equals("Tanaman Hias");

        warnaField.setEnabled(isHias);
        panenField.setEnabled(!isHias);
    }

    private void isiFormProduk(Produk p) {
        namaField.setText(p.getNama());
        hargaField.setText(String.valueOf(p.getHarga()));
        stokField.setText(String.valueOf(p.getStok()));
        potCheckBox.setSelected(false);

        if (p instanceof TanamanHias hias) {
            kategoriBox.setSelectedItem("Tanaman Hias");
            potCheckBox.setSelected(hias.isPotInclude());
            warnaField.setText(hias.getWarnaBunga());
        } else if (p instanceof BibitTanaman bibit) {
            kategoriBox.setSelectedItem("Bibit Tanaman");
            potCheckBox.setSelected(bibit.isPotInclude());
            panenField.setText(String.valueOf(bibit.getMasaPanen()));
        }

        kategoriBox.setEnabled(false); // Saat edit, kategori dikunci
        toggleInputFields();
    }

    private void simpanProduk(ActionEvent e) {
        String nama = namaField.getText().trim();
        String hargaText = hargaField.getText().trim();
        String stokText = stokField.getText().trim();

        if (nama.isEmpty() || hargaText.isEmpty() || stokText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap isi semua field dasar.");
            return;
        }

        try {
            double harga = Double.parseDouble(hargaText);
            int stok = Integer.parseInt(stokText);
            boolean pot = potCheckBox.isSelected();

            if (produkEdit == null) {
                Produk produkBaru = null;
                if (kategoriBox.getSelectedItem().equals("Tanaman Hias")) {
                    String warna = warnaField.getText().trim();
                    produkBaru = new TanamanHias(nama, harga, stok, pot, warna);
                } else {
                    int masaPanen = Integer.parseInt(panenField.getText().trim());
                    produkBaru = new BibitTanaman(nama, harga, stok, pot, masaPanen);
                }
                DataProduk.tambahProduk(produkBaru);
            } else {
                produkEdit.setNama(nama);
                produkEdit.setHarga(harga);
                produkEdit.setStok(stok);

                if (produkEdit instanceof TanamanHias hias) {
                    hias.setPotInclude(pot);
                    hias.setWarnaBunga(warnaField.getText().trim());
                } else if (produkEdit instanceof BibitTanaman bibit) {
                    bibit.setPotInclude(pot);
                    bibit.setMasaPanen(Integer.parseInt(panenField.getText().trim()));
                }
            }

            perbaruiTable();
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga, stok, dan masa panen harus berupa angka.");
        }
    }

    private void perbaruiTable() {
        tableModel.setRowCount(0);
        for (Produk p : DataProduk.getDaftarProduk()) {
            String kategori = (p instanceof TanamanHias) ? "Tanaman Hias" : "Bibit Tanaman";
            tableModel.addRow(new Object[]{
                    p.getProdukID(),
                    p.getNama(),
                    "Rp " + String.format("%.0f", p.getHarga()),
                    p.getStok(),
                    kategori
            });
        }
    }
}
