package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KysymysDao;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:db/database.db");
        database.init();

        KysymysDao kysymysDao = new KysymysDao(database);

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
            map.put("kysymys", kysymysDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "kysymys");
        }, new ThymeleafTemplateEngine());
    }
}
