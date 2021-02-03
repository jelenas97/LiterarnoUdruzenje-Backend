package com.literarnoudruzenje.services;

import com.literarnoudruzenje.dto.FormSubmissionDto;
import com.literarnoudruzenje.model.BetaReader;
import com.literarnoudruzenje.model.Book;
import com.literarnoudruzenje.model.Genre;
import com.literarnoudruzenje.model.User;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferBetaReadersService implements JavaDelegate {

    @Autowired
    UserService userService;

    @Autowired
    GenreService genreService;

    @Autowired
    BetaReaderService betaReaderService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDto> bookPublishing = (List<FormSubmissionDto>)delegateExecution.getVariable("bookPublishing");
        List<Genre> list =new ArrayList<>();
        for (FormSubmissionDto formField : bookPublishing) {
            if (formField.getFieldId().equals("genres")) {
                list =formField.getFieldValues().stream()
                        .map(x -> new Genre(Long.parseLong(x), null)).collect(Collectors.toList());
            }
        }

        Long genre_id= list.get(0).getId();

        List<BigInteger> betaReaderIdList =betaReaderService.getBetaReadersByGenre(genre_id);
        List<User> allBetaReaders = this.userService.findByType("BETAREADER");
        List<User> betaReadersByGenre = new ArrayList<>();
        for(User u : allBetaReaders){
           for(BigInteger id : betaReaderIdList){
               if(u.getId().equals(id.longValue())){
                   betaReadersByGenre.add(u);
               }
           }
        }
        delegateExecution.setVariable("betaReadersByGenre", betaReadersByGenre);


    }
}
