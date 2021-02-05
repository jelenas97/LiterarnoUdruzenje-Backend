package com.literarnoudruzenje.model;


import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("LECTOR")
public class Lector extends User {
}
