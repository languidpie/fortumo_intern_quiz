package quiz;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import quiz.db.DBConnectionManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuestionTest {

    private String questions;
    private DBConnectionManager dbConnectionManager = new DBConnectionManager("");

    @Before
    public void setUp() throws IOException {

        FileInputStream inputStream = new FileInputStream("gistfile1.txt");

        this.questions = IOUtils.toString(inputStream);
        inputStream.close();
    }

    @Test
    public void should_have_multiple_answers_in_list() {
        //given
        Map<Integer, Question> questionMap = dbConnectionManager.findAllQuestions(questions);
        Question question = questionMap.get(1);

        //when
        List<String> allAnswers = new ArrayList<>();
        allAnswers.add("Lars");
        allAnswers.add("Lars Eckart");
        allAnswers.add("Oskar");

        //then
        assertTrue(question.getAnswers().size() == 3);
        assertTrue(question.getAnswers().containsAll(allAnswers));
    }

    @Test
    public void should_have_correct_values_on_given_question_id() {
        //given
        Map<Integer, Question> questionMap = dbConnectionManager.findAllQuestions(questions);
        Question question = questionMap.get(2);

        //when
        String quest = question.getQuestion();
        String category = question.getCategory();
        int difficulty = question.getDifficulty();
        List<String> answers = question.getAnswers();

        //then
        assertEquals(quest, "Which city is Estonia's 2nd largest city?");
        assertEquals(category, "geography");
        assertEquals(difficulty, 3);
        assertEquals(answers, answers);
    }
}
