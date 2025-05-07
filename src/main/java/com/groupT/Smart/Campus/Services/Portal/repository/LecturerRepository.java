package com.groupT.Smart.Campus.Services.Portal.repository;

import com.groupT.Smart.Campus.Services.Portal.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer,Long> {

    boolean existsByEmail(String email);

    Optional<Lecturer> findByUsername(String username);

    Optional<Lecturer> findByFullName(String fullName);
}
