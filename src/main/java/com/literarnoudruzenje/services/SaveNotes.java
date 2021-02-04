package com.literarnoudruzenje.services;

import com.literarnoudruzenje.dto.FormSubmissionDto;
import com.literarnoudruzenje.dto.NoteDto;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveNotes implements JavaDelegate {

    private Expression editor;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormSubmissionDto> form = (List<FormSubmissionDto>) delegateExecution.getVariable( "writeNotesForm");
        String note = "";
        for (FormSubmissionDto formField : form) {
            if(formField.getFieldId().equals("note"))
            {
                note = formField.getFieldValue();
            }
        }

        List<NoteDto> noteDtoList = (List<NoteDto>) delegateExecution.getVariable("notes");
        NoteDto noteDto = new NoteDto((String) editor.getValue(delegateExecution), note);
        noteDtoList.add(noteDto);
        delegateExecution.setVariable("notes", noteDtoList);
    }
}
