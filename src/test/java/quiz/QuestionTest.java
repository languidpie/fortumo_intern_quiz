package quiz;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import quiz.db.DBConnectionManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class QuestionTest {

    private String questions;
    private DBConnectionManager dbConnectionManager = new DBConnectionManager("");

    @Before
    public void setUp() throws IOException, FileNotFoundException {

        FileInputStream inputStream = new FileInputStream("gistfile1.txt");

        this.questions = IOUtils.toString(inputStream);
        inputStream.close();
    }

    @Test
    public void should_have_multiple_answers_in_list() {
        //given
        Map<Integer, Question> questionMap = dbConnectionManager.findAllQuestions(questions);

        //then
        assertTrue(questionMap.get(1).getAnswers().size() == 3);
    }
}
