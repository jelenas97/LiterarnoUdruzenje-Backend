package com.literarnoudruzenje.services;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SendActivationLink implements JavaDelegate {

    private final EmailService emailService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String email = (String)delegateExecution.getVariable("email");
        emailService.sendNotification(email,"Ovo je neki content", "Naslov mejla");
        System.out.println(email);
    }

}
