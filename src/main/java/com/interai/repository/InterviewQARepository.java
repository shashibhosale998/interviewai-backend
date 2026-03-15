package com.interai.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interai.entity.InterviewQA;

import java.util.List;

@Repository
public interface InterviewQARepository
        extends JpaRepository<InterviewQA, Long> {

    List<InterviewQA> findBySessionId(Long sessionId);
}