package com.buinevich.task5.model.mappers;

import com.buinevich.task5.exceptions.NotFoundException;
import com.buinevich.task5.model.dto.GameRequest;
import com.buinevich.task5.model.dto.GameResponse;
import com.buinevich.task5.model.entities.Game;
import com.buinevich.task5.model.entities.Tag;
import com.buinevich.task5.model.repositories.TagRepo;
import com.buinevich.task5.model.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GameMapper {

    private UserRepo userRepo;
    private TagRepo tagRepo;

    public Game gameRequestToGame(GameRequest gameRequest) {
        Game game = Game.builder()
                .name(gameRequest.getName())
                .playerOne(gameRequest.getPlayerOne() != null
                        ? userRepo.findById(gameRequest.getPlayerOne())
                        .orElseThrow(() -> new NotFoundException("User with this name not found."))
                        : null)
                .playerTwo(gameRequest.getPlayerTwo() != null
                        ? userRepo.findById(gameRequest.getPlayerTwo())
                        .orElseThrow(() -> new NotFoundException("User with this name not found."))
                        : null)
                .tags((gameRequest.getTags() != null && !gameRequest.getTags().isEmpty())
                        ? gameRequest.getTags().stream()
                        .map(tagText -> {
                            Tag tag = tagRepo.findByText(tagText).orElseGet(() -> tagRepo.save(Tag.builder()
                                            .text(tagText)
                                            .games(new ArrayList<>())
                                            .build()));
                            tag.setPopularity(tag.getPopularity() + 1L);
                            return tag;
                        })
                        .collect(Collectors.toList())
                        : new ArrayList<>())
                .build();
        game.getTags().forEach(tag -> {
            if (!tag.getGames().contains(game)) {
                tag.getGames().add(game);
            }
        });
        return game;
    }

    public GameResponse gameToGameResponse(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .name(game.getName())
                .playerOne(game.getPlayerOne() != null ? game.getPlayerOne().getName() : null)
                .playerTwo(game.getPlayerTwo() != null ? game.getPlayerTwo().getName() : null)
                .winner(game.getWinner() != null ? game.getWinner().getName() : null)
                .turn(game.getTurn() != null ? game.getTurn().getName() : null)
                .tags(game.getTags().stream().map(Tag::getText).collect(Collectors.toList()))
                .moveOne(game.getMoveOne() != null ? game.getMoveOne().getName() : null)
                .moveTwo(game.getMoveTwo() != null ? game.getMoveTwo().getName() : null)
                .moveThree(game.getMoveThree() != null ? game.getMoveThree().getName() : null)
                .moveFour(game.getMoveFour() != null ? game.getMoveFour().getName() : null)
                .moveFive(game.getMoveFive() != null ? game.getMoveFive().getName() : null)
                .moveSix(game.getMoveSix() != null ? game.getMoveSix().getName() : null)
                .moveSeven(game.getMoveSeven() != null ? game.getMoveSeven().getName() : null)
                .moveEight(game.getMoveEight() != null ? game.getMoveEight().getName() : null)
                .moveNine(game.getMoveNine() != null ? game.getMoveNine().getName() : null)
                .build();

    }
}
