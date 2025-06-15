package idGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class IDGenerator {
    private static final AtomicInteger produkCounter = new AtomicInteger(1);
    private static final AtomicInteger userCounter = new AtomicInteger(1);
    private static final AtomicInteger orderCounter = new AtomicInteger(1);

    private static String getToday() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static String generateProdukID() {
        return "PRD-" + getToday() + "-" + String.format("%04d", produkCounter.getAndIncrement());
    }

    public static String generateUserID() {
        return "USR-" + getToday() + "-" + String.format("%04d", userCounter.getAndIncrement());
    }

    public static String generateTransaksiID() {
        return "TRS-" + getToday() + "-" + String.format("%04d", orderCounter.getAndIncrement());
    }
}
