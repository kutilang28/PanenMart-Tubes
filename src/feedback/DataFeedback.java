package feedback;

import java.util.ArrayList;
import java.util.List;

public class DataFeedback {
    private static List<Feedback> feedbackList = new ArrayList<>();

    public static void tambahFeedback(Feedback feedback) {
        feedbackList.add(feedback);
        System.out.println("Feedback baru ditambahkan: " + feedback.getDetail());
    }

    public static List<Feedback> getSemuaFeedback() {
        return feedbackList;
    }
}