package com.literarnoudruzenje.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilesPlagiarismDTO implements Serializable {
    String original;
    String plagiarism;

}
