package feedback;

import model.User;

public class LayananFeedback extends Feedback {
    private String jenisLayanan;
    private int tingkatPelayanan;

    public LayananFeedback(User customer, String jenisLayanan, int tingkatPelayanan) {
        super(customer);
        this.jenisLayanan = jenisLayanan;
        this.tingkatPelayanan = tingkatPelayanan;
    }

    public String getJenisLayanan() {
        return jenisLayanan;
    }

    public int getTingkatPelayanan() {
        return tingkatPelayanan;
    }

    @Override
    public String getDetail() {
        return "Feedback Layanan: " + jenisLayanan + " - Rating: " + tingkatPelayanan + "/10";
    }
}