package transaction;

import product.Produk;
import idGenerator.IDGenerator;

import java.util.List;


public class Transaksi {
    private String transaksiID;
    private List<Produk> daftarProduk;
    private String status; // "Diproses", "Dikirim", "Telah Sampai"

    public Transaksi(List<Produk> daftarProduk, String status) {
    	this.transaksiID = IDGenerator.generateOrderID();
        this.daftarProduk = daftarProduk;
        this.status = status;
    }

    public String getTransaksiID() {
        return transaksiID;
    }

    public List<Produk> getDaftarProduk() {
        return daftarProduk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
