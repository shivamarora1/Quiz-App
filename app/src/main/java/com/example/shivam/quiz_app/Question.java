package com.example.shivam.quiz_app;

/**
 * Created by Shivam on 12/28/2017.
 */

public class Question {

    private String question_title;
    private String correct_ans;
    private String[] incorrect_ans;

    public Question(String question_title, String correct_ans, String[] incorrect_ans) {
        this.question_title = question_title;
        this.correct_ans = correct_ans;
        this.incorrect_ans = incorrect_ans;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getCorrect_ans() {
        return correct_ans;
    }

    public void setCorrect_ans(String correct_ans) {
        this.correct_ans = correct_ans;
    }

    public String[] getIncorrect_ans() {
        return incorrect_ans;
    }

    public void setIncorrect_ans(String[] incorrect_ans) {
        this.incorrect_ans = incorrect_ans;
    }
}
