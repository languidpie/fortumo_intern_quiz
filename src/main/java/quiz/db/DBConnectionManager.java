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
    //    private int count;

    public DBConnectionManager(String dbURL) {
        this.dbURL = dbURL;
    }

    public void load() throws IOException {
        final Request request = new Request.Builder().url(this.dbURL).build();
        final Call call = this.okHttpClient.newCall(request);
        final Response response = call.execute();

        final String questions = response.body().string();

        this.allQuestions = this.findAllQuestions(questions);

        //        this.questionGame();
    }

    //    private void questionGame() {
    //
    //        if (this.count < this.allQuestions.size()) {
    //            if (this.showQuestion()) {
    //                System.out.println("Congratulations! Do you want another question? y/n");
    //                this.returnPlayerResponse(this.fetchUserAnswer());
    //            } else {
    //                System.out.println("Oops, that was wrong. Do you want to try again? y/n");
    //                this.returnPlayerResponse(this.fetchUserAnswer());
    //            }
    //        }
    //    }
    //
    //    private void returnPlayerResponse(String reply) {
    //        switch (reply) {
    //            case "y":
    //                if (this.count == this.allQuestions.size()) {
    //                    this.count = 0;
    //                    this.questionGame();
    //                } else {
    //                    this.questionGame();
    //                }
    //                break;
    //            default:
    //                System.out.println("The game is shutting down. Thanks for playing!");
    //                this.call.cancel();
    //                break;
    //        }
    //    }

    public Map<Integer, Question> findAllQuestions(String questions) {

        final Map<Integer, Question> result = new HashMap<>();

        final String[] list = questions.split("\n");

        for (String string : list) {
            final String[] questionSep = string.split(";");
            if (result.containsKey(Integer.parseInt(questionSep[0]))) {
                System.out.println("Question with this id already exists!");
            } else {
                final Question question = new Question();
                question.setQuestion(questionSep[QUESTION_INDEX]);
                question.setCategory(questionSep[CATEGORY_INDEX]);
                question.setDifficulty(Integer.parseInt(questionSep[DIFFICULTY_INDEX]));

                /* Checks if there are multiple answers */
                if (questionSep.length < MULTIPLE_ANSWER_INDEX) {
                    question.addAnswer(questionSep[ANSWER_INDEX]);
                } else {
                    final List<String> answers = new ArrayList<>();
                    final String[] givenAnswers = Arrays.copyOfRange(questionSep, 4, questionSep.length);
                    Collections.addAll(answers, givenAnswers);
                    question.setAnswers(answers);
                }
                final int questionId = Integer.parseInt(questionSep[0]);
                result.put(questionId, question);
            }
        }
        return result;
    }

//    public Boolean showQuestion() {
//        final List<Integer> questId = this.getQuestionId();
//
//        final Question question = this.allQuestions.get(questId.get(this.count));
//        System.out.println("("
//                + String.valueOf(question.getDifficulty())
//                + ","
//                + question.getCategory()
//                + ") "
//                + question.getQuestion());
//
//        final String answer = this.fetchUserAnswer();
//
//        this.count++;
//
//        return question.getAnswers().contains(answer);
//    }
//
//    private String fetchUserAnswer() {
//        final Scanner scanner = new Scanner(System.in);
//
//        return scanner.next();
//    }
//
//    private List<Integer> getQuestionId() {
//
//        return new ArrayList<>(this.allQuestions.keySet());
//    }
}
