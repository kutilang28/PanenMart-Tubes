package model;

public class Admin extends User{
	public Admin(String name, String email, String password) {
        super(name, email, password);
    }

    @Override
    public String getRole() {
        return "Admin"; // Mengembalikan peran pengguna [cite: 6]
    }

    // Metode spesifik Admin (contoh saja, tidak digunakan di GUI ini)
    public void manageUser() {
        System.out.println("Admin " + name + " managing users.");
    }
}
