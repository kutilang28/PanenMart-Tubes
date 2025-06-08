package idGenerator;

import java.util.concurrent.atomic.AtomicInteger;

public class IDGenerator {
    private static final AtomicInteger produkID = new AtomicInteger(1000);
    private static final AtomicInteger userID = new AtomicInteger(5000);
    private static final AtomicInteger orderID = new AtomicInteger(8000);

    public static String generateProdukID() {
        return "P" + produkID.getAndIncrement();
    }

    public static String generateUserID() {
        return "U" + userID.getAndIncrement();
    }

    public static String generateOrderID() {
        return "O" + orderID.getAndIncrement();
    }
}
