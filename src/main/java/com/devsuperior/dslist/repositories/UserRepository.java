package com.devsuperior.dslist.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.devsuperior.dslist.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
