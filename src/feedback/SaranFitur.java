package feedback;

import model.User;

public class SaranFitur extends Feedback {
    private String judulFitur;
    private String deskripsiFitur;

    public SaranFitur(User customer, String judulFitur, String deskripsiFitur) {
        super(customer);
        this.judulFitur = judulFitur;
        this.deskripsiFitur = deskripsiFitur;
    }

    public String getJudulFitur() {
        return judulFitur;
    }

    public String getDeskripsiFitur() {
        return deskripsiFitur;
    }

    @Override
    public String getDetail() {
        return "Saran Fitur: " + judulFitur;
    }
}