package com.example.demo.Respositories;

import com.example.demo.Tables.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OTPRepository extends JpaRepository<Verification, Long> {
    Optional<Verification> findByEmail(String email);
}