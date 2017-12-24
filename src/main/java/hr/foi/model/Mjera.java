package hr.foi.model;

public class Mjera {

    private int id;
    private String naziv;
    private String skracenica;

    public Mjera() {
    }

    public Mjera(int id, String naziv, String skracenica) {
        this.id = id;
        this.naziv = naziv;
        this.skracenica = skracenica;
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

    public String getSkracenica() {
        return skracenica;
    }

    public void setSkracenica(String skracenica) {
        this.skracenica = skracenica;
    }
}
