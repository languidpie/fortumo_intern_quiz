package quiz;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class QuestionTest {

    Question question = new Question();

    @Before
    public void setUp() throws IOException {
        this.question.setId(2);
        this.question.setQuestion("Kes on tubli poiss?");
        this.question.setDifficulty(1);
        this.question.setCategory("Fortumo");
    }

    @Test
    public void should_have_correct_answer() {
        //given
        this.question.addAnswer("Lars");

        //when
        List<String> result = this.question.getAnswers();

        //then
        assertTrue(result.contains("Lars"));
    }
}
