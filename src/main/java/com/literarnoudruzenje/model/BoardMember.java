package com.literarnoudruzenje.model;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("BOARDMEMBER")
public class BoardMember extends User{
}
