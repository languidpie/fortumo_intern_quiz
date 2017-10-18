package quiz.listener;

import quiz.db.DBConnectionManager;

import java.io.IOException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class QuizContextListener implements ServletContextListener {

    private final String url;

    public QuizContextListener(String quesitonDB) {
        this.url = quesitonDB;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final DBConnectionManager dbConnectionManager = new DBConnectionManager(this.url);
        try {
            dbConnectionManager.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
