package Feedback;

public abstract class Feedback {
    private String idFeedBack;
    private String comment;
    private String kategori;
    private Date tanggal;

    public Feedback(String idFeedBack, String comment, String kategori, Date tanggal) {
        this.idFeedBack = idFeedBack;
        this.comment = comment;
        
        this.kategori = kategori;
        this.tanggal = tanggal;
    }

    public abstract void submitFeedBack();

    public abstract void tampilkan();
}
