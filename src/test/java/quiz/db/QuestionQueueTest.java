package quiz.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import quiz.Question;
import quiz.QuestionsDAO;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuestionQueueTest {

    private QuestionQueue questionQueue;
    Type collectionType = new TypeToken<Collection<Question>>() {
    }.getType();

    Gson gson = new Gson();
    JsonReader jsonReader;
    Collection<Question> questionCollection;
    private QuestionsDAO questionDAO;

    @Before
    public void setUp() throws Exception {
        this.questionDAO = Mockito.mock(QuestionsDAO.class);
        this.questionQueue = new QuestionQueue(questionDAO);

        this.jsonReader = new JsonReader(
                new FileReader("/Users/mariliis/repos/fortumo_intern_quiz/src/test/resources/questionsSample")); /* Different pathing doesn't work atm */
    /* Deserializing JSON file*/
        this.questionCollection = this.gson.fromJson(
                this.jsonReader,
                this.collectionType);
    }

    @Test
    public void should_return_next_question() throws Exception {
        //given
        final Iterator<Question> questionIterator = this.questionCollection.iterator();


        //when
        when(this.questionQueue.nextQuestion().getQuestion()).thenReturn(questionIterator.next().getQuestion())
                                                             .thenReturn(questionIterator.next().getQuestion());

        //then
        verify(this.questionQueue, atLeastOnce()).nextQuestion().getQuestion().equals("Kes on tubli poiss?");
    }
}