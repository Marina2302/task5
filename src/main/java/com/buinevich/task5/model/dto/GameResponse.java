package com.buinevich.task5.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameResponse {
    private long id;

    private String name;

    private String playerOne;

    private String playerTwo;

    private String winner;

    private String turn;

    private List<String> tags;

    private String moveOne;
    private String moveTwo;
    private String moveThree;
    private String moveFour;
    private String moveFive;
    private String moveSix;
    private String moveSeven;
    private String moveEight;
    private String moveNine;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameResponse)) return false;

        GameResponse response = (GameResponse) o;

        return name.equals(response.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
