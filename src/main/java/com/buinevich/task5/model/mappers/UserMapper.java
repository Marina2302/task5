package com.buinevich.task5.model.mappers;

import com.buinevich.task5.model.dto.AuthRequest;
import com.buinevich.task5.model.entities.User;

public class UserMapper {

    public static User UserRequestToUser(AuthRequest authRequest) {
        return new User(authRequest.getLogin(),
                authRequest.getPassword());
    }

}
