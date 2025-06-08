package order;

import model.*;

import idGenerator.IDGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String orderID;
    private User customer;
    private List<OrderItem> items;
    private OrderStatus status;
    private Date tanggalOrder;

    public Order(User customer) {
        this.orderID = IDGenerator.generateOrderID();
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = OrderStatus.DIPROSES;
        this.tanggalOrder = new Date();
    }

    public String getOrderID() {
        return orderID;
    }

    public User getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getTanggalOrder() {
        return tanggalOrder;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public double getTotalHarga() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }
}
