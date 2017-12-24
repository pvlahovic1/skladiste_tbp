package hr.foi.model;

public class TipDokumenta {

    private int id;
    private String naziv;
    private String akcija;

    public TipDokumenta() {
    }

    public TipDokumenta(int id, String naziv, String akcija) {
        this.id = id;
        this.naziv = naziv;
        this.akcija = akcija;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAkcija() {
        return akcija;
    }

    public void setAkcija(String akcija) {
        this.akcija = akcija;
    }
}
