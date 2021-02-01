package com.literarnoudruzenje.services;

import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public List<User> findByType(String type){ return this.userRepository.findByType(type);}

    public User findById(Long id){ return  this.userRepository.findOneById(id);}
}
