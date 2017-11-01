package quiz.servlets;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import quiz.JettyRule;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

public class GameServletTest {

    @Rule
    public JettyRule jettyRule = new JettyRule(new GameServlet(), "/play") {

        @Override
        protected ServletContextHandler buildHandler() throws Exception {
            final ServletContextHandler handler = super.buildHandler();

            handler.addServlet(new ServletHolder(new TestForwardedServlet() {

                @Override
                protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                        throws ServletException, IOException
                {
                    resp.getWriter().write("Is it okay to question questions?");
                }
            }), "/play.jsp");

            return handler;
        }
    };

    @Test
    public void should_return_200_when_question_is_returned() throws IOException {
        final HttpSession httpSession = Mockito.mock(HttpSession.class);
        Mockito.when(httpSession.getAttribute(eq("user_name"))).thenReturn("mari-liis");
        Mockito.when(this.jettyRule.getSessionManager().newHttpSession(any())).thenReturn(httpSession);

        final RequestBody requestBody = RequestBody.create(MediaType.parse("text/html"), "lala");

        final Request request =
                new Request.Builder().url(this.jettyRule.getUrl("/play.jsp")).post(requestBody)
                                     .addHeader("x-player-name", "mari-liis")
                                     .build();

        final Response question = this.jettyRule.makeRequest(request);

        assertEquals("Is it okay to question questions?", question.body().string());
        assertEquals(HttpServletResponse.SC_OK, question.code());
    }
}