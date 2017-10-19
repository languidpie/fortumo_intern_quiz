package quiz.db;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import quiz.Question;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DBConnectionManagerTest {

    private String questions;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final DBConnectionManager dbConnectionManager = new DBConnectionManager("");

    @Before
    public void setUp() throws IOException {
        FileInputStream inputStream = new FileInputStream("gistfile1.txt");

        this.questions = IOUtils.toString(inputStream);

        inputStream.close();
    }
    /* Testing findAllQuestions() */

    @Test
    public void should_return_correct_response_if_question_id_already_exists() throws IOException {
        //given
        FileInputStream inputStream = new FileInputStream("gistfile2.txt");

        String questionsFile = IOUtils.toString(inputStream);

        inputStream.close();

        //when
        dbConnectionManager.findAllQuestions(questionsFile);

        //then
        assertEquals("Question with this id already exists!\n", outContent.toString());
    }

    @Test
    public void should_create_a_question_with_multiple_answers() {
        //given
        String questionString = "1;Kes on tubli poiss?;general;1;Lars;Lars Eckart;Oskar\n";
        List<String> list = new ArrayList<>();
        list.add("Lars");
        list.add("Lars Eckart");
        list.add("Oskar");

        //when
        Map<Integer, Question> questionMap = dbConnectionManager.findAllQuestions(questionString);

        //then
        assertTrue(questionMap.get(1).getAnswers().containsAll(list));
        assertTrue(questionMap.get(1).getAnswers().size() == 3);
    }

    @Test
    public void should_create_a_question_with_only_one_answer() {
        //given
        String question = "4;Which plant does the Canadian flag contain?;nature;1;Maple";
        List<String> stringList = new ArrayList<>();
        stringList.add("Maple");

        //when
        Map<Integer, Question> questionMap = dbConnectionManager.findAllQuestions(question);

        //then
        assertTrue(questionMap.get(4).getAnswers().containsAll(stringList));
        assertTrue(questionMap.get(4).getAnswers().size() == 1);
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }
}