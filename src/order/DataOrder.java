package order;

import model.*;

import product.DataProduk;
import product.Produk;

import java.util.ArrayList;
import java.util.List;

public class DataOrder {
    public static List<Order> orderList = defaultOrders();

    private static List<Order> defaultOrders() {
        List<Order> list = new ArrayList<>();

        // Contoh order 1 (customer1 beli 2 produk)
        User customer1 = DataUser.userList.stream()
            .filter(u -> u.getRole().equals("Customer"))
            .findFirst()
            .orElse(null);

        if (customer1 != null) {
            Order order1 = new Order(customer1);
            Produk p1 = DataProduk.getDaftarProduk().get(0);
            Produk p2 = DataProduk.getDaftarProduk().get(1);
            order1.addItem(new OrderItem(p1, 2));
            order1.addItem(new OrderItem(p2, 1));
            order1.setStatus(OrderStatus.DIPROSES);
            list.add(order1);

            // Contoh order 2
            Order order2 = new Order(customer1);
            Produk p3 = DataProduk.getDaftarProduk().get(2);
            order2.addItem(new OrderItem(p3, 3));
            order2.setStatus(OrderStatus.DIANTAR);
            list.add(order2);
        }
        return list;
    }

	public static List<Order> getOrderList() {
		return orderList;
	}
}
