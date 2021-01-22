package com.example.demo.services;

import com.example.demo.entities.Cast;
import com.example.demo.entities.Movie;
import com.example.demo.models.MovieResultItem;
import com.example.demo.models.MovieResults;
import com.example.demo.repositories.CastRepo;
import com.example.demo.repositories.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private MovieRepo movieRepo;
    @Autowired
    private CastRepo castRepo;

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();

        for(int i = 1; i < 750; i++) {
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

        Map<String, Object> movieMap = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + id + "?api_key=7641e2c988f78099d675e3e5a90a9a56&language=sv", Map.class);
        if(movieMap == null) return null;

        List<Cast> credits = new ArrayList<>();
        try{
            System.out.println("YEY");
            credits = getCastByMovieId(id);
        }
        catch (Exception e){
            System.out.println("NOOOO");
            credits = new ArrayList<>();
        }

        System.out.println("CREDITS TOSTRING " + credits.toString());

        for (Cast cast : credits) {
            if (cast != null) {
                System.out.println("CREDITS: " + cast.getName());
            }
        }


        Movie movie = new Movie(
                (int) movieMap.get("id"),
                (String) movieMap.get("title"),
                (String) movieMap.get("original_title"),
                "https://image.tmdb.org/t/p/original" + (String) movieMap.get("poster_path"),
                (String) movieMap.get("release_date"),
                credits
        );

        movieRepo.save(movie);

        return movie;
    }

    public List<Cast> getCastByMovieId(int id) {
        Optional<Movie> optional = movieRepo.findById(id);
        if(optional.isPresent()) {
            return optional.get().getCredits();
        }

        Map<String, Object> creditsMap = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + id + "/credits?api_key=7641e2c988f78099d675e3e5a90a9a56", Map.class);
        if(creditsMap == null) return null;

        List<Cast> credits = ((List<Map<String, Object>>) creditsMap.get("cast"))
                .stream()
                .map(c -> {
                    Optional<Cast> castOptional = castRepo.findById((int) c.get("id"));
                    if(castOptional.isPresent()) {
                        return castOptional.get();
                    }
                    Cast cast = new Cast(
                            (int) c.get("id"),
                            (String) c.get("name"),
                            (String) c.get("original_name"),
                            "https://image.tmdb.org/t/p/original" + (String) c.get("profile_path")
                    );
                    System.out.println("kfd: " + c.get("known_for_department"));
                    if(c.get("known_for_department").equals("Acting")) {
                        castRepo.save(cast);
                        return cast;
                    } else {
                        return null;
                    }
                })
                .filter(c ->  c != null )
                .collect(Collectors.toList());


        return credits;
    }
}
