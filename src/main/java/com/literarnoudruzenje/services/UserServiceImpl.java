package com.literarnoudruzenje.services;

import com.literarnoudruzenje.dto.UserDTO;
import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserServiceInterface {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private UserRepository userRepository;

   @Override
    public List<UserDTO> findAllUsers() {
        this.userRepository.findAllUsers();
        List<UserDTO> users = new ArrayList<>();
        for (User u : this.userRepository.findAllUsers()) {
            users.add(new UserDTO(u));
        }
        return users;
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findOneById(id);
    }

    @Override
    public  User saveUser(User user){ return this.userRepository.save(user);}

}
