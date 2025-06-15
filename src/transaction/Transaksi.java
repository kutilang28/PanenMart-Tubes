package transaction;

import model.User;
import idGenerator.IDGenerator;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;


public class Transaksi {
	private String transaksiID;
    private User customer;
    private List<TransaksiItem> items;
    private TransaksiStatus status;
    private Date tanggalTransaksi;

    public Transaksi(User customer) {
        this.transaksiID = IDGenerator.generateTransaksiID();
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = TransaksiStatus.DIPROSES;
        this.tanggalTransaksi = new Date();
    }

    public String getTransaksiID() {
        return transaksiID;
    }

    public User getCustomer() {
        return customer;
    }

    public List<TransaksiItem> getItems() {
        return items;
    }

    public TransaksiStatus getStatus() {
        return status;
    }

    public void setStatus(TransaksiStatus status) {
        this.status = status;
    }

    public Date getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void addItem(TransaksiItem item) {
        items.add(item);
    }

    public double getTotalHarga() {
        double total = 0;
        for (TransaksiItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }
}
