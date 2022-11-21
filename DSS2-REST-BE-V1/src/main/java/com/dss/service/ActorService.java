package com.dss.service;

import com.dss.entity.Actor;
import com.dss.model.ActorRequest;

import java.util.List;

public interface ActorService {
    List<Actor> getAllActors();

    String addActor(ActorRequest actor);

    String deleteActor(long actorId);

    String updateActor(ActorRequest actorRequest);

    String addMovieDetailsToTheActor(long actorId, long movieId);
}
