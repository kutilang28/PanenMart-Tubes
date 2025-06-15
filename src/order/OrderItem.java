package order;

import product.Produk;

public class OrderItem {
    private Produk produk;
    private int jumlah;

    public OrderItem(Produk produk, int jumlah) {
        this.produk = produk;
        this.jumlah = jumlah;
    }

    public Produk getProduk() {
        return produk;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double getSubtotal() {
        return produk.getHarga() * jumlah;
    }
}