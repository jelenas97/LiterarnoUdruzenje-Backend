package com.literarnoudruzenje.handlers;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DecisionHandler implements TaskListener {

    private final RuntimeService runtimeService;

    @Override
    public void notify(DelegateTask delegateTask) {
        HashMap<String, Object> map  = (HashMap<String, Object>) runtimeService.getVariable(delegateTask.getProcessInstanceId(), "form-data");
        String finalDecision = (String) map.get("finalDecision");
        List<String> finalDecisions = (List<String>) delegateTask.getVariable("finalDecisions");
        finalDecisions.add(finalDecision);
        delegateTask.setVariable("finalDecisions", finalDecisions);
    }
}
