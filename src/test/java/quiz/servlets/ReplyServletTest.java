package quiz.servlets;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Rule;
import org.junit.Test;
import quiz.JettyRule;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;

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
                    resp.getWriter().write("success");
                }
            }), "/result.jsp");

            return handler;
        }
    };

    @Test
    public void should_return_success_when_post_is_successful() throws IOException {
        //given
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), "{\n"
                + "  \"id\": \"1\",\n"
                + "  \"answer\": \"Test answer 1\"\n"
                + "}");

        final Request request =
                new Request.Builder().url(this.jettyRule.getUrl("/result.jsp")).post(requestBody).build();

        //when
        final Response response = this.jettyRule.makeRequest(request);

        //then
        assertEquals(HttpServletResponse.SC_OK, response.code());
        assertEquals("success", response.body().string());
    }

    @Test
    public void should_return_400_when_body_not_present() throws IOException {
        //given
        final Request request =
                new Request.Builder().url(this.jettyRule.getUrl("/result.jsp")).build();

        //when
        final Response response = this.jettyRule.makeRequest(request);

        //then
        assertEquals(HttpServletResponse.SC_METHOD_NOT_ALLOWED, response.code());
    }
}