package product;

public class BibitTanaman extends Produk {
	private boolean potInclude;
    private int masaPanen;

    public BibitTanaman(String nama, double harga, int stok, boolean potInclude, int masaPanen) {
        super(nama, harga, stok, potInclude);
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

	public void setPotInclude(boolean potInclude) {
		this.potInclude = potInclude;
	}

	public void setMasaPanen(int masaPanen) {
		this.masaPanen = masaPanen;
	}
}
