package model;

import java.util.ArrayList;

public class DataUser {

    public static ArrayList<User> userList = defaultUser();

    private static ArrayList<User> defaultUser() {
        ArrayList<User> list = new ArrayList<>();
        list.add(new Admin("admin", "admin@panenmart.com", "admin123"));
        list.add(new Customer("customer", "customer@panenmart.com", "cust123", "08123456789", "Jl. Contoh No. 1"));
        list.add(new Warehouse("warehouse", "warehouse@panenmart.com", "ware123", "0876543210"));
        return list;
    }
    
    public static void hapusUser(String email) {
        userList.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
    }

}