package com.literarnoudruzenje.handlers;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

@Service
public class BookReviewEditorHandler implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {

    }
}
