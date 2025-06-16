package feedback;

import idGenerator.IDGenerator;
import model.User;
import java.util.Date;

public abstract class Feedback {
    private String feedbackID;
    private User customer;
    private Date tanggal;

    public Feedback(User customer) {
        this.feedbackID = IDGenerator.generateTransaksiID(); // Bisa menggunakan ID generator yang sama
        this.customer = customer;
        this.tanggal = new Date();
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public User getCustomer() {
        return customer;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public abstract String getDetail();
}