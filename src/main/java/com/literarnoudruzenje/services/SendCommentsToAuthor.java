package com.literarnoudruzenje.services;

import com.literarnoudruzenje.dto.CommentDto;
import com.literarnoudruzenje.dto.FormSubmissionDto;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendCommentsToAuthor implements JavaDelegate {

    private Expression betaReader;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormSubmissionDto> form = (List<FormSubmissionDto>) delegateExecution.getVariable( "betaReaderCommentForm");
        String comment = "";
        for (FormSubmissionDto formField : form) {
            if(formField.getFieldId().equals("beta_reader_comment"))
            {
                comment = formField.getFieldValue();
            }
        }

        List<CommentDto> comments = (List<CommentDto>) delegateExecution.getVariable("commentsFromBR");
        CommentDto commentDto = new CommentDto((String) this.betaReader.getValue(delegateExecution), comment);
        comments.add(commentDto);
        delegateExecution.setVariable("commentsFromBR", comments);
        delegateExecution.setVariable("anyComment", true);
    }
}
