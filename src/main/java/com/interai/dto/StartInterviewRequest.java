package com.interai.dto;

public class StartInterviewRequest {
    private String candidateName;
    private String role;
    private String difficulty;
    private Integer questionCount = 5;

    // Getters
    public String getCandidateName() { return candidateName; }
    public String getRole() { return role; }
    public String getDifficulty() { return difficulty; }
    public Integer getQuestionCount() { return questionCount; }

    // Setters
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }
    public void setRole(String role) { this.role = role; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public void setQuestionCount(Integer questionCount) { this.questionCount = questionCount; }
}