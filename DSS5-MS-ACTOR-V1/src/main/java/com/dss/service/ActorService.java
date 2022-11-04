package com.dss.service;

import com.dss.entity.Actor;

import java.util.List;

public interface ActorService {
    List<Actor> getAllActors();

    String addActor(Actor actor);

    String deleteActor(long actorId);

    String updateActor(Actor actor);

    String addMovieDetailsToTheActor(long actorId, long movieId);
}
