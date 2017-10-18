package quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class QuestionQuiz {

    private int count;
    private Map<Integer, Question> gameQuestions;

    public void startGame(Map<Integer, Question> quizQuestions) {
        this.gameQuestions = quizQuestions;

        if (this.count < this.gameQuestions.size()) {
            if (this.showQuestion()) {
                System.out.println("Congratulations! Do you want another question? y/n");
                this.returnPlayerResponse(this.fetchUserAnswer());
            } else {
                System.out.println("Oops, that was wrong. Do you want to try again? y/n");
                this.returnPlayerResponse(this.fetchUserAnswer());
            }
        }
    }

    private void returnPlayerResponse(String reply) {
        switch (reply) {
            case "y":
                if (this.count == this.gameQuestions.size()) {
                    this.count = 0;
                    this.startGame(this.gameQuestions);
                } else {
                    this.startGame(this.gameQuestions);
                }
                break;
            default:
                System.out.println("The game is shutting down. Thanks for playing!");
                break;
        }
    }

    public Boolean showQuestion() {
        final List<Integer> questId = this.getQuestionId();

        final Question question = this.gameQuestions.get(questId.get(this.count));
        System.out.println("("
                + String.valueOf(question.getDifficulty())
                + ","
                + question.getCategory()
                + ") "
                + question.getQuestion());

        final String answer = this.fetchUserAnswer();

        this.count++;

        return question.getAnswers().contains(answer);
    }

    private String fetchUserAnswer() {
        final Scanner scanner = new Scanner(System.in);

        return scanner.next();
    }

    private List<Integer> getQuestionId() {

        return new ArrayList<>(this.gameQuestions.keySet());
    }
}
