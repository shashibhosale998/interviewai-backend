package com.interai.dto;


public class AnswerRequest {
    private Long sessionId;
    private String question;
    private String userAnswer;
    private Integer questionNumber;

    // Getters
    public Long getSessionId() { return sessionId; }
    public String getQuestion() { return question; }
    public String getUserAnswer() { return userAnswer; }
    public Integer getQuestionNumber() { return questionNumber; }

    // Setters
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }
    public void setQuestion(String question) { this.question = question; }
    public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }
    public void setQuestionNumber(Integer questionNumber) { this.questionNumber = questionNumber; }
}