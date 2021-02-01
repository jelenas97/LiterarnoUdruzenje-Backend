package com.literarnoudruzenje.services;

import com.literarnoudruzenje.dto.FormSubmissionDto;
import com.literarnoudruzenje.model.BetaReader;
import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.repository.BetaReaderRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignPenaltyPoints implements JavaDelegate {

   /* @Autowired
    UserService userService;*/

    @Autowired
    BetaReaderRepository betaReaderRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String username = (String) delegateExecution.getVariable("assignedBetaReader");

        Long points =betaReaderRepository.findByUsername(username).getPenaltyPoints();
        Boolean b=false;
        System.out.println(points);

        if(points<5){
            points++;
            System.out.println(points);
        }
        if(points==5){
           System.out.println(points);
           //skinuti betausera ili mogucnost
           b=true;
        }

        delegateExecution.setVariable( "fivePoints", b);
    }
}
