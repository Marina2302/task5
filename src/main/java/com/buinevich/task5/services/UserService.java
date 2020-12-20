package com.buinevich.task5.services;

import com.buinevich.task5.exceptions.NotFoundException;
import com.buinevich.task5.model.dto.AuthRequest;
import com.buinevich.task5.model.entities.User;
import com.buinevich.task5.model.mappers.UserMapper;
import com.buinevich.task5.model.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private static final String USER_NOT_FOUND = "User not found.";
    private static final String INVALID_CREDENTIALS = "Invalid name or password.";

    private UserRepo userRepo;
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User save(AuthRequest authRequest) {
        authRequest.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        User createdUser = UserMapper.UserRequestToUser(authRequest);
        return userRepo.save(createdUser);
    }

    public User findByLoginAndPassword(String name, String password) {
        User user = userRepo.findByName(name).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        throw new UsernameNotFoundException(INVALID_CREDENTIALS);
    }


}
