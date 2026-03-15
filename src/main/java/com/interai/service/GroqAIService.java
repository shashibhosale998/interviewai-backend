package com.interai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class GroqAIService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    @Value("${groq.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    // ✅ Generate Questions with count!
    public String generateQuestions(
            String role,
            String difficulty,
            int questionCount) {

        String prompt = "Generate " + questionCount
                + " interview questions for a "
                + role + " position with "
                + difficulty + " difficulty level. "
                + "Return ONLY a JSON array like this: "
                + "[\"Question 1\", \"Question 2\"] "
                + "No extra text, just the JSON array!";

        return callGroqAPI(prompt);
    }

    // Analyze Answer
    public String analyzeAnswer(
            String question,
            String userAnswer) {

        String prompt = "You are an expert technical interviewer. "
                + "Question: " + question + " "
                + "Candidate Answer: " + userAnswer + " "
                + "Analyze this answer and return ONLY a JSON object: "
                + "{\"score\": 7, \"feedback\": \"feedback here\", "
                + "\"tip\": \"tip here\"} "
                + "Score must be between 1 to 10. "
                + "No extra text, just the JSON object!";

        return callGroqAPI(prompt);
    }

    // Common API Call
    private String callGroqAPI(String prompt) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("temperature", 0.7);

        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        requestBody.put("messages", List.of(message));

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                Map.class
        );

        Map responseBody = response.getBody();
        List choices = (List) responseBody.get("choices");
        Map firstChoice = (Map) choices.get(0);
        Map messageResponse = (Map) firstChoice.get("message");

        return (String) messageResponse.get("content");
    }
}