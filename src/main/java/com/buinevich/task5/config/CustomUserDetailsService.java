package com.buinevich.task5.config;

import com.buinevich.task5.model.entities.User;
import com.buinevich.task5.model.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final String USER_NOT_FOUND = "User not found.";

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByName(username).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        return CustomUserDetails.fromUserToCustomUserDetails(user);
    }
}
