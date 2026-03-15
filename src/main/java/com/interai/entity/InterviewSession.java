package com.interai.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "interview_sessions")
public class InterviewSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String candidateName;
    private String role;
    private String difficulty;
    private Integer totalScore;
    private Integer totalQuestions;

    // ✅ String instead of LocalDateTime!
    private String createdAt;

    public InterviewSession() {}

    // Getters
    public Long getId() { return id; }
    public String getCandidateName() { return candidateName; }
    public String getRole() { return role; }
    public String getDifficulty() { return difficulty; }
    public Integer getTotalScore() { return totalScore; }
    public Integer getTotalQuestions() { return totalQuestions; }
    public String getCreatedAt() { return createdAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setCandidateName(String n) { this.candidateName = n; }
    public void setRole(String role) { this.role = role; }
    public void setDifficulty(String d) { this.difficulty = d; }
    public void setTotalScore(Integer s) { this.totalScore = s; }
    public void setTotalQuestions(Integer q) { this.totalQuestions = q; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}