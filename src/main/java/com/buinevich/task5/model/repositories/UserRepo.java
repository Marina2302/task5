package com.buinevich.task5.model.repositories;

import com.buinevich.task5.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByName(String name);

    Optional<User> findByName(String name);
}
