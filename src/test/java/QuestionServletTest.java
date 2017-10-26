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

    final Gson gson = new Gson();
    final Question firstQuestion = new Question();

    @Rule
    public JettyRule jettyRuleWithId = new JettyRule(new Identification(), new QuestionServlet(), "/question");

    @Before
    public void setUp() {
        final QuestionQueue queue = Mockito.mock(QuestionQueue.class);

        this.firstQuestion.setQuestion("Test question 1");
        this.firstQuestion.setDifficulty(1);
        this.firstQuestion.setCategory("test_category");
        this.firstQuestion.setAnswers(Arrays.asList("first answer", "second answer"));

        when(queue.nextQuestion()).thenReturn(this.firstQuestion);

        QuizContextListener.setQuestionQueue(queue);
    }

    /* Testing with Identification class */
    @Test
    public void should_return_status_200() throws Exception {
        //given
        final Request request = new Request.Builder().url(this.jettyRuleWithId.getUrl("/question"))
                                                     .addHeader("x-player-name", "mari-liis")
                                                     .build();

        //when
        final Response response = this.jettyRuleWithId.makeRequest(request);

        //then
        assertEquals(HttpServletResponse.SC_OK, response.code());
    }

    @Test
    public void should_return_status_400_when_header_is_missing() throws Exception {
        //given
        final Request request = new Request.Builder().url(this.jettyRuleWithId.getUrl("/question")).build();

        //when
        final Response response = this.jettyRuleWithId.makeRequest(request);

        //then
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.code());
    }

    @Rule
    public JettyRule jettyRule = new JettyRule(new QuestionServlet(), "/question");

    @Test
    public void should_return_correct_response_body_when_status_is_200() throws Exception {
        //given
        final Request request = new Request.Builder().url(this.jettyRule.getUrl("/question")).build();

        //when
        final Response response = this.jettyRule.makeRequest(request);
        final Question respQuestion = this.gson.fromJson(response.body().string(), Question.class);

        //then
        assertEquals(HttpServletResponse.SC_OK, response.code());
        assertEquals(this.firstQuestion.getQuestion(), respQuestion.getQuestion());
    }
}