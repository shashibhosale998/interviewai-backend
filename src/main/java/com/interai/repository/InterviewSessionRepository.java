package com.interai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interai.entity.InterviewSession;

@Repository
public interface InterviewSessionRepository
        extends JpaRepository<InterviewSession, Long> {
}