package product;

import idGenerator.IDGenerator;

public abstract class Produk {
    private String produkID;
    private String nama;
    private double harga;
    private int stok;
    private boolean potInclude;
    

    public Produk(String nama, double harga, int stok, boolean potInclude) {
        this.produkID = IDGenerator.generateProdukID();
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
        this.potInclude = potInclude;
    }

    // Getter
    public String getProdukID() {
        return produkID;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    // Method fungsional
    public void tambahStok(int jumlah) {
        this.stok += jumlah;
    }

    public void kurangiStok(int jumlah) {
        if (jumlah <= this.stok) {
            this.stok -= jumlah;
        }
    }

	public void setNama(String nama) {
		this.nama = nama;
	}

	public void setHarga(double harga) {
		this.harga = harga;
	}

	public void setStok(int stok) {
		this.stok = stok;
	}

	public boolean isPotInclude() {
		return potInclude;
	}

	public void setPotInclude(boolean potInclude) {
		this.potInclude = potInclude;
	}
}
