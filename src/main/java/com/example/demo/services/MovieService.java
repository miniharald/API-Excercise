package com.example.demo.services;

import com.example.demo.entities.Movie;
import com.example.demo.models.MovieResultItem;
import com.example.demo.models.MovieResults;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MovieService {
    private final RestTemplate restTemplate = new RestTemplate();

    public List<MovieResultItem> getAllMovies() {
        var movieResult = restTemplate.getForObject("https://api.themoviedb.org/3/movie/popular?api_key=7641e2c988f78099d675e3e5a90a9a56&language=sv", MovieResults.class);

        if(movieResult == null)  return new ArrayList<>();

        return movieResult.getResults();
    }

    public Movie getMovieById(long id) {
        Map<String, Object> pokeMap = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + id + "?api_key=7641e2c988f78099d675e3e5a90a9a56&language=sv", Map.class);
        if(pokeMap == null) return null;

        Movie movie = new Movie(
                (int) pokeMap.get("id"),
                (String) pokeMap.get("title"),
                (String) pokeMap.get("original_title"),
                "https://image.tmdb.org/t/p/original" + (String) pokeMap.get("poster_path"),
                (String) pokeMap.get("release_date")
        );
        return movie;
    }

}
