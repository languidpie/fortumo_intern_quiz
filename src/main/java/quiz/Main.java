package quiz;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class Main {

    public static void main(String[] args) {
        int PORT = Integer.parseInt(System.getenv("PORT"));
        Server server = new Server(PORT);
        try {
            ServletHandler servletHandler = new ServletHandler();
            server.setHandler(servletHandler);

            servletHandler.addServletWithMapping(HelloServlet.class, "/*");

            server.start();
            server.join();
        } catch (Exception e) {
            System.out.println("Something went wrong during server start");
        }
    }
}
