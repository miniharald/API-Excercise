package com.example.demo.controllers;

import com.example.demo.entities.Cast;
import com.example.demo.entities.Movie;
import com.example.demo.services.MovieService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

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
    public List<Cast> getMovieCastById(@PathVariable int id) {
        return movieService.getCastByMovieId(id);
    }

    @PostMapping("/new")
    public String addMovieToDb(@RequestBody Movie movie) {
        return movieService.createMovie(movie);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMovieFromDb(@PathVariable int id) {
        return movieService.deleteMovie(id);
    }

    @PutMapping("update/{id}")
    public String updateMovieInDb(@RequestBody Movie movie, @PathVariable int id) {
        return movieService.updateMovie(movie, id);
    }
}
