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

        get("/kysymykset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Kysymys kysymys = kysymysDao.findOne(Integer.parseInt(req.params("id")));
            map.put("kysymys", kysymys);
            List vastaukset = vastausDao.findByKysymys(kysymys);
            map.put("lkm", vastaukset.size());
            map.put("vastaukset", vastaukset);

            return new ModelAndView(map, "kysymys");
        }, new ThymeleafTemplateEngine());
    }
}
