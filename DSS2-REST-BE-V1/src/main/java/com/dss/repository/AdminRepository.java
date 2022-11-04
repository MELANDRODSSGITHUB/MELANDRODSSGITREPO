package com.dss.repository;

import com.dss.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findAdminByEmail(String email);
    Optional<Admin> findAdminByEmailAndPassword(String email,String password);
}
