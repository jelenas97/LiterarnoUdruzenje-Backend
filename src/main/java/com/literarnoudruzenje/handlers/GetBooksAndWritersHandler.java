package com.literarnoudruzenje.handlers;

import com.literarnoudruzenje.model.Book;
import com.literarnoudruzenje.model.Genre;
import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.services.UserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GetBooksAndWritersHandler implements TaskListener {

    private final UserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormData = delegateTask.getExecution().getProcessEngineServices()
                .getFormService().getTaskFormData(delegateTask.getId());
        List<FormField> fields = taskFormData.getFormFields();

        List<User> users = userService.findAllByType("WRITER");

        if(fields != null) {
            for(FormField f: fields) {
                if(f.getId().equals("originalBookWriter")) {
                    HashMap<String, String> values = (HashMap<String, String>) f.getType().getInformation("values");
                    values.clear();
                    for(User user : users) {
                        values.put(user.getId().toString(),user.getFirstName().concat(user.getLastName()));
                    }
                }

                if(f.getId().equals("plagiarismBookWriter")) {
                    HashMap<String, String> values = (HashMap<String, String>) f.getType().getInformation("values");
                    values.clear();
                    for(User user : users) {
                        values.put(user.getId().toString(),user.getFirstName().concat(user.getLastName()));
                    }
                }
            }
        }
    }
}
