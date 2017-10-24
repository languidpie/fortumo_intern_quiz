package quiz.db;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import quiz.Question;
import quiz.QuestionsDAO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class QuestionQueueTest {

    private QuestionsDAO questionDAO;

    @Before
    public void setUp() throws Exception {
        this.questionDAO = Mockito.mock(QuestionsDAO.class);

        final Map<Integer, Question> questions = new HashMap<>();
        final Question firstQuestion = new Question();
        firstQuestion.setQuestion("Test question 1");
        firstQuestion.setDifficulty(1);
        firstQuestion.setCategory("test_category");
        firstQuestion.setAnswers(Arrays.asList("first answer", "second answer"));
        questions.put(1, firstQuestion);

        final Question secondQuestion = new Question();
        secondQuestion.setQuestion("Test question 2");
        secondQuestion.setDifficulty(2);
        secondQuestion.setCategory("test_category");
        secondQuestion.setAnswers(Arrays.asList("first answer", "second answer"));
        questions.put(2, secondQuestion);

        Mockito.when(this.questionDAO.findAllQuestions()).thenReturn(questions);
    }

    @Test
    public void should_return_next_question() throws Exception {
        //given
        final QuestionQueue queue = new QuestionQueue(this.questionDAO);
        final Question testQuestion = queue.nextQuestion(); //Do not delete. Is necessary for the test to work.

        //when
        String question1 = queue.nextQuestion().getQuestion();
        String question2 = queue.nextQuestion().getQuestion();

        //then
        assertEquals(question1, "Test question 1");
        assertEquals(question2, "Test question 2");
    }
}