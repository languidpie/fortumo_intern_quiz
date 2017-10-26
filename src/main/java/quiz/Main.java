package quiz;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import quiz.listener.QuizContextListener;

import java.util.EnumSet;
import javax.servlet.DispatcherType;

public class Main {

    private static final int PORT = Integer.parseInt("8080");
    private static final String QUESTION_PATH = "/question";
    private static final String ANSWERS_PATH = "/answer";
    private static final String URL = "https://goo.gl/gGbvnm";

    private Main() {
    }

    public static void main(String[] args) throws Exception {

        final ServletContextHandler servletHandler = new ServletContextHandler();

        servletHandler.addEventListener(new QuizContextListener(URL));
        servletHandler.addFilter(Identification.class, QUESTION_PATH, EnumSet.of(DispatcherType.REQUEST));

        servletHandler.addServlet(QuestionServlet.class, QUESTION_PATH);
        servletHandler.addFilter(Identification.class, ANSWERS_PATH, EnumSet.of(DispatcherType.FORWARD));
        servletHandler.addServlet(AnswersServlet.class, ANSWERS_PATH);

        final Server server = new Server(PORT);

        server.setHandler(servletHandler);
        server.start();
        server.join();
    }
}
