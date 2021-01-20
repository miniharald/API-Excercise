package com.example.demo.models;

import lombok.Data;

import java.util.List;

@Data
public class MovieResultItem {
    private boolean adult;
    private String backdrop_path;
    private List<Long> genre_ids;
    private long id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
    private String release_date;
    private String title;
    private boolean video;
    private double vote_average;
    private long vote_count;

//    private String title;
//    private String year;
//    private String imdbId;
//    private String type;
//    private String poster;
}
