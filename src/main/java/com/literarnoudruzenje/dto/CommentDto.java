package com.literarnoudruzenje.dto;

import java.io.Serializable;

public class CommentDto implements Serializable {
    String commenter;
    String comment;

    public CommentDto(String commenter, String comment) {
        this.commenter = commenter;
        this.comment = comment;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
