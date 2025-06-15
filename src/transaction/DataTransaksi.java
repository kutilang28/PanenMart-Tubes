package transaction;

import java.util.ArrayList;

public class DataTransaksi {
    public static ArrayList<Transaksi> daftarTransaksi = new ArrayList<>();

   
    public static void tambahTransaksi(Transaksi transaksi) {
        daftarTransaksi.add(transaksi);
    }
}
