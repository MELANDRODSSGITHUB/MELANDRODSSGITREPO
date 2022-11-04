package com.dss.service;

import com.dss.entity.Actor;
import com.dss.entity.Movie;
import com.dss.exception.ActorHaveMovieDetailsException;
import com.dss.exception.ActorNotFoundException;
import com.dss.exception.DuplicateActorException;
import com.dss.exception.MovieNotFoundException;
import com.dss.repository.ActorRepository;
import com.dss.repository.MovieRepository;
import com.dss.util.ResponseMsgConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    MovieRepository movieRepository;

    public ActorServiceImpl(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public String addActor(Actor actor) {
        Actor addedActor;
        Optional<Actor> actorOptional = actorRepository.findById(actor.getActorId());
        if (actorOptional.isPresent()) {
            throw new DuplicateActorException(ResponseMsgConstant.FAILED_TO_ADD_ACTOR_MSG);
        }
        addedActor = actorRepository.save(actor);

        return ResponseMsgConstant.SUCCESSFULLY_ADDED_ACTOR_MSG +
                " (Id: " + addedActor.getActorId() + "\t Name:" + addedActor.getFirstName()
                + " " + addedActor.getLastName() + ")";
    }

    @Override
    public String deleteActor(long actorId) {
        Optional<Actor> actorOptional = actorRepository.findById(actorId);
        if (!actorOptional.isPresent()) {
            throw new ActorNotFoundException(ResponseMsgConstant.FAILED_TO_DELETE_ACTOR_MSG);
        }
        Actor actorResult = actorOptional.get();
        if (!actorResult.getMovies().isEmpty()) {
            throw new ActorHaveMovieDetailsException(ResponseMsgConstant.FAILED_TO_DELETE_ACTOR_MSG);
        }
        actorRepository.deleteById(actorId);

        Actor deletedActor = actorOptional.get();
        return ResponseMsgConstant.SUCCESSFULLY_DELETED_ACTOR_MSG.concat(
                " (Id: " + deletedActor.getActorId() + "\t Name:" + deletedActor.getFirstName()
                        + " " + deletedActor.getLastName() + ")");
    }

    @Override
    public String updateActor(Actor actor) {
        Optional<Actor> actorOptional = actorRepository.findById(actor.getActorId());
        if (!actorOptional.isPresent()) {
            throw new ActorNotFoundException(ResponseMsgConstant.FAILED_TO_UPDATE_ACTOR_MSG);
        }
        actorRepository.save(actor);

        return ResponseMsgConstant.SUCCESSFULLY_UPDATED_ACTOR_MSG.concat(" (Id: " + actor.getActorId() + "\t Name:"
                + actor.getFirstName() + " " + actor.getLastName() + ")");
    }

    @Override
    public String addMovieDetailsToTheActor(long actorId, long movieId) {
        Optional<Movie> movieResult = movieRepository.findById(movieId);
        if (!movieResult.isPresent()) {
            throw new MovieNotFoundException("[Failed to add movie details to the actor]: ");
        }

        Optional<Actor> actorOptional = actorRepository.findById(actorId);
        if (!actorOptional.isPresent()) {
            throw new ActorNotFoundException(ResponseMsgConstant.FAILED_TO_ADD_MOVIE_DETAILS_TO_ACTOR_MSG);
        }

        Actor actor = actorOptional.get();
        List<Movie> movies = actor.getMovies();
        movies.add(movieResult.get());
        actor.setMovies(movies);
        actorRepository.save(actor);
        return ResponseMsgConstant.SUCCESSFULLY_ADDED_MOVIE_DETAILS_TO_ACTOR_MSG;
    }
}
