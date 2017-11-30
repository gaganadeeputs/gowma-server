/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;


import org.mihy.gowma.model.SecureUser;
import org.mihy.gowma.model.User;
import org.mihy.gowma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User create(User user) {
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        return userRepository.create(user);
    }

    public User get(int userId) {
        return userRepository.get(userId);
    }

    public User update(User user) {
        return userRepository.update(user);
    }

    public void delete(int userId) {
        userRepository.delete(userId);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}