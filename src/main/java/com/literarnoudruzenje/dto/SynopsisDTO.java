package com.literarnoudruzenje.dto;

public class SynopsisDTO {

    String writer;
    String title;
    String synopsis;

    public SynopsisDTO(String writer, String title, String synopsis) {
        this.writer = writer;
        this.title = title;
        this.synopsis = synopsis;
    }

    public String getWriter() { return writer; }

    public void setWriter(String writer) { this.writer = writer; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getSynopsis() { return synopsis; }

    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
}
