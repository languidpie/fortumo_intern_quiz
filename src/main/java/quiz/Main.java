package quiz;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import quiz.listener.QuizContextListener;

import java.util.EnumSet;
import java.util.Scanner;
import javax.servlet.DispatcherType;

public class Main {

    private static final int PORT = Integer.parseInt(System.getenv("PORT"));
    private static final String PATH = "/question";

    private Main() {
    }

    public static void main(String[] args) throws Exception {
        /* Asking for user name */
        System.out.println("What is your name?");
        final Scanner scanner = new Scanner(System.in);
        final String userName = scanner.nextLine();

        System.out.println("Hello " + userName);

        /* Launching server */
        final String questionDB = System.getenv("DATABASE");
        final ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.addEventListener(new QuizContextListener(questionDB));
        final Server server = new Server(PORT);
        servletHandler.addFilter(Identification.class, PATH, EnumSet.of(DispatcherType.REQUEST));
        servletHandler.addServlet(QuizQuestions.class, PATH);
        server.setHandler(servletHandler);
        server.start();
        server.join();
    }
}
