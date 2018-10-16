package tikape.runko.domain;

public class Vastaus implements AbstractNamedObject {

    private Integer id;
    private Kysymys kysymys;
    private String vastausTeksti;
    private Boolean oikein;

    public Vastaus(Integer id, Kysymys kysymys, String vastausTeksti, Boolean oikein) {
        this.id = id;
        this.kysymys = kysymys;
        this.vastausTeksti = vastausTeksti;
        this.oikein = oikein;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Kysymys getKysymys() {
        return kysymys;
    }

    public void setKysymys(Kysymys kysymys) {
        this.kysymys = kysymys;
    }

    public String getVastausTeksti() {
        return vastausTeksti;
    }

    public void setVastausTeksti(String vastausTeksti) {
        this.vastausTeksti = vastausTeksti;
    }

    public Boolean getOikein() {
        return oikein;
    }

    public void setOikein(Boolean oikein) {
        this.oikein = oikein;
    }

}
