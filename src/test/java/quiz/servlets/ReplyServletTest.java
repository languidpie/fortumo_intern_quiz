package quiz.servlets;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Rule;
import quiz.JettyRule;
import quiz.TestForwardedServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReplyServletTest extends HttpServlet {

    @Rule
    public JettyRule jettyRule = new JettyRule(new ReplyServlet(), "/reply") {

        @Override
        protected ServletContextHandler buildHandler() throws Exception {
            final ServletContextHandler handler = super.buildHandler();

            handler.addServlet(new ServletHolder(new TestForwardedServlet() {

                @Override
                protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                        throws ServletException, IOException
                {
                    resp.getWriter().write("tere");
                }
            }), "/result.jsp");

            return handler;
        }
    };
}