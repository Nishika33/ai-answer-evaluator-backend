package com.ai.answer_evaluator.Controller;

import com.ai.answer_evaluator.Entity.Users;
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
    public String register(@RequestBody Users user) {
        userRepo.save(user);
        return "User Registered Successfully";
    }

    // LOGIN
    @PostMapping("/login")
    public Users login(@RequestBody Users user) {

        Users dbUser = userRepo.findByUsername(user.getUsername());

        if (dbUser == null) {
            throw new RuntimeException("User not found");
        }

        if (!dbUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return dbUser;
    }
}






   

    