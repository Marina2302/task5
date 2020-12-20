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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(name = "/game")
@AllArgsConstructor
public class GameController {

    private GameService gameService;

    @GetMapping("/{tags}")
    public Collection<GameResponse> getByTag(@PathVariable String[] tags) {
        return gameService.getGames(tags);
    }

    @GetMapping("/status/{gameName}")
    public GameResponse getGameStatus(@PathVariable String gameName) {
        return gameService.getGame(gameName);
    }

    @PostMapping
    public GameResponse createGame(@Valid @RequestBody GameRequest gameRequest) {
        return gameService.createGame(gameRequest);
    }

    @PutMapping("/{gameName}/{userName}")
    public GameResponse joinGame(@PathVariable String gameName, @PathVariable String userName) {
        return gameService.joinTheGame(gameName, userName);
    }

    @PutMapping
    public GameResponse makeMove(@RequestBody MoveRequest gameRequest) {
        return gameService.makeMove(gameRequest);
    }
}
