package com.literarnoudruzenje.services;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SendActivationLink implements JavaDelegate {

    private final EmailService emailService;

    private Expression content;

    private Expression receiver;

    private Expression subject;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        //String content = (String) this.content.getValue(delegateExecution);
        //String receiver = (String) this.receiver.getValue(delegateExecution);
        //String subject = (String) this.subject.getValue(delegateExecution);

        //emailService.sendNotification(receiver,content, subject);
    }

}
