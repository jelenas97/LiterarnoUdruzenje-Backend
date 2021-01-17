package com.literarnoudruzenje.services;

import com.literarnoudruzenje.dto.UserDTO;
import com.literarnoudruzenje.model.User;
import java.util.List;

public interface UserServiceInterface {

    List<UserDTO> findAllUsers();

    User findByEmail(String email);

    User findByUsername(String username);

    User findById(Long id);

    User saveUser(User user);
}
