package com.literarnoudruzenje.services;

import com.literarnoudruzenje.dto.NoteDto;
import com.literarnoudruzenje.model.User;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GetEditorsAndCheckAvailable implements JavaDelegate {

    @Autowired
    private UserService userService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<User> editors = userService.findByType("EDITOR");
        List<User> editorsSelected = (List<User>) delegateExecution.getVariable("editorsList");
        List<User> newEditors = new ArrayList<User>();
        for(User editor : editors) {
            boolean found = false;
            for(User editor2 : editorsSelected){
                if(editor.getId().equals(editor2.getId())){
                    found = true;
                    break;
                }
            }
            if(!found) {
                newEditors.add(editor);
            }
        }

        if(newEditors.size() > 0) {
            delegateExecution.setVariable("editorsAvailable" , true);
        } else {
            delegateExecution.setVariable("editorsAvailable" , false);
        }


    }
}
