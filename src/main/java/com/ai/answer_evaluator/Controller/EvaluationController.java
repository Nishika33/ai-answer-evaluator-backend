package com.ai.answer_evaluator.Controller;

import com.ai.answer_evaluator.Entity.*;
import com.ai.answer_evaluator.Repository.*;
import com.ai.answer_evaluator.Service.EvaluationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class EvaluationController {

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private SubmissionRepository submissionRepo;

    @Autowired
    private EvaluationService evalService;

    // FACULTY ADD QUESTION
    @PostMapping("/question")
    public Question addQuestion(@RequestBody Question q) {
        return questionRepo.save(q);
    }

    // STUDENT SUBMIT ANSWER
    @PostMapping("/submit/{id}")
    public Submission submit(@PathVariable Long id, @RequestBody Submission req) {
        Submission submission = evalService.evaluate(id, req.getStudentAnswer());

        submission.setStudentUsername(req.getStudentUsername());
        submission.setRollNumber(req.getRollNumber());
        submission.setSubject(req.getSubject());
        submission.setQuestionId(id);

    return submissionRepo.save(submission);
}

    // VIEW ALL RESULTS
    @GetMapping("/results")
    public List<Submission> getAllResults() {
        return submissionRepo.findAll();
    }

    // FILTER BY SUBJECT
    @GetMapping("/results/subject/{subject}")
    public List<Submission> bySubject(@PathVariable String subject){
        return submissionRepo.findBySubject(subject);
    }

    // FILTER BY QUESTION ID
    @GetMapping("/results/question/{id}")
    public List<Submission> byQuestion(@PathVariable Long id){
        return submissionRepo.findByQuestionId(id);
    }

    // FILTER BY STUDENT NAME
    @GetMapping("/results/student/{name}")
    public List<Submission> byStudent(@PathVariable String name){
        return submissionRepo.findByStudentUsername(name);
    }

    // FILTER BY ROLL NUMBER
    @GetMapping("/results/roll/{roll}")
    public List<Submission> byRoll(@PathVariable String roll){
        return submissionRepo.findByRollNumber(roll);
    }

    // AI PROMPT SEARCH
    @PostMapping("/results/prompt")
    public List<Submission> searchByPrompt(@RequestBody String prompt){

        prompt = prompt.toLowerCase();

        // Detect roll number
        if(prompt.contains("roll")){
            String roll = prompt.replaceAll("[^0-9]", "");
            return submissionRepo.findByRollNumber(roll);
        }

        // Detect question id
        if(prompt.contains("question")){
            String id = prompt.replaceAll("[^0-9]", "");
            return submissionRepo.findByQuestionId(Long.parseLong(id));
        }

        // Detect student name
        if(prompt.contains("student")){
            String name = prompt.replace("student", "").trim();
            return submissionRepo.findByStudentUsername(name);
        }

        // Detect subject
        if(prompt.contains("subject")){
            String subject = prompt.replace("subject", "").trim();
            return submissionRepo.findBySubject(subject);
        }

        // Default return all
        return submissionRepo.findAll();
    }
}





