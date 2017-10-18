package quiz;

import java.util.Map;

@FunctionalInterface
public interface QuestionsDAO {

    Map<Integer, Question> findAllQuestions();
}
