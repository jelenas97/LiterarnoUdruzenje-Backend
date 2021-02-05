package com.literarnoudruzenje.handlers;

import com.literarnoudruzenje.model.Genre;
import com.literarnoudruzenje.services.GenreService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class GetGenresHandler implements TaskListener {

    @Autowired
    GenreService genreService;


    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("Dobavlja zanrove");
        TaskFormData taskFormData = delegateTask.getExecution().getProcessEngineServices()
                .getFormService().getTaskFormData(delegateTask.getId());
        List<Genre> genres = genreService.getAllGenres();
        List<FormField> fields = taskFormData.getFormFields();

        if(fields != null) {
            for(FormField f: fields) {
                if(f.getId().equals("genres")) {
                    HashMap<String, String> values = (HashMap<String, String>) f.getType().getInformation("values");
                    values.clear();
                    for(Genre g : genres) {
                        values.put(g.getId().toString(),g.getName());
                    }
                }
            }
        }
    }
}
