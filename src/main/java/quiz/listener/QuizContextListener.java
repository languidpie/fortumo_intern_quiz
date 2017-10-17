package quiz.listener;

import quiz.db.DBConnectionManager;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class QuizContextListener implements ServletContextListener {

    String url;

    public QuizContextListener(String quesitonDB) {
        this.url = quesitonDB;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext cntxt = sce.getServletContext();

        DBConnectionManager dbConnectionManager = new DBConnectionManager(url);
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
