package quiz;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import quiz.listener.QuizContextListener;

import java.util.EnumSet;
import javax.servlet.DispatcherType;

public class Main {

    private static final String QUESTION_PATH = "/question";
    private static final String ANSWERS_PATH = "/answer";
    private static final String URL = "https://goo.gl/gGbvnm";
    private static final int PORT = 8080;

    private Main() {
    }

    public static void main(String[] args) throws Exception {
        final WebAppContext servletHandler = new WebAppContext();
        servletHandler.setContextPath("/");
        servletHandler.setDescriptor("webapp/WEB-INF/web.xml");

        servletHandler.addEventListener(new QuizContextListener(URL));
        servletHandler.addFilter(Identification.class, QUESTION_PATH, EnumSet.of(DispatcherType.REQUEST));

        servletHandler.addServlet(QuestionServlet.class, QUESTION_PATH);
        servletHandler.addFilter(Identification.class, ANSWERS_PATH, EnumSet.of(DispatcherType.FORWARD));
        servletHandler.addServlet(AnswersServlet.class, ANSWERS_PATH);

        final java.net.URL webAppDir = Thread.currentThread().getContextClassLoader().getResource("webapp");
        if (webAppDir == null) {
            throw new RuntimeException("Webapp directory was not found");
        }
        servletHandler.setResourceBase(webAppDir.toURI().toString());
        servletHandler.setParentLoaderPriority(true);

        servletHandler.setAttribute(
                "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$");

        final Server server = new Server(getPort());

        server.setHandler(servletHandler);

        final Configuration.ClassList classlist = Configuration.ClassList
                .setServerDefault(server);
        classlist.addBefore(
                "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration");

        server.start();
        server.join();
    }

    public static int getPort() {
        final String port = System.getenv("PORT");
        if (port == null) {
            return PORT;
        } else {
            return Integer.parseInt(port);
        }
    }
}
