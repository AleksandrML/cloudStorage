package com.example.cloudStorage.repositories;

import com.example.cloudStorage.models.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ProjectUser, String> {
    Optional<ProjectUser> findByUsername(String username);
}
