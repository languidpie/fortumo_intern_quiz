import com.google.gson.Gson;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import quiz.Identification;
import quiz.JettyRule;
import quiz.Question;
import quiz.QuestionServlet;
import quiz.db.QuestionQueue;
import quiz.listener.QuizContextListener;

import java.util.Arrays;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class QuestionServletTest {

    private Gson gson = new Gson();
    final Question firstQuestion = new Question();

    @Rule
    public JettyRule jettyRuleWithId = new JettyRule(new Identification(), new QuestionServlet(), "/question");

    @Before
    public void setUp() {
        final QuestionQueue queue = Mockito.mock(QuestionQueue.class);
        firstQuestion.setQuestion("Test question 1");
        firstQuestion.setDifficulty(1);
        firstQuestion.setCategory("test_category");
        firstQuestion.setAnswers(Arrays.asList("first answer", "second answer"));
        when(queue.nextQuestion()).thenReturn(firstQuestion);
        QuizContextListener.setQuestionQueue(queue);
    }

    /* Testing with Identification class */
    @Test
    public void should_return_status_200() throws Exception {
        // test
        final Request request = new Request.Builder().url(this.jettyRuleWithId.getUrl("/question"))
                                                     .addHeader("x-player-name", "mari-liis")
                                                     .build();
        final Response response = this.jettyRuleWithId.makeRequest(request);

        // assert
        assertEquals(HttpServletResponse.SC_OK, response.code());
    }

    @Test
    public void should_return_status_400_when_header_is_missing() throws Exception {
        //test
        final Request request = new Request.Builder().url(this.jettyRuleWithId.getUrl("/question")).build();
        final Response response = this.jettyRuleWithId.makeRequest(request);

        //assert
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.code());
    }

    @Rule
    public JettyRule jettyRule = new JettyRule(new QuestionServlet(), "/question");

    @Test
    public void should_return_correct_response_body_when_status_is_200() throws Exception {
        //test
        final Request request = new Request.Builder().url(this.jettyRule.getUrl("/question")).build();
        final Response response = this.jettyRule.makeRequest(request);

        Question respQuestion = this.gson.fromJson(response.body().string(), Question.class);

        //assert
        assertEquals(HttpServletResponse.SC_OK, response.code());
        assertEquals();
    }
}