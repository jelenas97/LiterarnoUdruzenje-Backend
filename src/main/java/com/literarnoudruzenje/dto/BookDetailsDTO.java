package com.literarnoudruzenje.dto;

import java.io.Serializable;
import java.util.List;

public class BookDetailsDTO  implements Serializable {

    String writer;
    String title;
    List<String> plagiarisms;

    public BookDetailsDTO(String writer, String title, List<String> plagiarisms) {
        this.writer = writer;
        this.title = title;
        this.plagiarisms = plagiarisms;
    }

    public String getWriter() { return writer; }

    public void setWriter(String writer) { this.writer = writer; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public List<String> getPlagiarisms() { return plagiarisms; }

    public void setPlagiarisms(List<String> plagiarisms) { this.plagiarisms = plagiarisms; }
}
