package quiz.listener;

import quiz.db.DBConnectionManager;
import quiz.db.QuestionQueue;
import quiz.servlets.AnswersServlet;

import java.io.IOException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class QuizContextListener implements ServletContextListener {

    private static QuestionQueue questionQueue;

    private final String url;

    public QuizContextListener(String quesitonDB) {
        this.url = quesitonDB;
    }

    @Override

    public void contextInitialized(ServletContextEvent sce) {
        final DBConnectionManager dbConnectionManager = new DBConnectionManager(this.url);
        try {
            dbConnectionManager.load();
            questionQueue = new QuestionQueue(dbConnectionManager);
            AnswersServlet.setQuestionMap(dbConnectionManager.findAllQuestions());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    public static QuestionQueue getQuestionQueue() {
        return questionQueue;
    }

    public static void setQuestionQueue(QuestionQueue questionQueue) {
        QuizContextListener.questionQueue = questionQueue;
    }
}
