package hr.foi.model;

import java.time.LocalDate;

public class Povijest {

    private int id;
    private LocalDate vremenskaOznaka;
    private Integer kolicina;
    private int idArtikla;
    private String nazivArtikla;

    public Povijest(int id, LocalDate vremenskaOznaka, Integer kolicina, int idArtikla, String nazivArtikla) {
        this.id = id;
        this.vremenskaOznaka = vremenskaOznaka;
        this.kolicina = kolicina;
        this.idArtikla = idArtikla;
        this.nazivArtikla = nazivArtikla;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getVremenskaOznaka() {
        return vremenskaOznaka;
    }

    public void setVremenskaOznaka(LocalDate vremenskaOznaka) {
        this.vremenskaOznaka = vremenskaOznaka;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public int getIdArtikla() {
        return idArtikla;
    }

    public void setIdArtikla(int idArtikla) {
        this.idArtikla = idArtikla;
    }

    public String getNazivArtikla() {
        return nazivArtikla;
    }

    public void setNazivArtikla(String nazivArtikla) {
        this.nazivArtikla = nazivArtikla;
    }
}
