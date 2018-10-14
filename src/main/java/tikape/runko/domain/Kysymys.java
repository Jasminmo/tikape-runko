package tikape.runko.domain;

public class Kysymys {

    private Integer id;
    private String kurssi;
    private String aihe;
    private String kysymysTeksti;

    public Kysymys(Integer id, String kurssi, String aihe, String kysymysTeksti) {
        this.id = id;
        this.kurssi = kurssi;
        this.aihe = aihe;
        this.kysymysTeksti = kysymysTeksti;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKurssi() {
        return kurssi;
    }

    public void setKurssi(String kurssi) {
        this.kurssi = kurssi;
    }

    public String getAihe() {
        return aihe;
    }

    public void setAihe(String aihe) {
        this.aihe = aihe;
    }

    public String getKysymysTeksti() {
        return kysymysTeksti;
    }

    public void setKysymysTeksti(String kysymysTeksti) {
        this.kysymysTeksti = kysymysTeksti;
    }

}
