package quiz;

import java.util.Map;

public interface QuestionsDAO {

    //TODO: Find questions method
    Map<Integer, Question> findAllQuestions();
}
