/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.model.SecureUser;
import org.mihy.gowma.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static SecureUser SECURE_USER;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        User rootUser = new User();
        rootUser.setEmail("Root@gowmaserice.com");
        //PASSword,2017
        rootUser.setPassword("$2a$10$HK.wRgzpnScSpOyU8foLEO1u1AqdG4RNzVO1PZsFsX263W8VKe9S.");
        SECURE_USER = new SecureUser(rootUser);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (SECURE_USER.getUsername().equals(email)) {
            return SECURE_USER;
        }
        User user = this.userService.findByEmail(email);
        return new SecureUser(user);
    }
}