package com.literarnoudruzenje.services;

import com.literarnoudruzenje.dto.UserDTO;
import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User save(User user);

    User findByUsername(String username);

    public User findByEmail(String email);

    public User findById(Long id);

    public List<UserDTO> findAll();

    public List<User> findByType(String type);

}
