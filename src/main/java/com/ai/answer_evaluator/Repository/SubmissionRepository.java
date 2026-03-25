package com.ai.answer_evaluator.Repository;

import com.ai.answer_evaluator.Entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findBySubject(String subject);

    List<Submission> findByQuestionId(Long questionId);

    List<Submission> findByStudentUsername(String studentUsername);

    List<Submission> findByRollNumber(String rollNumber);
}


