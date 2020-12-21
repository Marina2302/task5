package com.buinevich.task5.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameRequest {

    @NotBlank(message = "Name is mandatory field.")
    String name;
    List<String> tags;
    Long playerOne;
    Long playerTwo;

}
