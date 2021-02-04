package com.literarnoudruzenje.dto;

public class SubmissionDetailsDTO {

    String chiefEditor;
    String title;

    public SubmissionDetailsDTO(String chiefEditor, String title) {
        this.chiefEditor = chiefEditor;
        this.title = title;
    }

    public String getChiefEditor() { return chiefEditor; }

    public void setChiefEditor(String chiefEditor) { this.chiefEditor = chiefEditor; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
}
