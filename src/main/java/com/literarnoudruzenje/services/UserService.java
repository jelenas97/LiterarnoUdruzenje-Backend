package com.literarnoudruzenje.services;

import com.literarnoudruzenje.dto.UserDTO;
import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    User save(User user);

    User findById(Long id);

    User findByEmail(String email);

    User findByUsername(String username);

    List<UserDTO> findAll();

    List<User> findAllByType(String type);
}
