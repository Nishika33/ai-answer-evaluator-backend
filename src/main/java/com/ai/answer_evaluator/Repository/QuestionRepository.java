package com.ai.answer_evaluator.Repository;

import com.ai.answer_evaluator.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {}


