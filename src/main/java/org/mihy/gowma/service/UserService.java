/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;


import org.mihy.gowma.model.AuthenticatedUser;
import org.mihy.gowma.model.User;
import org.mihy.gowma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User create(User user) {
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setCreatedBy(authenticatedUser.getId());
        user.setCreatedDate(LocalDateTime.now());
        return userRepository.create(user);
    }

    public User get(int userId) {
        return userRepository.get(userId);
    }

    public User update(User user) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setLastModifiedBy(authenticatedUser.getId());
        user.setLastModifiedDate(LocalDateTime.now());
        return userRepository.update(user);
    }

    public void delete(int userId) {
        userRepository.delete(userId);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}