package com.example.demo.controllers;

import com.example.demo.entities.Cast;
import com.example.demo.entities.Movie;
import com.example.demo.models.MovieResultItem;
import com.example.demo.services.MovieService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public MappingJacksonValue getAllMovies() {
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("credits");
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("movieFilter", simpleBeanPropertyFilter);
        List<Movie> allMovies = movieService.getAllMovies();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(allMovies);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping("/{id}")
    public MappingJacksonValue getMovieById(@PathVariable int id) {
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAll();
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("movieFilter", simpleBeanPropertyFilter);
        Movie movie = movieService.getMovieById(id);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(movie);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping("/{id}/cast")
    public List<Cast> getCastMovieById(@PathVariable int id) {
        return movieService.getCastByMovieId(id);
    }
}
