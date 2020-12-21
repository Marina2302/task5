package com.buinevich.task5.rest;

import com.buinevich.task5.model.dto.GameRequest;
import com.buinevich.task5.model.dto.GameResponse;
import com.buinevich.task5.model.dto.MoveRequest;
import com.buinevich.task5.services.GameService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/games")
@AllArgsConstructor
public class GameController {

    private GameService gameService;

    @GetMapping
    public Collection<GameResponse> getByTag(@RequestParam(required = false, name = "tags") List<String> tags) {
        return gameService.getGames(tags);
    }

    @GetMapping("/{id}")
    public GameResponse getGameStatus(@PathVariable long id) {
        return gameService.getGame(id);
    }

    @PostMapping
    public GameResponse createGame(@Valid @RequestBody GameRequest gameRequest) {
        return gameService.createGame(gameRequest);
    }

    @PostMapping("/{gameId}/{userId}")
    public GameResponse joinGame(@PathVariable Long gameId, @PathVariable Long userId) {
        return gameService.joinTheGame(gameId, userId);
    }

    @PostMapping("/move")
    public GameResponse makeMove(@RequestBody MoveRequest gameRequest) {
        return gameService.makeMove(gameRequest);
    }
}
