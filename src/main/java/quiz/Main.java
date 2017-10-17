package quiz;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import quiz.listener.QuizContextListener;

import java.util.EnumSet;
import javax.servlet.DispatcherType;

public class Main {

    public static void main(String[] args) {
        int PORT = Integer.parseInt(System.getenv("PORT"));
        String quesitonDB = System.getenv("DATABASE");
        Server server = new Server(PORT);
        try {
            ServletContextHandler servletHandler = new ServletContextHandler();
            //            ListenerHolder listenernerHolder = new ListenerHolder(Source.EMBEDDED);
            //            listenerHolder.setListener(new QuizContextListener(quesitonDB));
            servletHandler.addEventListener(new QuizContextListener(quesitonDB));

            servletHandler.addFilter(Identification.class, "/question", EnumSet.of(DispatcherType.REQUEST));

            servletHandler.addServlet(QuizQuestions.class, "/question");
            server.setHandler(servletHandler);

            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong during server start");
        }
    }
}
