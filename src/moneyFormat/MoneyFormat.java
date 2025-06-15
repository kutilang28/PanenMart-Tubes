package moneyFormat;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyFormat {

    public static String rupiah(double amount) {
        Locale indonesia = Locale.forLanguageTag("id-ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(indonesia);
        formatter.setMaximumFractionDigits(0);
        return formatter.format(amount);
    }
}
