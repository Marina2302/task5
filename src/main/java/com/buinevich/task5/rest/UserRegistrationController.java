package com.buinevich.task5.rest;

import com.buinevich.task5.config.JwtProvider;
import com.buinevich.task5.model.dto.AuthRequest;
import com.buinevich.task5.model.dto.AuthResponse;
import com.buinevich.task5.model.entities.User;
import com.buinevich.task5.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserRegistrationController {

    private UserService userService;
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid AuthRequest AuthRequest) {
        userService.save(AuthRequest);
        return "OK";
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.getName(), user.getId());
        return new AuthResponse(token);
    }
}
