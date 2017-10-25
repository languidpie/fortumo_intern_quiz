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
        //TODO

    }

    @Test
    public void should_have_correct_values_on_given_question_id() {
            //TODO
    }
}
