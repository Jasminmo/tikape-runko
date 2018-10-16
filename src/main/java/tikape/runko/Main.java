package tikape.runko;

import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KysymysDao;
import tikape.runko.database.VastausDao;
import tikape.runko.domain.Kysymys;
import tikape.runko.domain.Vastaus;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:db/database.db");
        database.init();

        KysymysDao kysymysDao = new KysymysDao(database);
        VastausDao vastausDao = new VastausDao(database, kysymysDao);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/kysymykset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kysymykset", kysymysDao.findAll());

            return new ModelAndView(map, "kysymykset");
        }, new ThymeleafTemplateEngine());

        post("/kysymykset", (req, res) -> {
            Kysymys kysymys = new Kysymys(null, req.queryParams("kurssi"), req.queryParams("aihe"), req.queryParams("kysymysTeksti"));
            kysymys = kysymysDao.saveOrUpdate(kysymys);

            res.redirect("/kysymykset/" + kysymys.getId());
            return "";
        });

        get("/kysymykset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Kysymys kysymys = kysymysDao.findOne(Integer.parseInt(req.params("id")));
            map.put("kysymys", kysymys);
            List<Vastaus> vastaukset = vastausDao.findByKysymys(kysymys);
            for (Vastaus v: vastaukset) {
                System.out.println(v.getOikein());
            }
            
            map.put("lkm", vastaukset.size());
            map.put("vastaukset", vastaukset);

            return new ModelAndView(map, "kysymys");
        }, new ThymeleafTemplateEngine());

        post("/kysymykset/:id", (req, res) -> {
            Kysymys kysymys = kysymysDao.findOne(Integer.parseInt(req.params("id")));
            System.out.println(req.queryString());
            boolean oikein = false;
            if (!req.queryParams("oikein").equals("null")) {
                oikein = true;
            }
            Vastaus vastaus = new Vastaus(null, kysymys, req.queryParams("vastausTeksti"), oikein);
            vastausDao.saveOrUpdate(vastaus);
            res.redirect("/kysymykset/" + kysymys.getId());
            return "";
        });

        post("/kysymykset/delete/:id", (req, res) -> {
            Kysymys k = kysymysDao.findOne(Integer.parseInt(req.params("id")));
            vastausDao.deleteByKysymys(k);
            kysymysDao.delete(k.getId());
            res.redirect("/kysymykset");
            return "";
        });

        post("/vastaukset/delete/:id", (req, res) -> {
            vastausDao.delete(Integer.parseInt(req.params("id")));
            res.redirect("/kysymykset");
            return "";
        });

    }
}
