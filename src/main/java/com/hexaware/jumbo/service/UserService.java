package com.hexaware.jumbo.service;

import java.util.ArrayList;

import com.hexaware.jumbo.model.User;
import com.hexaware.jumbo.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * UserService.
 */
@Service
public class UserService implements UserDetailsService {
    /**
     * UserRepository.
     */
    @Autowired
    private UserRepository userRepository;
    /**
     * PasswordEncoder.
     */
    @Autowired
    private PasswordEncoder bcryptEncoder;

    /**
     * @param username username.
     * @return UserDetails.
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        com.hexaware.jumbo.model.UserDetails user  = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
        new ArrayList<>());
    }

    /**
     * @param user user.
     * @return newuser.
     */
    public com.hexaware.jumbo.model.UserDetails save(final User user) {
        com.hexaware.jumbo.model.UserDetails newUser = new com.hexaware.jumbo.model.UserDetails();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }
}
