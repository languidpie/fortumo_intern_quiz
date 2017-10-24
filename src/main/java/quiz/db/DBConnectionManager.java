package quiz.db;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import quiz.Question;
import quiz.QuestionsDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConnectionManager implements QuestionsDAO {

    private static final int QUESTION_INDEX = 1;
    private static final int CATEGORY_INDEX = 2;
    private static final int DIFFICULTY_INDEX = 3;
    private static final int ANSWER_INDEX = 4;
    private static final int MULTIPLE_ANSWER_INDEX = 5;
    private final String dbURL;
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private Map<Integer, Question> allQuestions;

    public DBConnectionManager(String dbURL) {
        this.dbURL = dbURL;
    }

    public void load() throws IOException {
        final Request request = new Request.Builder().url(this.dbURL).build();
        final Call call = this.okHttpClient.newCall(request);
        final Response response = call.execute();

        final String questions = response.body().string();

        this.allQuestions = this.parseQuestions(questions);

    }

    public Map<Integer, Question> parseQuestions(String questions) {

        final Map<Integer, Question> result = new HashMap<>();

        final String[] list = questions.split("\n");

        for (String string : list) {
            final String[] questionSep = string.split(";");
            /* Checks if a question with the given id already exists */
            if (result.containsKey(Integer.parseInt(questionSep[0]))) {
                System.out.println("Question with this id already exists!");
            } else {
                /* Question creation - setting question, category, difficulty*/
                final Question question = new Question();
                question.setQuestion(questionSep[QUESTION_INDEX]);
                question.setCategory(questionSep[CATEGORY_INDEX]);
                question.setDifficulty(Integer.parseInt(questionSep[DIFFICULTY_INDEX]));

                /* Checks if there are multiple answers */
                if (questionSep.length < MULTIPLE_ANSWER_INDEX) {
                    /* Adds one answer if there are no multiple answers */
                    question.addAnswer(questionSep[ANSWER_INDEX]);
                } else {
                    /* Adds multiple answers if there are any */
                    final List<String> answers = new ArrayList<>();
                    final String[] givenAnswers = Arrays.copyOfRange(questionSep, 4, questionSep.length);
                    Collections.addAll(answers, givenAnswers);
                    question.setAnswers(answers);
                }
                /* Parses question id and adds it to the result list with the corresponding question */
                final int questionId = Integer.parseInt(questionSep[0]);
                result.put(questionId, question);
            }
        }
        return result;
    }

    @Override
    public Map<Integer, Question> findAllQuestions() {
        return this.allQuestions;
    }
}