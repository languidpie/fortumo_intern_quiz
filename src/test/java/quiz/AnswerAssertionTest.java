package quiz;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AnswerAssertionTest {

    Map<Integer, Question> questionMap = new HashMap<>();
    AnswerView answerView;
    AnswerAssertion answerAssertion = new AnswerAssertion();
    Question question = new Question();

    @Before
    public void setUp() throws Exception {
        String answer = "{\n"
                + "  \"id\": \"2\",\n"
                + "  \"answer\": \"Tartu\"\n"
                + "}";
        this.answerView = new Gson().fromJson(answer, AnswerView.class);
        this.question.setId(2);
        this.question.setQuestion("Which city is Estonia's 2nd largest city?");
        this.question.setCategory("geography");
        this.question.setDifficulty(3);
    }

    @Test
    public void should_return_correct_if_answer_is_right() throws Exception {
        //given
        final Question question = new Question();
        question.setId(2);
        question.setQuestion("Which city is Estonia's 2nd largest city?");
        question.setCategory("geography");
        question.setDifficulty(3);
        question.setAnswers(Arrays.asList("Tartu"));
        this.questionMap.put(2, question);

        //when
        final String answer = this.answerAssertion.assertAnswer(this.questionMap, this.answerView);

        //then
        assertEquals("correct", answer);
    }

    @Test
    public void should_return_wrong_if_answer_is_incorrect() throws Exception {
        //given
        this.question.setAnswers(Arrays.asList("Tallinn"));
        this.questionMap.put(2, this.question);

        //when
        final String answer = this.answerAssertion.assertAnswer(this.questionMap, this.answerView);

        //then
        assertEquals("wrong", answer);
    }
}