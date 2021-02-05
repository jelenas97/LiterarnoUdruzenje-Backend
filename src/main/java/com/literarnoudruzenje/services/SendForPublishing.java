package com.literarnoudruzenje.services;

import com.literarnoudruzenje.dto.FormSubmissionDto;
import com.literarnoudruzenje.model.Book;
import com.literarnoudruzenje.model.Genre;
import com.literarnoudruzenje.model.PublishedBook;
import com.literarnoudruzenje.model.Writer;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SendForPublishing implements JavaDelegate {

    @Autowired
    PublishedBookService publishedBookService;

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FileValue> values = (List<FileValue>) delegateExecution.getVariable( "files");

        PublishedBook publishedBook = new PublishedBook();
        List<FormSubmissionDto> bookPublishing = (List<FormSubmissionDto>) delegateExecution.getVariable("bookPublishing");

        for (FormSubmissionDto formField : bookPublishing) {
            if (formField.getFieldId().equals("title")) {
                publishedBook.setTitle(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("genres")) {
                publishedBook.setGenres(formField.getFieldValues().stream()
                        .map(x -> new Genre(Long.parseLong(x), null)).collect(Collectors.toList()));
            }
        }
        String processWriterEmail = (String) delegateExecution.getVariable("processWriterEmail");
        Writer user = (Writer) userService.findByEmail(processWriterEmail);
        publishedBook.setWriter(user);
        publishedBook.setPlagiarism(false);

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
                publishedBook.setWork(fileToDownload.getFilename());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new BpmnError("Saving files failed!");
            }
        }
        publishedBookService.save(publishedBook);


    }
}
