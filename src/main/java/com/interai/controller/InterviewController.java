package com.interai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.interai.dto.AnswerRequest;
import com.interai.dto.FeedbackResponse;
import com.interai.dto.StartInterviewRequest;
import com.interai.service.InterviewService;

import java.util.Map;

@RestController
@RequestMapping("/api/interview")
@CrossOrigin(origins = {
	    "http://localhost:3000",
	    "https://interviewai-shashi.vercel.app"
	})
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    // Start Interview
    @PostMapping("/start")
    public Map<String, Object> startInterview(
            @RequestBody StartInterviewRequest request) 
            throws Exception {
        return interviewService.startInterview(request);
    }

    // Submit Answer
    @PostMapping("/answer")
    public FeedbackResponse submitAnswer(
            @RequestBody AnswerRequest request) 
            throws Exception {
        return interviewService.submitAnswer(request);
    }

    // Get Final Report
    @GetMapping("/report/{sessionId}")
    public Map<String, Object> getReport(
            @PathVariable Long sessionId) {
        return interviewService.getReport(sessionId);
    }
}