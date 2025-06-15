package transaction;

import idGenerator.IDGenerator;
import java.util.List;
import shoppingCart.KeranjangItem; 

public class Transaksi {
    private String transaksiID;
    // Ubah dari List<Produk> menjadi List<KeranjangItem>
    private List<KeranjangItem> items; 
    private String status;

    /**
     * Konstruktor sekarang menerima List<KeranjangItem> untuk menyimpan
     * produk beserta jumlahnya.
     */
    public Transaksi(List<KeranjangItem> items, String status) {
    	this.transaksiID = IDGenerator.generateOrderID();
        this.items = items;
        this.status = status;
    }

    public String getTransaksiID() {
        return transaksiID;
    }

    /**
     * Getter diubah untuk mengembalikan List<KeranjangItem>.
     */
    public List<KeranjangItem> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}