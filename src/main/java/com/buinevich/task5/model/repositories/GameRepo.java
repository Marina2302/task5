package com.buinevich.task5.model.repositories;

import com.buinevich.task5.model.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepo extends JpaRepository<Game, Long> {
    boolean existsByName(String name);

    Optional<Game> findByName(String name);

    List<Game> findAllByWinnerIsNull();
}
