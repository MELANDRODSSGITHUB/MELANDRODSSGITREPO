package com.dss.service;

import com.dss.entity.Actor;
import com.dss.entity.Movie;
import com.dss.exception.ActorNotFoundException;
import com.dss.exception.DeleteNewMovieException;
import com.dss.exception.DuplicateMovieException;
import com.dss.exception.MovieNotFoundException;
import com.dss.model.SearchMovieRequest;
import com.dss.model.UpdateMovieRequest;
import com.dss.repository.ActorRepository;
import com.dss.repository.MovieRepository;
import com.dss.util.ResponseMsgConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ActorRepository actorRepository;

    public MovieServiceImpl(MovieRepository movieRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    /**
     * Get all movies
     *
     * @return List of movies
     */
    @Override
    public List<Movie> getAllMovie() {
        return movieRepository.findAll();
    }

    /**
     * Add movie.
     *
     * @param movie - contains movie details that will be added.
     * @return - Movie object that contains details of added movie.
     */
    @Override
    public String addMovie(Movie movie) {
        Optional<Movie> movieResult = movieRepository.findById(movie.getMovieId());
        if (movieResult.isPresent()) {
            throw new DuplicateMovieException(ResponseMsgConstant.FAILED_TO_ADD_MOVIE_MSG);
        }
        Movie addedMovie = movieRepository.save(movie);

        return ResponseMsgConstant.SUCCESSFULLY_ADDED_MOVIE_MSG.concat(" (id: "
                + addedMovie.getMovieId() + "\t Title:" + addedMovie.getTitle() + ")");
    }

    /**
     * Delete movie.
     *
     * @param movieId - contains movie id value that will be deleted.
     * @return - Response message.
     */
    @Override
    public String deleteMovie(long movieId) {
        Optional<Movie> movieResult = movieRepository.findById(movieId);
        if (!movieResult.isPresent()) {
            throw new MovieNotFoundException(ResponseMsgConstant.FAILED_TO_DELETE_MOVIE_MSG);
        }

        Movie movie = movieResult.get();
        int currentYear = LocalDate.now().getYear();
        if (currentYear <= movie.getYearOfRelease() ? (currentYear - movie.getYearOfRelease() < 1) : false) {
            throw new DeleteNewMovieException(ResponseMsgConstant.FAILED_TO_DELETE_MOVIE_MSG);
        }

        movieRepository.deleteById(movieId);
        Movie deletedMovie = movieResult.get();
        return ResponseMsgConstant.SUCCESSFULLY_DELETED_MOVIE_MSG.concat(" (Id: " + deletedMovie.getMovieId()
                + "\t Title:" + deletedMovie.getTitle() + ")");
    }

    /**
     * Update Movie details.
     *
     * @param updateMovieRequest - contains movie details that will be updated.
     * @return - Response message.
     */
    @Override
    public String updateMovie(UpdateMovieRequest updateMovieRequest) {
        Optional<Movie> movieResult = movieRepository.findById(updateMovieRequest.getMovieId());
        if (!movieResult.isPresent()) {
            throw new MovieNotFoundException(ResponseMsgConstant.FAILED_TO_UPDATE_MOVIE_MSG);
        }
        Movie movieUpdate = movieResult.get();
        movieUpdate.setImage(updateMovieRequest.getImage());
        movieUpdate.setCost(updateMovieRequest.getCost());
        movieRepository.save(movieUpdate);

        return ResponseMsgConstant.SUCCESSFULLY_UPDATED_MOVIE_MSG.concat(" (Id: "
                + movieUpdate.getMovieId() + "Tile: " + movieUpdate.getTitle() + ")");
    }


    @Override
    public String addActorDetailsToTheMovie(long actorId, long movieId) {
        Optional<Actor> actorResult = actorRepository.findById(actorId);
        if (!actorResult.isPresent()) {
            throw new ActorNotFoundException(ResponseMsgConstant.FAILED_TO_ADD_ACTOR_DETAILS_TO_MOVIE_MSG);
        }
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (!movieOptional.isPresent()) {
            throw new MovieNotFoundException(ResponseMsgConstant.FAILED_TO_ADD_ACTOR_DETAILS_TO_MOVIE_MSG);
        }

        Movie movie = movieOptional.get();
        List<Actor> actors = movie.getActors();
        actors.add(actorResult.get());
        movie.setActors(actors);
        movieRepository.save(movie);

        return ResponseMsgConstant.SUCCESSFULLY_ADDED_ACTOR_DETAILS_TO_MOVIE_MSG;
    }

    /**
     * @param searchMovieRequest
     * @return
     */
    @Override
    public List<Movie> searchMovie(SearchMovieRequest searchMovieRequest) {
        Movie movie = Movie.builder().movieId(searchMovieRequest.getMovieId()).title(searchMovieRequest.getTitle()).build();
        List<Movie> searchResult = movieRepository.findMovieByMovieIdOrTitle(movie.getMovieId(),movie.getTitle());
        if (searchResult == null || searchResult.isEmpty()) {
            throw new MovieNotFoundException("Search result: ");
        }

        return searchResult;
    }
}
