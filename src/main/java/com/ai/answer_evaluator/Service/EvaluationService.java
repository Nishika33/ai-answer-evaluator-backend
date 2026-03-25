package com.ai.answer_evaluator.Service;

import com.ai.answer_evaluator.Entity.*;
import com.ai.answer_evaluator.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private SubmissionRepository submissionRepo;

    @Autowired
    private SimilarityService similarityService;

    public Submission evaluate(Long qid, String studentAnswer) {
        Question q = questionRepo.findById(qid).orElseThrow();

        double sim = similarityService.cosineSimilarity(q.getModelAnswer(), studentAnswer);
        int marks = (int) Math.round(sim * q.getMaxScore());

        Submission s = new Submission();
        s.setQuestionId(qid);
        s.setStudentAnswer(studentAnswer);
        s.setSimilarityScore(sim);
        s.setPredictedMarks(marks);
        s.setFeedback(similarityService.generateFeedback(sim));

        return submissionRepo.save(s);
    }
}

