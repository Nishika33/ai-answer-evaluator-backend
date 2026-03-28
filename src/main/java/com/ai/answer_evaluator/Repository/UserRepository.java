package com.ai.answer_evaluator.Repository;

import com.ai.answer_evaluator.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByUsername(String username);
}