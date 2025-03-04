package org.example.nckh.repository;

import org.example.nckh.model.User; // Đảm bảo đường dẫn đúng tới lớp User
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByVerificationCode(String code);
}
