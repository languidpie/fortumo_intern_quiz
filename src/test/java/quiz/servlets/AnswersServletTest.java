package quiz.servlets;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import quiz.Identification;
import quiz.JettyRule;
import quiz.Question;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;

public class AnswersServletTest {

    @Rule
    public JettyRule jettyRule = new JettyRule(new Identification(), new AnswersServlet(), "/answer");
    // I think it should be 'result.jsp', but I'm not sure. Can check later.

    Map<Integer, Question> questionMap = new HashMap<>();
    Question question = new Question();

    @Before
    public void setUp() throws Exception {

        this.question.setId(1);
        this.question.setQuestion("Test question 1");
        this.question.setDifficulty(3);
        this.question.setCategory("Test category");
        this.question.setAnswers(Arrays.asList("Test answer 1", "Test answer 2"));

        this.questionMap.put(1, this.question);
        AnswersServlet.setQuestionMap(this.questionMap);
    }

    @Test
    public void should_return_assertion_response_when_status_code_200() throws Exception {
        //given
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), "{\n"
                + "  \"id\": \"1\",\n"
                + "  \"answer\": \"Test answer 1\"\n"
                + "}");

        //when
        final Request request = new Request.Builder().url(this.jettyRule.getUrl("/answer"))
                                               .addHeader("x-player-name", "mari-liis")
                                               .post(requestBody)
                                               .build();
        final Response response = this.jettyRule.makeRequest(request);

        //then
        assertEquals(HttpServletResponse.SC_OK, response.code());
        assertEquals("correct", response.body().string());
    }

    @Test
    public void should_return_400_bad_request_when_reply_is_not_presented_correctly() throws Exception {
        //given
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), "Test answer 1");

        //when
        final Request request = new Request.Builder().url(this.jettyRule.getUrl("/answer"))
                                               .addHeader("x-player-name", "mari-liis")
                                               .post(requestBody)
                                               .build();
        final Response response = this.jettyRule.makeRequest(request);

        //then
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.code());
        assertEquals("Invalid input!", response.body().string());
    }
}