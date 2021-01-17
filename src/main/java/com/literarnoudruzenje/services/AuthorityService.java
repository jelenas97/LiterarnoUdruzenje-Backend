package com.literarnoudruzenje.services;

import com.literarnoudruzenje.model.Authority;
import java.util.List;

public interface AuthorityService {
    List<Authority> findById(Long id);

    Authority findByName(String name);
}
