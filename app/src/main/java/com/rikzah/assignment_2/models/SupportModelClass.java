package com.rikzah.assignment_2.models;
public class SupportModelClass {
    String Question, Answers;
    boolean expandable;
    public SupportModelClass() {}
    public SupportModelClass(String question, String answers) {
        Question = question;
        Answers = answers;}
    public SupportModelClass(boolean expandable) {
        this.expandable = expandable;
    }
    public boolean isExpandable() {
        return expandable;
    }
    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }
    public String getQuestion() {
        return Question;
    }
    public void setQuestion(String question) {
        Question = question;
    }
    public String getAnswers() {
        return Answers;
    }
    public void setAnswers(String answers) {
        Answers = answers;
    }
}

