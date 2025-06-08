package product;

import idGenerator.IDGenerator;

public abstract class Produk {
    protected String produkID;
    protected String nama;
    protected double harga;
    protected int stok;

    public Produk(String nama, double harga, int stok) {
        this.produkID = IDGenerator.generateProdukID();
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    // Getter
    public String getProdukID() {
        return produkID;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    // Method fungsional
    public void tambahStok(int jumlah) {
        this.stok += jumlah;
    }

    public void kurangiStok(int jumlah) {
        if (jumlah <= this.stok) {
            this.stok -= jumlah;
        }
    }
}
