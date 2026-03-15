package com.interai.service;

import com.interai.dto.AnswerRequest;
import com.interai.dto.FeedbackResponse;
import com.interai.dto.StartInterviewRequest;
import com.interai.entity.InterviewQA;
import com.interai.entity.InterviewSession;
import com.interai.repository.InterviewQARepository;
import com.interai.repository.InterviewSessionRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class InterviewService {

    @Autowired
    private InterviewSessionRepository sessionRepository;

    @Autowired
    private InterviewQARepository qaRepository;

    @Autowired
    private GroqAIService groqAIService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Start Interview
    public Map<String, Object> startInterview(
            StartInterviewRequest request) throws Exception {

        // Create and save session
        InterviewSession session = new InterviewSession();
        session.setCandidateName(request.getCandidateName());
        session.setRole(request.getRole());
        session.setDifficulty(request.getDifficulty());
        session.setTotalScore(0);
        session.setTotalQuestions(request.getQuestionCount());

        // ✅ Fixed - String date instead of LocalDateTime!
        session.setCreatedAt(
            LocalDateTime.now()
            .format(DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        InterviewSession savedSession =
                sessionRepository.save(session);

        // Generate questions
        String questionsJson = groqAIService
                .generateQuestions(
                        request.getRole(),
                        request.getDifficulty(),
                        request.getQuestionCount()
                );

        // Parse questions
        List<String> questions = objectMapper
                .readValue(questionsJson, List.class);

        return Map.of(
                "sessionId", savedSession.getId(),
                "candidateName", savedSession.getCandidateName(),
                "role", savedSession.getRole(),
                "difficulty", savedSession.getDifficulty(),
                "questionCount", savedSession.getTotalQuestions(),
                "questions", questions
        );
    }

    // Submit Answer
    public FeedbackResponse submitAnswer(
            AnswerRequest request) throws Exception {

        // Get AI feedback
        String feedbackJson = groqAIService
                .analyzeAnswer(
                        request.getQuestion(),
                        request.getUserAnswer()
                );

        // Parse feedback
        Map<String, Object> feedbackMap = objectMapper
                .readValue(feedbackJson, Map.class);

        Integer score = (Integer) feedbackMap.get("score");
        String feedback = (String) feedbackMap.get("feedback");
        String tip = (String) feedbackMap.get("tip");

        // Save QA to database
        InterviewQA qa = new InterviewQA();
        qa.setSessionId(request.getSessionId());
        qa.setQuestion(request.getQuestion());
        qa.setUserAnswer(request.getUserAnswer());
        qa.setAiFeedback(feedback);
        qa.setScore(score);
        qa.setQuestionNumber(request.getQuestionNumber());
        qaRepository.save(qa);

        // Update session total score
        InterviewSession session = sessionRepository
                .findById(request.getSessionId())
                .orElseThrow(() ->
                new RuntimeException("Session not found!"));

        session.setTotalScore(session.getTotalScore() + score);
        sessionRepository.save(session);

        // Return feedback
        FeedbackResponse response = new FeedbackResponse();
        response.setScore(score);
        response.setFeedback(feedback);
        response.setTip(tip);

        return response;
    }

    // Get Final Report
    public Map<String, Object> getReport(Long sessionId) {
        InterviewSession session = sessionRepository
                .findById(sessionId)
                .orElseThrow(() ->
                new RuntimeException("Session not found!"));

        List<InterviewQA> qaList = qaRepository
                .findBySessionId(sessionId);

        int totalQuestions = session.getTotalQuestions();
        int averageScore = totalQuestions > 0
                ? session.getTotalScore() / totalQuestions
                : 0;

        return Map.of(
                "session", session,
                "qaList", qaList,
                "totalScore", session.getTotalScore(),
                "totalQuestions", totalQuestions,
                "averageScore", averageScore
        );
    }
}