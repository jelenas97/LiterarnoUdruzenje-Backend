package com.literarnoudruzenje.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Service
public class BetaReaderService {

    @Autowired
    EntityManager entityManager;

    public List<BigInteger> getBetaReadersByGenre(Long genre_id) {
        Query query = entityManager.createNativeQuery("SELECT user_id FROM betareader_genres WHERE genre_id = :id");
        query.setParameter("id", genre_id);
        List<BigInteger> brList = query.getResultList();

        return brList;
    }
}
