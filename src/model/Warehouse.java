package model;

public class Warehouse extends User {
    private String noTelp; // Atribut tambahan untuk Warehouse

    public Warehouse(String name, String email, String password, String noTelp) {
        super(name, email, password);
        this.noTelp = noTelp;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    @Override
    public String getRole() {
        return "Warehouse"; // Mengembalikan peran pengguna
    }

    // Metode spesifik Warehouse (contoh saja, tidak digunakan di GUI ini)
    public void addProduct() { // Mengelola stok barang [cite: 40]
        System.out.println("Warehouse " + name + " adding product.");
    }

    public void checkStock() { // Mengelola stok barang [cite: 40]
        System.out.println("Warehouse " + name + " checking stock.");
    }

    public void getOrder() { // Memproses pesanan dan pembayaran [cite: 40]
        System.out.println("Warehouse " + name + " getting order details.");
    }
}