package hr.foi.model;

import static java.lang.Math.sqrt;

public class Artikl {

    private int id;
    private String naziv;
    private int kolicinaNaSkladistu;
    private int minimalneZalihe;
    private double jedinicnaCijena;
    private int godisnjaPotraznja;
    private double troskoviSkladistenja;
    private double troskoviNabave;
    private int idMjere;
    private Mjera mjera;
    private int ekonomicnaKolicinaNarucivanja;

    private String kolicinaNaSkladistuTableCollValue;
    private String minimalnaKolicinaNaSkladistuTableCollValue;

    public Artikl() {
    }

    public Artikl(int id, String naziv, int kolicinaNaSkladistu, int minimalneZalihe, double jedinicnaCijena, int godisnjaPotraznja, double troskoviSkladistenja, double troskoviNabave, int idMjere, Mjera mjera) {
        this.id = id;
        this.naziv = naziv;
        this.kolicinaNaSkladistu = kolicinaNaSkladistu;
        this.minimalneZalihe = minimalneZalihe;
        this.jedinicnaCijena = jedinicnaCijena;
        this.godisnjaPotraznja = godisnjaPotraznja;
        this.troskoviSkladistenja = troskoviSkladistenja;
        this.troskoviNabave = troskoviNabave;
        this.idMjere = idMjere;
        this.mjera = mjera;
        calculateEkonomicnaCijenaNarucivanja();
    }

    private void calculateEkonomicnaCijenaNarucivanja() {
        try{
            ekonomicnaKolicinaNarucivanja = (int)sqrt((2 * troskoviNabave * godisnjaPotraznja) / troskoviSkladistenja);
            if (ekonomicnaKolicinaNarucivanja <= minimalneZalihe) {
                ekonomicnaKolicinaNarucivanja = minimalneZalihe - kolicinaNaSkladistu + 1;
            }

        } catch (Exception e) {
            ekonomicnaKolicinaNarucivanja = 0;
        }
    }

    public int getEkonomicnaKolicinaNarucivanja() {
        calculateEkonomicnaCijenaNarucivanja();
        return ekonomicnaKolicinaNarucivanja;
    }

    public void setEkonomicnaKolicinaNarucivanja(int ekonomicnaKolicinaNarucivanja) {
        this.ekonomicnaKolicinaNarucivanja = ekonomicnaKolicinaNarucivanja;
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

    public int getKolicinaNaSkladistu() {
        return kolicinaNaSkladistu;
    }

    public void setKolicinaNaSkladistu(int kolicinaNaSkladistu) {
        this.kolicinaNaSkladistu = kolicinaNaSkladistu;
    }

    public int getMinimalneZalihe() {
        return minimalneZalihe;
    }

    public void setMinimalneZalihe(int minimalneZalihe) {
        this.minimalneZalihe = minimalneZalihe;
    }

    public double getJedinicnaCijena() {
        return jedinicnaCijena;
    }

    public void setJedinicnaCijena(double jedinicnaCijena) {
        this.jedinicnaCijena = jedinicnaCijena;
    }

    public int getGodisnjaPotraznja() {
        return godisnjaPotraznja;
    }

    public void setGodisnjaPotraznja(int godisnjaPotraznja) {
        this.godisnjaPotraznja = godisnjaPotraznja;
    }

    public double getTroskoviSkladistenja() {
        return troskoviSkladistenja;
    }

    public void setTroskoviSkladistenja(double troskoviSkladistenja) {
        this.troskoviSkladistenja = troskoviSkladistenja;
    }

    public double getTroskoviNabave() {
        return troskoviNabave;
    }

    public void setTroskoviNabave(double troskoviNabave) {
        this.troskoviNabave = troskoviNabave;
    }

    public int getIdMjere() {
        return idMjere;
    }

    public void setIdMjere(int idMjere) {
        this.idMjere = idMjere;
    }

    public String getKolicinaNaSkladistuTableCollValue() {
        kolicinaNaSkladistuTableCollValue = String.valueOf(kolicinaNaSkladistu) + " (" + mjera.getSkracenica() + ")";
        return kolicinaNaSkladistuTableCollValue;
    }

    public void setKolicinaNaSkladistuTableCollValue(String kolicinaNaSkladistuTableCollValue) {
        this.kolicinaNaSkladistuTableCollValue = kolicinaNaSkladistuTableCollValue;
    }

    public Mjera getMjera() {
        return mjera;
    }

    public void setMjera(Mjera mjera) {
        this.mjera = mjera;
    }

    public String getMinimalnaKolicinaNaSkladistuTableCollValue() {
        minimalnaKolicinaNaSkladistuTableCollValue = String.valueOf(minimalneZalihe) + " (" + mjera + ")";
        return minimalnaKolicinaNaSkladistuTableCollValue;
    }

    public void setMinimalnaKolicinaNaSkladistuTableCollValue(String minimalnaKolicinaNaSkladistuTableCollValue) {
        this.minimalnaKolicinaNaSkladistuTableCollValue = minimalnaKolicinaNaSkladistuTableCollValue;
    }

    @Override
    public String toString() {
        return naziv + " " + getKolicinaNaSkladistuTableCollValue();
    }
}
