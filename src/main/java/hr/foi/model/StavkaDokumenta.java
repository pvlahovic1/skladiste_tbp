package hr.foi.model;

public class StavkaDokumenta {

    private int idArtikla;
    private int idDokumenta;
    private String naziv;
    private int kolicina;
    private String skracenica;

    private String kolicinaTableCollValue;

    public StavkaDokumenta(String naziv, int kolicina, String skracenica) {
        this.naziv = naziv;
        this.kolicina = kolicina;
        this.skracenica = skracenica;
    }

    public StavkaDokumenta(int idArtikla, String naziv, int kolicina, String skracenica) {
        this.idArtikla = idArtikla;
        this.naziv = naziv;
        this.kolicina = kolicina;
        this.skracenica = skracenica;
    }

    public int getIdArtikla() {
        return idArtikla;
    }

    public void setIdArtikla(int idArtikla) {
        this.idArtikla = idArtikla;
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

    public int getIdDokumenta() {
        return idDokumenta;
    }

    public void setIdDokumenta(int idDokumenta) {
        this.idDokumenta = idDokumenta;
    }

    public String getKolicinaTableCollValue() {
        this.kolicinaTableCollValue = kolicina + " " + skracenica;
        return kolicinaTableCollValue;
    }

    public void setKolicinaTableCollValue(String kolicinaTableCollValue) {
        this.kolicinaTableCollValue = kolicinaTableCollValue;
    }

}
