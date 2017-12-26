package hr.foi.model;

public class StavkaDokumenta {

    private String naziv;
    private int kolicina;
    private String skracenica;

    private String kolicinaTableCollValue;

    public StavkaDokumenta(String naziv, int kolicina, String skracenica) {
        this.naziv = naziv;
        this.kolicina = kolicina;
        this.skracenica = skracenica;
        this.kolicinaTableCollValue = kolicina + " " + skracenica;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public String getSkracenica() {
        return skracenica;
    }

    public void setSkracenica(String skracenica) {
        this.skracenica = skracenica;
    }

    public String getKolicinaTableCollValue() {
        return kolicinaTableCollValue;
    }

    public void setKolicinaTableCollValue(String kolicinaTableCollValue) {
        this.kolicinaTableCollValue = kolicinaTableCollValue;
    }

}
