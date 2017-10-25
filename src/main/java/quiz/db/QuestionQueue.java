package quiz.db;

import quiz.Question;
import quiz.QuestionsDAO;

import java.util.Iterator;
import java.util.Map;

public class QuestionQueue {

    private final Map<Integer, Question> questionMap;
    private Iterator<Question> questionIterator;

    public QuestionQueue(QuestionsDAO questionsDAO) {
        this.questionMap = questionsDAO.findAllQuestions();
    }

    public Question nextQuestion() {
        final Question question;

        if (this.questionIterator == null || !this.questionIterator.hasNext()) {
            this.questionIterator = this.questionMap.values().iterator();
        }

        question = this.questionIterator.next();

        return question;
    }
}
