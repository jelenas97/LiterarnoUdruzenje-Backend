package com.literarnoudruzenje.handlers;

import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.services.UserService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetEditorsHandler implements TaskListener {

    private final UserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormData = delegateTask.getExecution().getProcessEngineServices()
                .getFormService().getTaskFormData(delegateTask.getId());
        List<User> editors = userService.findAllByType("EDITOR");
        List<FormField> fields = taskFormData.getFormFields();

        if(fields != null) {
            for(FormField f: fields) {
                if(f.getId().equals("editors")) {
                    HashMap<String, String> values = (HashMap<String, String>) f.getType().getInformation("values");
                    values.clear();
                    for(User editor : editors) {
                        values.put(editor.getId().toString(),editor.getFirstName() + " " + editor.getLastName());
                    }
                }
            }
        }

    }
}
