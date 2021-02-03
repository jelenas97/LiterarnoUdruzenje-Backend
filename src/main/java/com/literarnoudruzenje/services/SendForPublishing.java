package com.literarnoudruzenje.services;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Service
public class SendForPublishing implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FileValue> values = (List<FileValue>) delegateExecution.getVariable( "files");

        for (FileValue value : values) {
            FileValue fileToDownload = value;
            InputStream is = fileToDownload.getValue();
            File targetFile = new File(fileToDownload.getFilename());

            java.nio.file.Files.copy(
                    is,
                    targetFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            String rootPath = System.getProperty("user.dir");
            //LINUX
            String resourceFile = rootPath + "/books/" + fileToDownload.getFilename();
            //WINDOWS String resourceFile = rootPath + "\\books\\" + fileToDownload.getFilename();

            byte[] bytes = FileUtils.readFileToByteArray(targetFile);


            IOUtils.closeQuietly(is);
            try {
                FileOutputStream outputStream = new FileOutputStream(resourceFile);
                outputStream.write(bytes);

                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new BpmnError("Saving files failed!");
            }
        }


    }
}
