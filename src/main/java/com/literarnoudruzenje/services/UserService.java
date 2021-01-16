package com.literarnoudruzenje.services;

import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
