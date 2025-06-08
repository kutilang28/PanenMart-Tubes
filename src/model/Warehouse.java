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
        return "Warehouse";
    }

    public void addProduct() {
        System.out.println("Warehouse " + name + " adding product.");
    }

    public void checkStock() {
        System.out.println("Warehouse " + name + " checking stock.");
    }

    public void getOrder() {
        System.out.println("Warehouse " + name + " getting order details.");
    }
}