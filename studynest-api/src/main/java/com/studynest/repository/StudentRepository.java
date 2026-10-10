package com.studynest.repository;

import com.studynest.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByPhone(String phone);
    Optional<Student> findByEmail(String email);

    @Query("SELECT s FROM Student s WHERE LOWER(s.phone) = LOWER(:identifier) OR LOWER(s.email) = LOWER(:identifier)")
    Optional<Student> findByIdentifier(@Param("identifier") String identifier);

    List<Student> findByRole(String role);
}
