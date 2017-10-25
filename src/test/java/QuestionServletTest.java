

import org.eclipse.jetty.testing.HttpTester;
import org.eclipse.jetty.testing.ServletTester;
import org.junit.Before;
import org.junit.Test;
import quiz.Identification;
import quiz.QuizQuestions;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionServletTest {

    private ServletTester servletTester;
    private HttpTester request;
    private HttpTester response;

    @Before
    public void initialize() throws Exception {
        this.servletTester = new ServletTester();
        this.servletTester.addServlet(QuizQuestions.class, "/question");
        this.servletTester.addFilter(Identification.class, "/question", 0);
        this.servletTester.start();

        this.request = new HttpTester();
        this.response = new HttpTester();
    }
//TODO: Find out why ByteArrayBuffer is missing
//    @Test
//    public void should_return_status_200() throws Exception {
//        // given
//        this.request.setMethod("GET");
//        this.request.setURI("/question");
//        this.request.setVersion("HTTP/1.0");
//        this.request.setHeader("x-player-name", "any_player_name");
//
//        // when
//        this.response.parse(this.servletTester.getResponses(this.request.generate()));
//
//        // then
//        assertThat(this.response.getStatus()).isEqualTo(200);
//    }

//    @Test
//    public void should_return_status_400_when_header_is_missing() throws Exception {
//        // given
//        this.request.setMethod("GET");
//        this.request.setURI("/question");
//        this.request.setVersion("HTTP/1.0");
//
//        // when
//        this.response.parse(this.servletTester.getResponses(this.request.generate()));
//
//        // then
//        assertThat(this.response.getStatus()).isEqualTo(400);
//    }
}
