package com.literarnoudruzenje.camunda.config;

import com.literarnoudruzenje.camunda.type.EnumMultiSelectType;
import com.literarnoudruzenje.camunda.type.FileFormFieldType;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.spring.boot.starter.configuration.CamundaProcessEngineConfiguration;
import org.springframework.stereotype.Component;

@Component
public class CustomTypeConfig implements CamundaProcessEngineConfiguration {

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        processEngineConfiguration.getCustomFormTypes().add(new EnumMultiSelectType("multiSelect"));
        processEngineConfiguration.getCustomFormTypes().add(new FileFormFieldType());

    }
}
