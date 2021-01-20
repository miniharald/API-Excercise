package com.example.demo.services;

import com.example.demo.entities.Movie;
import com.example.demo.models.MovieResultItem;
import com.example.demo.models.MovieResults;
import com.example.demo.repositories.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MovieService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private MovieRepo movieRepo;

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();

        for(int i = 1; i < 500; i++) {
            try {
                movies.add(getMovieById(i));
            }
            catch (Exception e) {
                System.out.println("id " + i + " was not found");
            }

        }

        return movies;
    }

    public Movie getMovieById(int id) {
        Optional<Movie> optional = movieRepo.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }

        Map<String, Object> pokeMap = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + id + "?api_key=7641e2c988f78099d675e3e5a90a9a56&language=sv", Map.class);
        if(pokeMap == null) return null;

        Movie movie = new Movie(
                (int) pokeMap.get("id"),
                (String) pokeMap.get("title"),
                (String) pokeMap.get("original_title"),
                "https://image.tmdb.org/t/p/original" + (String) pokeMap.get("poster_path"),
                (String) pokeMap.get("release_date")
        );

        movieRepo.save(movie);

        return movie;
    }

}
