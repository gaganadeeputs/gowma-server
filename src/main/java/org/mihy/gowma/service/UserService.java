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
	private UserRepository userRepository;


	public User create(User user){
		return userRepository.create(user);
	}

    public User get(int userId){
        return userRepository.get(userId);
    }

    public User update(User user){
        return userRepository.update(user);
    }

    public void delete(int userId) {
        userRepository.delete(userId);
    }

    public UserDetails findByEmail(String email) {
        return new SecureUser(userRepository.findByEmail(email));
    }
}