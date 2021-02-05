package com.literarnoudruzenje.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("BETAREADER")
public class BetaReader extends User{

    @ManyToMany
    @JoinTable(name = "reader_genres",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(name = "betareader_genres",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private List<Genre> betaGenres;


    @Column
    private Long penaltyPoints;

    public Long getPenaltyPoints() {
        return penaltyPoints;
    }

    public void setPenaltyPoints(Long penaltyPoints) {
        this.penaltyPoints = penaltyPoints;
    }

}
