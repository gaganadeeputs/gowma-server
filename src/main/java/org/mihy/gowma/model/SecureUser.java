package org.mihy.gowma.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecureUser implements UserDetails {
	private static final long serialVersionUID = -8756608845278722035L;
	private final User user;
	private final List<SimpleGrantedAuthority> authorities = new ArrayList<>();

	public SecureUser(User user) {
		if (user == null) {
			throw new UsernameNotFoundException("UserRequest not found");
		} else {
			this.user = user;
			authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
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