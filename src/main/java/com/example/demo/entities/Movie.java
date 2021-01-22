package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name= "movies")
public class Movie {
    @Id
    private int id;
    private String title;
    private String originalTitle;
    private String posterUrl;
    private String releaseDate;
    @OneToMany
    private List<Cast> credits;
}
