package com.dss.service;

import com.dss.entity.Movie;
import com.dss.model.AddMovieRequest;
import com.dss.model.SearchMovieRequest;
import com.dss.model.UpdateMovieRequest;

import java.util.List;

public interface MovieService {
    List<Movie> getAllMovie();

    String addMovie(AddMovieRequest addMovieRequest);

    String deleteMovie(long movieId);

    String updateMovie(UpdateMovieRequest updateMovieRequest);

    String addActorDetailsToTheMovie(long actorId, long movieId);

    List<Movie> searchMovie(SearchMovieRequest searchMovieRequest);
}
