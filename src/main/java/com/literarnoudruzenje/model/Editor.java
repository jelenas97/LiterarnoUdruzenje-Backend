package com.literarnoudruzenje.model;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("EDITOR")
public class Editor extends User {
}
