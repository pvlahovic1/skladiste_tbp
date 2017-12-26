package hr.foi.model;

import java.time.LocalDateTime;

public class Dokument {

    private int id;
    private LocalDateTime datumKreiranja;
    private int idTipDokument;
    private TipDokumenta tipDokumenta;
    private int idZaposlenika;
    private Zaposlenik zaposlenik;
    private int idPoslovnigPartnera;
    private PoslovniPartner poslovniPartner;

    public Dokument() {
    }

    public Dokument(int id, LocalDateTime datumKreiranja, TipDokumenta tipDokumenta, Zaposlenik zaposlenik, PoslovniPartner poslovniPartner) {
        this.id = id;
        this.datumKreiranja = datumKreiranja;
        this.tipDokumenta = tipDokumenta;
        this.zaposlenik = zaposlenik;
        this.poslovniPartner = poslovniPartner;
        this.idTipDokument = tipDokumenta.getId();
        this.idZaposlenika = zaposlenik.getId();
        this.idPoslovnigPartnera = poslovniPartner.getId();
    }

    public Dokument(int idTipDokument, int idZaposlenika, int idPoslovnigPartnera) {
        this.idTipDokument = idTipDokument;
        this.idZaposlenika = idZaposlenika;
        this.idPoslovnigPartnera = idPoslovnigPartnera;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(LocalDateTime datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public int getIdTipDokument() {
        return idTipDokument;
    }

    public void setIdTipDokument(int idTipDokument) {
        this.idTipDokument = idTipDokument;
    }

    public TipDokumenta getTipDokumenta() {
        return tipDokumenta;
    }

    public void setTipDokumenta(TipDokumenta tipDokumenta) {
        this.tipDokumenta = tipDokumenta;
    }

    public int getIdZaposlenika() {
        return idZaposlenika;
    }

    public void setIdZaposlenika(int idZaposlenika) {
        this.idZaposlenika = idZaposlenika;
    }

    public Zaposlenik getZaposlenik() {
        return zaposlenik;
    }

    public void setZaposlenik(Zaposlenik zaposlenik) {
        this.zaposlenik = zaposlenik;
    }

    public int getIdPoslovnigPartnera() {
        return idPoslovnigPartnera;
    }

    public void setIdPoslovnigPartnera(int idPoslovnigPartnera) {
        this.idPoslovnigPartnera = idPoslovnigPartnera;
    }

    public PoslovniPartner getPoslovniPartner() {
        return poslovniPartner;
    }

    public void setPoslovniPartner(PoslovniPartner poslovniPartner) {
        this.poslovniPartner = poslovniPartner;
    }
}
