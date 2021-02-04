package com.literarnoudruzenje.handlers;

import com.literarnoudruzenje.model.BetaReader;
import com.literarnoudruzenje.repository.BetaReaderRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignBetaReaderHandler implements ExecutionListener {

    @Autowired
    BetaReaderRepository betaReaderRepository;

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String username = (String) delegateExecution.getVariable("assignedBetaReader");
        BetaReader br = betaReaderRepository.findByUsername(username);
        delegateExecution.setVariable("brEmail", br.getEmail());
    }
}
