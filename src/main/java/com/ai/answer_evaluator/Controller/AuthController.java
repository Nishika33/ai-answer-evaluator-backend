package com.ai.answer_evaluator.Controller;

import com.ai.answer_evaluator.Entity.User;
import com.ai.answer_evaluator.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        userRepo.save(user);
        return "User Registered Successfully";
    }

    // LOGIN
    @PostMapping("/login")
    public User login(@RequestBody User user) {

        User dbUser = userRepo.findByUsername(user.getUsername());

        if (dbUser == null) {
            throw new RuntimeException("User not found");
        }

        if (!dbUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return dbUser;
    }
}






   

    