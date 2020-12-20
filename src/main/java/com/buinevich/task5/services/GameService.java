package com.buinevich.task5.services;

import com.buinevich.task5.exceptions.AlreadyExistsException;
import com.buinevich.task5.exceptions.BadRequestException;
import com.buinevich.task5.exceptions.CantJoinException;
import com.buinevich.task5.exceptions.MoveException;
import com.buinevich.task5.exceptions.NotFoundException;
import com.buinevich.task5.model.dto.GameRequest;
import com.buinevich.task5.model.dto.GameResponse;
import com.buinevich.task5.model.dto.MoveRequest;
import com.buinevich.task5.model.entities.Game;
import com.buinevich.task5.model.entities.User;
import com.buinevich.task5.model.mappers.GameMapper;
import com.buinevich.task5.model.repositories.GameRepo;
import com.buinevich.task5.model.repositories.TagRepo;
import com.buinevich.task5.model.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class GameService {
    private static final String GAME_NOT_FOUND = "Game with this name not exists.";
    private static final String PLAYER_NOT_FOUND = "Player with this name not exists.";
    private static final String TAG_NOT_FOUND = "Tag not exists.";
    private static final String GAME_CANT_BE_CREATED_WITHOUT_PLAYER_ONE = "Game cant't be created without player one.";
    private static final String GAME_ALREADY_EXISTS = "Game with this name already exists.";
    private static final String GAME_IS_FULL = "There is no free seats in the game.";
    private static final String GAME_OVER = "The game is over.";
    private static final String NOT_YOUR_TURN = "Now is not your turn.";
    private static final String WRONG_MOVE = "You can't move like that.";

    private GameRepo gameRepo;
    private TagRepo tagRepo;
    private UserRepo userRepo;
    private GameMapper gameMapper;

    public Collection<GameResponse> getGames(String[] tags) {
        return Arrays.stream(tags)
                .filter(tag -> tagRepo.existsByText(tag))
                .map(tag -> tagRepo.findByText(tag).orElseThrow(() -> new NotFoundException(TAG_NOT_FOUND)).getGames())
                .flatMap(List::stream)
                .map(game -> gameMapper.gameToGameResponse(game))
                .collect(Collectors.toCollection(HashSet::new));
    }

    public GameResponse createGame(GameRequest gameRequest) {
        if (gameRequest.getPlayerOne() != null) {
            throw new BadRequestException(GAME_CANT_BE_CREATED_WITHOUT_PLAYER_ONE);
        }
        if (gameRepo.existsByName(gameRequest.getName())) {
            throw new AlreadyExistsException(GAME_ALREADY_EXISTS);
        }
        Game newGame = gameMapper.gameRequestToGame(gameRequest);
        gameRepo.save(newGame);
        return gameMapper.gameToGameResponse(newGame);
    }

    public GameResponse joinTheGame(String gameName, String userName) {
        Game game = gameRepo.findByName(gameName).orElseThrow(() -> new NotFoundException(GAME_NOT_FOUND));
        if (game.getPlayerTwo() != null) {
            throw new CantJoinException(GAME_IS_FULL);
        }
        User playerTwo = userRepo.findByName(userName).orElseThrow(() -> new NotFoundException(PLAYER_NOT_FOUND));
        game.setPlayerTwo(playerTwo);
        game.setTurn(game.getPlayerOne());
        gameRepo.save(game);
        return gameMapper.gameToGameResponse(game);
    }

    public GameResponse getGame(String gameName) {
        return gameMapper.gameToGameResponse(gameRepo.findByName(gameName).orElseThrow(() -> new NotFoundException(GAME_NOT_FOUND)));
    }


    public GameResponse makeMove(MoveRequest moveRequest) {
        Game game = gameRepo.findByName(moveRequest.getGameName()).orElseThrow(() -> new NotFoundException(GAME_NOT_FOUND));
        if (game.getWinner() != null) {
            throw new MoveException(GAME_OVER);
        }
        User user = userRepo.findByName(moveRequest.getUserName()).orElseThrow(() -> new NotFoundException(PLAYER_NOT_FOUND));
        if (!game.getTurn().getName().equals(moveRequest.getUserName())) {
            throw new MoveException(NOT_YOUR_TURN);
        }
        switch (moveRequest.getMove()) {
            case MOVE_ONE:
                checkOccupied(game.getMoveOne());
                game.setMoveOne(user);
                break;
            case MOVE_TWO:
                checkOccupied(game.getMoveTwo());
                game.setMoveTwo(user);
                break;
            case MOVE_THREE:
                checkOccupied(game.getMoveThree());
                game.setMoveThree(user);
                break;
            case MOVE_FOUR:
                checkOccupied(game.getMoveFour());
                game.setMoveFour(user);
                break;
            case MOVE_FIVE:
                checkOccupied(game.getMoveFive());
                game.setMoveFive(user);
                break;
            case MOVE_SIX:
                checkOccupied(game.getMoveSix());
                game.setMoveSix(user);
                break;
            case MOVE_SEVEN:
                checkOccupied(game.getMoveSeven());
                game.setMoveSeven(user);
                break;
            case MOVE_EIGHT:
                checkOccupied(game.getMoveEight());
                game.setMoveEight(user);
                break;
            case MOVE_NINE:
                checkOccupied(game.getMoveNine());
                game.setMoveNine(user);
                break;
            default:
                throw new MoveException(WRONG_MOVE);
        }
        if (!checkWinner(game)) passMove(game);
        gameRepo.save(game);
        return gameMapper.gameToGameResponse(game);
    }

    private boolean checkWinner(Game game) {
        if (checkRow(game.getMoveOne(), game.getMoveTwo(), game.getMoveThree(), game)) return true;
        if (checkRow(game.getMoveFour(), game.getMoveFive(), game.getMoveSix(), game)) return true;
        if (checkRow(game.getMoveSeven(), game.getMoveEight(), game.getMoveNine(), game)) return true;

        if (checkRow(game.getMoveOne(), game.getMoveFour(), game.getMoveSeven(), game)) return true;
        if (checkRow(game.getMoveTwo(), game.getMoveFive(), game.getMoveEight(), game)) return true;
        if (checkRow(game.getMoveThree(), game.getMoveSix(), game.getMoveNine(), game)) return true;

        if (checkRow(game.getMoveOne(), game.getMoveFive(), game.getMoveNine(), game)) return true;
        if (checkRow(game.getMoveThree(), game.getMoveFive(), game.getMoveSeven(), game)) return true;
        return false;
    }

    private boolean checkRow(User firstCell, User secondCell, User thirdCell, Game game) {
        if ((firstCell != null && secondCell != null && thirdCell != null) && (firstCell.equals(secondCell) && firstCell.equals(thirdCell))) {
            game.setWinner(firstCell);
            return true;
        }
        return false;
    }


    private void passMove(Game game) {
        if (game.getTurn().equals(game.getPlayerOne())) {
            game.setTurn(game.getPlayerTwo());
        } else {
            game.setTurn(game.getPlayerOne());
        }
    }

    private void checkOccupied(User moveOne) {
        if (moveOne != null) throw new MoveException(WRONG_MOVE);
    }
}
