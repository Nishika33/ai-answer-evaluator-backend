package com.ai.answer_evaluator.Repository;

import com.ai.answer_evaluator.Entity.Question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findBySubject(String subject);}


