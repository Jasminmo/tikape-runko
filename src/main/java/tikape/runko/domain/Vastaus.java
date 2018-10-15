package tikape.runko.domain;

public class Vastaus {

    private Integer id;
    private Kysymys kysymys;
    private String vastausTeksti;

    public Vastaus(Integer id, Kysymys kysymys, String vastausTeksti) {
        this.id = id;
        this.kysymys = kysymys;
        this.vastausTeksti = vastausTeksti;
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

}
