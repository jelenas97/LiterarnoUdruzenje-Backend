package com.literarnoudruzenje.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentService {
    public boolean pay(Long ccn, Long cvvCode, String nameOnCard) {
        Random random = new Random();
        return random.nextBoolean();
    }
}
