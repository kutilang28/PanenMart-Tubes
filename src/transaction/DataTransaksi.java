package transaction;

import java.util.*;

public class DataTransaksi {
    private static final Map<String, List<Transaksi>> transaksiMap = new HashMap<>();

    // Menambahkan transaksi untuk customer tertentu
    public static void tambahTransaksi(String customerID, Transaksi transaksi) {
        transaksiMap.computeIfAbsent(customerID, k -> new ArrayList<>()).add(transaksi);
    }

    // Mengambil daftar transaksi berdasarkan customerID
    public static List<Transaksi> getTransaksiByCustomerID(String customerID) {
        return transaksiMap.getOrDefault(customerID, new ArrayList<>());
    }
    
    // Mencari daftar transaksi berdasarkan transaksiID
    public static Transaksi cariTransaksiByID(String id) {
        for (Transaksi t : getSemuaTransaksi()) {
            if (t.getTransaksiID().equals(id)) return t;
        }
        return null;
    }

    // Mengambil semua transaksi dari seluruh customer
    public static List<Transaksi> getSemuaTransaksi() {
        List<Transaksi> semua = new ArrayList<>();
        for (List<Transaksi> list : transaksiMap.values()) {
            semua.addAll(list);
        }
        return semua;
    }
}
