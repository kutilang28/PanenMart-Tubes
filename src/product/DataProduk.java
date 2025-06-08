package product;

import java.util.ArrayList;
import java.util.List;

public class DataProduk {
    private static List<Produk> daftarProduk = new ArrayList<>();

    static {
        // Tambahkan Tanaman Hias
        daftarProduk.add(new TanamanHias("Anggrek Bulan", 50000, 10, true, "Ungu"));
        daftarProduk.add(new TanamanHias("Mawar Merah", 45000, 8, true, "Merah"));
        daftarProduk.add(new TanamanHias("Melati", 30000, 15, false, "Putih"));
        daftarProduk.add(new TanamanHias("Kaktus Mini", 25000, 20, true, "Hijau"));
        daftarProduk.add(new TanamanHias("Lavender", 60000, 5, false, "Ungu"));

        // Tambahkan Bibit Tanaman
        daftarProduk.add(new BibitTanaman("Bibit Cabai", 15000, 30, false, 90));
        daftarProduk.add(new BibitTanaman("Bibit Tomat", 12000, 25, true, 80));
        daftarProduk.add(new BibitTanaman("Bibit Bayam", 10000, 50, false, 30));
        daftarProduk.add(new BibitTanaman("Bibit Wortel", 14000, 35, true, 100));
        daftarProduk.add(new BibitTanaman("Bibit Terong", 16000, 28, true, 85));
    }

    public static List<Produk> getDaftarProduk() {
        return daftarProduk;
    }

    public static void tambahProduk(Produk produk) {
        daftarProduk.add(produk);
    }
}