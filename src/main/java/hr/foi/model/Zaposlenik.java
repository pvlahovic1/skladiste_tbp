package hr.foi.model;

import java.time.LocalDateTime;

public class Zaposlenik {

    private int id;
    private String oib;
    private String ime;
    private String prezime;
    private String emai;
    private String adresa;
    private String telefon;
    private LocalDateTime vrijemeUnosa;

    public Zaposlenik() {
    }

    public Zaposlenik(int id, String oib, String ime, String prezime, String emai, String adresa, String telefon, LocalDateTime vrijemeUnosa) {
        this.id = id;
        this.oib = oib;
        this.ime = ime;
        this.prezime = prezime;
        this.emai = emai;
        this.adresa = adresa;
        this.telefon = telefon;
        this.vrijemeUnosa = vrijemeUnosa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmai() {
        return emai;
    }

    public void setEmai(String emai) {
        this.emai = emai;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public LocalDateTime getVrijemeUnosa() {
        return vrijemeUnosa;
    }

    public void setVrijemeUnosa(LocalDateTime vrijemeUnosa) {
        this.vrijemeUnosa = vrijemeUnosa;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }
}
