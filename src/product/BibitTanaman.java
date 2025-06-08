package product;

public class BibitTanaman extends Produk {
    private boolean potInclude;
    private int masaPanen; // dalam hari

    public BibitTanaman(String nama, double harga, int stok, boolean potInclude, int masaPanen) {
        super(nama, harga, stok);
        this.potInclude = potInclude;
        this.masaPanen = masaPanen;
    }

    // Getter
    public boolean isPotInclude() {
        return potInclude;
    }

    public int getMasaPanen() {
        return masaPanen;
    }
}
