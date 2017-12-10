/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthenticatedUser implements UserDetails {
    private static final long serialVersionUID = -8756608845278722035L;
    private final User user;
    private final List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    public AuthenticatedUser(User user) {
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
            this.user = user;
            user.getRoles().forEach(role ->authorities.add(new SimpleGrantedAuthority(role.getName())));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }


    public int getId() {
        return this.user.getId();
    }
}