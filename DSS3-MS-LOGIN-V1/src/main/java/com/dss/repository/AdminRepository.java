package com.dss.repository;

import com.dss.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<User, Integer> {
    Optional<User> findAdminByEmail(String email);
    Optional<User> findAdminByEmailAndPassword(String email, String password);
}
