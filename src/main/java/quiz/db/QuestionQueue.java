package quiz.db;

import quiz.Question;
import quiz.QuestionsDAO;

import java.util.Map;

public class QuestionQueue {

    private final Map<Integer, Question> questionMap;
    private int count;

    public QuestionQueue(QuestionsDAO questionsDAO) {
        this.questionMap = questionsDAO.findAllQuestions();
    }

    public Question nextQuestion() {
        final Question question;

        if (this.count == this.questionMap.size()) {
            question = this.questionMap.get(this.count);
            this.count = 1;
        } else {
            question = this.questionMap.get(this.count);
            this.count++;
        }

        return question;
    }
}
