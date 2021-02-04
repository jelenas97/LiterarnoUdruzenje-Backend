package com.literarnoudruzenje.handlers;

import com.literarnoudruzenje.model.BetaReader;
import com.literarnoudruzenje.model.Genre;
import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.repository.UserRepository;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GetBetaReadersHandler implements TaskListener {

    @Autowired
    UserRepository userRepository;

    @Override
    public void notify(DelegateTask delegateTask) {

        TaskFormData taskFormData = delegateTask.getExecution().getProcessEngineServices()
                .getFormService().getTaskFormData(delegateTask.getId());
        List<User> betaReadersByGenre = (List<User>) delegateTask.getVariable("betaReadersByGenre");
        List<FormField> fields = taskFormData.getFormFields();
        if(fields != null) {
            for(FormField f: fields) {
                if(f.getId().equals("betaReadersByGenre")) {
                    HashMap<String, String> values = (HashMap<String, String>) f.getType().getInformation("values");
                    values.clear();
                    for(User u : betaReadersByGenre) {
                        values.put(u.getId().toString(),u.getUsername());
                    }
                }
            }
        }

    }
}
