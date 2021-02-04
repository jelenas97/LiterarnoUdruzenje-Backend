package com.literarnoudruzenje.repository;

import com.literarnoudruzenje.model.PublishedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishedBookRepository extends JpaRepository<PublishedBook, Long> {

}
