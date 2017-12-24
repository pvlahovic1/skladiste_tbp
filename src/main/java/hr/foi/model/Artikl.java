package hr.foi.model;

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

    private String kolicinaNaSkladistuTableCollValue;

    public Artikl() {
    }

    public Artikl(int id, String naziv, int kolicinaNaSkladistu, int minimalneZalihe, double jedinicnaCijena, int godisnjaPotraznja, double troskoviSkladistenja, double troskoviNabave, int idMjere) {
        this.id = id;
        this.naziv = naziv;
        this.kolicinaNaSkladistu = kolicinaNaSkladistu;
        this.minimalneZalihe = minimalneZalihe;
        this.jedinicnaCijena = jedinicnaCijena;
        this.godisnjaPotraznja = godisnjaPotraznja;
        this.troskoviSkladistenja = troskoviSkladistenja;
        this.troskoviNabave = troskoviNabave;
        this.idMjere = idMjere;
    }

    public Artikl(int id, String naziv, int kolicinaNaSkladistu, int minimalneZalihe, double jedinicnaCijena, int godisnjaPotraznja, double troskoviSkladistenja, double troskoviNabave, int idMjere, String kolicinaNaSkladistuTableCollValue) {
        this.id = id;
        this.naziv = naziv;
        this.kolicinaNaSkladistu = kolicinaNaSkladistu;
        this.minimalneZalihe = minimalneZalihe;
        this.jedinicnaCijena = jedinicnaCijena;
        this.godisnjaPotraznja = godisnjaPotraznja;
        this.troskoviSkladistenja = troskoviSkladistenja;
        this.troskoviNabave = troskoviNabave;
        this.idMjere = idMjere;
        this.kolicinaNaSkladistuTableCollValue = kolicinaNaSkladistuTableCollValue;
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
        return kolicinaNaSkladistuTableCollValue;
    }

    public void setKolicinaNaSkladistuTableCollValue(String kolicinaNaSkladistuTableCollValue) {
        this.kolicinaNaSkladistuTableCollValue = kolicinaNaSkladistuTableCollValue;
    }
}
