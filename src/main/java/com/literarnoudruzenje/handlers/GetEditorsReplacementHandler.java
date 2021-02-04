package com.literarnoudruzenje.handlers;

import com.literarnoudruzenje.dto.NoteDto;
import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class GetEditorsReplacementHandler implements TaskListener {

    @Autowired
    private UserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormData = delegateTask.getExecution().getProcessEngineServices()
                .getFormService().getTaskFormData(delegateTask.getId());
        List<User> editors = userService.findAllByType("EDITOR");
        List<FormField> fields = taskFormData.getFormFields();
        List<User> editorsSelected = (List<User>) delegateTask.getExecution().getVariable("editorsList");
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

        if(fields != null) {
            for (FormField f : fields) {
                if (f.getId().equals("oneEditor")) {
                    HashMap<String, String> values = (HashMap<String, String>) f.getType().getInformation("values");
                    values.clear();
                    for (User editor : newEditors) {
                        values.put(editor.getId().toString(), editor.getFirstName() + " " + editor.getLastName());
                    }
                }
            }
        }

    }
}
