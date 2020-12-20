package com.buinevich.task5.model.dto;

import com.buinevich.task5.services.util.Move;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoveRequest {
    String gameName;
    String userName;
    Move move;
}
