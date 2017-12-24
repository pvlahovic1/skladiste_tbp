package hr.foi.model;

import java.time.LocalDateTime;

public class PoslovniPartner {

    private int id;
    private String oib;
    private String nazivPravneOsobe;
    private String telefon;
    private String email;
    private String adresa;
    private LocalDateTime vrijemeUnosa;

    public PoslovniPartner() {
    }

    public PoslovniPartner(int id, String oib, String nazivPravneOsobe, String telefon, String email, String adresa, LocalDateTime vrijemeUnosa) {
        this.id = id;
        this.oib = oib;
        this.nazivPravneOsobe = nazivPravneOsobe;
        this.telefon = telefon;
        this.email = email;
        this.adresa = adresa;
        this.vrijemeUnosa = vrijemeUnosa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNazivPravneOsobe() {
        return nazivPravneOsobe;
    }

    public void setNazivPravneOsobe(String nazivPravneOsobe) {
        this.nazivPravneOsobe = nazivPravneOsobe;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public LocalDateTime getVrijemeUnosa() {
        return vrijemeUnosa;
    }

    public void setVrijemeUnosa(LocalDateTime vrijemeUnosa) {
        this.vrijemeUnosa = vrijemeUnosa;
    }
}
