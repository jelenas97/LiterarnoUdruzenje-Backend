package com.literarnoudruzenje.services;

import com.literarnoudruzenje.dto.FormSubmissionDto;
import com.literarnoudruzenje.model.Authority;
import com.literarnoudruzenje.model.BetaReader;
import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.repository.BetaReaderRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.glassfish.jersey.Beta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignPenaltyPoints implements JavaDelegate {

   /* @Autowired
    UserService userService;*/

    @Autowired
    BetaReaderRepository betaReaderRepository;

    @Autowired
    AuthorityService authorityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String username = (String) delegateExecution.getVariable("assignedBetaReader");
        BetaReader br = betaReaderRepository.findByUsername(username);
        Long points = br.getPenaltyPoints();
        Boolean b=false;
        System.out.println(points);

        if(points<5){
            points++;
            System.out.println(points);
            br.setPenaltyPoints(br.getPenaltyPoints() + 1);
            betaReaderRepository.save(br);

        }
        if(points==5){
            System.out.println(points);
            br.setType("READER");
            Authority authority = authorityService.findByName("READER");
            List<Authority> authorities = new ArrayList<>();
            authorities.add(authority);
            br.setAuthorities(authorities);
            betaReaderRepository.save(br);
            b=true;
        }
        delegateExecution.setVariable("brEmail", br.getEmail());
        delegateExecution.setVariable( "fivePoints", b);
    }
}
