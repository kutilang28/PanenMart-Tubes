package transaction;

import java.util.ArrayList;
import java.util.List;

import product.BibitTanaman;
import product.Produk;
import product.TanamanHias;

public class DataTransaksi {
    public static List<Transaksi> daftarTransaksi = defaultTransaksi();

    private static List<Transaksi> defaultTransaksi() {
        List<Transaksi> list = new ArrayList<>();

        List<Produk> order1 = new ArrayList<>();
        order1.add(new TanamanHias("Anggrek Bulan", 40000, 5, true, "Ungu"));
        list.add(new Transaksi(order1, "Diproses"));

        List<Produk> order2 = new ArrayList<>();
        order2.add(new BibitTanaman("Cabai Rawit", 10000.0, 10, false, 90));
        list.add(new Transaksi(order2, "Dikirim"));

        return list;
    }
}
