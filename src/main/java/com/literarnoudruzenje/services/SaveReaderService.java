package com.literarnoudruzenje.services;

import com.literarnoudruzenje.dto.FormSubmissionDto;
import com.literarnoudruzenje.model.Authority;
import com.literarnoudruzenje.model.Genre;
import com.literarnoudruzenje.model.Reader;
import com.literarnoudruzenje.model.*;
import com.literarnoudruzenje.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaveReaderService implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authorityService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        boolean beta = (boolean)delegateExecution.getVariable("betaReader");
        if (beta) {
            BetaReader user = new BetaReader();
            List<FormSubmissionDto> registration = (List<FormSubmissionDto>)delegateExecution.getVariable("readerForm");
            List<FormSubmissionDto> betaGenres = (List<FormSubmissionDto>)delegateExecution.getVariable("betaReaderForm");
            user.setBetaGenres(betaGenres.get(0).getFieldValues().stream()
                    .map(x -> new Genre(Long.parseLong(x),null)).collect(Collectors.toList()));
            for (FormSubmissionDto formField : registration) {
                if(formField.getFieldId().equals("firstName")) {
                    user.setFirstName(formField.getFieldValue());
                }
                if(formField.getFieldId().equals("lastName")) {
                    user.setLastName(formField.getFieldValue());
                }
                if(formField.getFieldId().equals("email")) {
                    if (userService.findByEmail(formField.getFieldValue()) == null) {
                        user.setEmail(formField.getFieldValue());
                    } else {
                        throw new BpmnError("UserAlreadyExists");
                    }
                }
                if(formField.getFieldId().equals("username")) {
                    if (userService.findByUsername(formField.getFieldValue()) == null) {
                        user.setUsername(formField.getFieldValue());
                    } else {
                        throw new BpmnError("UserAlreadyExists");
                    }
                }
                if(formField.getFieldId().equals("password")) {
                    user.setPassword( passwordEncoder.encode(formField.getFieldValue()));
                }
                if(formField.getFieldId().equals("city")) {
                    user.setCity(formField.getFieldValue());
                }
                if(formField.getFieldId().equals("state")) {
                    user.setState(formField.getFieldValue());
                }
                if(formField.getFieldId().equals("genres")) {
                    user.setGenres(formField.getFieldValues().stream()
                            .map(x -> new Genre(Long.parseLong(x),null)).collect(Collectors.toList()));
                }

            }

            Authority authority = authorityService.findByName("BETAREADER");
            List<Authority> authorities = new ArrayList<>();
            authorities.add(authority);
            user.setAuthorities(authorities);
            userService.saveUser(user);
        } else {
            Reader user = new Reader();
            List<FormSubmissionDto> registration = (List<FormSubmissionDto>)delegateExecution.getVariable("readerForm");


            for (FormSubmissionDto formField : registration) {
                if(formField.getFieldId().equals("firstName")) {
                    user.setFirstName(formField.getFieldValue());
                }
                if(formField.getFieldId().equals("lastName")) {
                    user.setLastName(formField.getFieldValue());
                }
                if(formField.getFieldId().equals("email")) {
                    if (userService.findByEmail(formField.getFieldValue()) == null) {
                        user.setEmail(formField.getFieldValue());
                    } else {
                        throw new BpmnError("UserAlreadyExists");
                    }
                }
                if(formField.getFieldId().equals("username")) {
                    if (userService.findByUsername(formField.getFieldValue()) == null) {
                        user.setUsername(formField.getFieldValue());
                    } else {
                        throw new BpmnError("UserAlreadyExists");
                    }
                }
                if(formField.getFieldId().equals("password")) {
                    user.setPassword( passwordEncoder.encode(formField.getFieldValue()));
                }
                if(formField.getFieldId().equals("city")) {
                    user.setCity(formField.getFieldValue());
                }
                if(formField.getFieldId().equals("state")) {
                    user.setState(formField.getFieldValue());
                }
                if(formField.getFieldId().equals("genres")) {
                    user.setGenres(formField.getFieldValues().stream()
                            .map(x -> new Genre(Long.parseLong(x),null)).collect(Collectors.toList()));
                }

            }

            Authority authority = authorityService.findByName("READER");
            List<Authority> authorities = new ArrayList<>();
            authorities.add(authority);
            user.setAuthorities(authorities);
            userService.saveUser(user);
        }

    }
}
