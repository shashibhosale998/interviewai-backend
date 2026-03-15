package com.interai.dto;


public class FeedbackResponse {
    private Integer score;
    private String feedback;
    private String tip;

    // Getters
    public Integer getScore() { return score; }
    public String getFeedback() { return feedback; }
    public String getTip() { return tip; }

    // Setters
    public void setScore(Integer score) { this.score = score; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public void setTip(String tip) { this.tip = tip; }
}