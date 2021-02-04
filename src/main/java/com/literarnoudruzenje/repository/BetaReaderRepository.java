package com.literarnoudruzenje.repository;

import com.literarnoudruzenje.model.BetaReader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetaReaderRepository extends JpaRepository<BetaReader, Long> {

    BetaReader findByUsername(String username);
}
