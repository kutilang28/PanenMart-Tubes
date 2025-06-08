package model;

public class Customer extends User {
    private String noTelp;
    private String address;

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
        return "Customer";
    }

}