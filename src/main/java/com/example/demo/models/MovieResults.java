package com.example.demo.models;

import lombok.Data;

import java.util.List;

@Data
public class MovieResults {
    private List<MovieResultItem> results;
}
