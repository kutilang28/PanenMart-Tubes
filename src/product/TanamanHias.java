package product;

public class TanamanHias extends Produk {
    private boolean potInclude;
    private String warnaBunga;

    public TanamanHias(String nama, double harga, int stok, boolean potInclude, String warnaBunga) {
        super(nama, harga, stok);
        this.potInclude = potInclude;
        this.warnaBunga = warnaBunga;
    }

    // Getter
    public boolean isPotInclude() {
        return potInclude;
    }

    public String getWarnaBunga() {
        return warnaBunga;
    }
}
