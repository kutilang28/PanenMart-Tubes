package model;

public class Customer extends User {
    private String noTelp; // Atribut tambahan untuk Customer [cite: 36]
    private String address; // Atribut tambahan untuk Customer [cite: 36]

    public Customer(String name, String email, String password, String noTelp, String address) {
        super(name, email, password);
        this.noTelp = noTelp;
        this.address = address;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String getRole() {
        return "Customer"; // Mengembalikan peran pengguna
    }

    // Metode spesifik Customer (contoh saja, tidak digunakan di GUI ini)
    public void searchProduct() { // Mencari dan memilih produk. [cite: 37]
        System.out.println("Customer " + name + " searching for products.");
    }
}