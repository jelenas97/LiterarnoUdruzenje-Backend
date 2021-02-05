package com.literarnoudruzenje.services;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProceedPayment implements JavaDelegate {
    @Autowired
    PaymentService paymentService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long ccn = (Long) delegateExecution.getVariable("ccn");
        Long cvvCode = (Long) delegateExecution.getVariable("cvvcode");
        String nameOnCard = (String) delegateExecution.getVariable("nameOnCard");
        boolean succeded;
        try {
            succeded = paymentService.pay(ccn,cvvCode,nameOnCard);
        } catch (Exception e) {
            throw new BpmnError("PaymentFailed");
        }
        if(succeded) {
            delegateExecution.setVariable("paymentSucceed", true);
        } else {
            delegateExecution.setVariable("paymentSucceed", false);

        }

    }
}
