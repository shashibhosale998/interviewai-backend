package com.interai.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "interview_qa")
public class InterviewQA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sessionId;

    @Column(columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "TEXT")
    private String userAnswer;

    @Column(columnDefinition = "TEXT")
    private String aiFeedback;

    private Integer score;
    private Integer questionNumber;

    public InterviewQA() {}

    // Getters
    public Long getId() { return id; }
    public Long getSessionId() { return sessionId; }
    public String getQuestion() { return question; }
    public String getUserAnswer() { return userAnswer; }
    public String getAiFeedback() { return aiFeedback; }
    public Integer getScore() { return score; }
    public Integer getQuestionNumber() { return questionNumber; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }
    public void setQuestion(String question) { this.question = question; }
    public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }
    public void setAiFeedback(String aiFeedback) { this.aiFeedback = aiFeedback; }
    public void setScore(Integer score) { this.score = score; }
    public void setQuestionNumber(Integer questionNumber) { this.questionNumber = questionNumber; }
}