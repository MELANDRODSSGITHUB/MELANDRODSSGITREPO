package com.dss.controller;

import com.dss.entity.Movie;
import com.dss.model.SearchMovieRequest;
import com.dss.model.UpdateMovieRequest;
import com.dss.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dss/api/movie")
public class MovieController {

    private static final String GET_ALL_MOVIE_URL = "/getAll";
    private static final String SEARCH_MOVIE_URL = "/search";
    private static final String ADD_MOVIE_URL = "/add";
    private static final String UPDATE_MOVIE_URL = "/update";
    private static final String UPDATE_MOVIE_ADD_ACTOR_URL = "/update/{actorId}/{movieId}";
    private static final String DELETE_MOVIE_URL = "/delete/{movieId}";

    @Autowired
    MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping(ADD_MOVIE_URL)
    public ResponseEntity<String> addMovie(@Valid @RequestBody Movie movie) {
        return new ResponseEntity<>(movieService.addMovie(movie), HttpStatus.CREATED);
    }

    @GetMapping(GET_ALL_MOVIE_URL)
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(movieService.getAllMovie());
    }

    @PutMapping(UPDATE_MOVIE_URL)
    public ResponseEntity<String> updateMovie(@Valid @RequestBody UpdateMovieRequest updateMovieRequest) {
        return new ResponseEntity<>(movieService.updateMovie(updateMovieRequest), HttpStatus.OK);
    }

    @DeleteMapping(DELETE_MOVIE_URL)
    public ResponseEntity<String> deleteMovie(@PathVariable(value = "movieId") long movieId) {
        return new ResponseEntity<>(movieService.deleteMovie(movieId), HttpStatus.OK);
    }

    @PutMapping(UPDATE_MOVIE_ADD_ACTOR_URL)
    public ResponseEntity<String> updateMovieAddActor(@PathVariable(value = "actorId") long actorId, @PathVariable long movieId) {
        return new ResponseEntity<>(movieService.addActorDetailsToTheMovie(actorId, movieId), HttpStatus.OK);
    }

    @GetMapping(SEARCH_MOVIE_URL)
    public ResponseEntity<List<Movie>> searchMovie(@Valid @RequestBody SearchMovieRequest searchMovieRequest) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(movieService.searchMovie(searchMovieRequest));
    }
}
