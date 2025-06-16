package shoppingCart;

import java.util.ArrayList;
import java.util.List;

import product.Produk;

public class KeranjangBelanja {
    private List<KeranjangItem> itemList;

    public KeranjangBelanja() {
        this.itemList = new ArrayList<>();
    }
    
    public void tambahProduk(Produk produk) {
        tambahProduk(produk, 1);
    }

    public void tambahProduk(Produk produk, int jumlah) {
        for (KeranjangItem item : itemList) {
            if (item.getProduk().equals(produk)) {
                item.tambahJumlah(jumlah);
                return;
            }
        }
        itemList.add(new KeranjangItem(produk, jumlah));
    }

    public void hapusProduk(Produk produk) {
        itemList.removeIf(item -> item.getProduk().equals(produk));
    }

    public List<KeranjangItem> getDaftarItem() {
        return itemList;
    }

    public double getTotalKeranjang() {
        double total = 0;
        for (KeranjangItem item : itemList) {
            total += item.getTotalHarga();
        }
        return total;
    }

    public void tampilkanKeranjang() {
        System.out.println("Isi Keranjang:");
        for (KeranjangItem item : itemList) {
            System.out.println("- " + item.getProduk().getNama() + " x" + item.getJumlah() + 
                               " = Rp " + item.getTotalHarga());
        }
        System.out.println("Total: Rp " + getTotalKeranjang());
    }
    
    public void kosongkanKeranjang() {
        itemList.clear();
    }
}

