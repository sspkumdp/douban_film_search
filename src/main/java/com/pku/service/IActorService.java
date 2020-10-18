package com.pku.service;

import java.util.List;

import com.pku.bean.Actor;

public interface IActorService {
	Actor getActorById(String actor_id);
	List<Actor> getActorUnLucene();
	List<Actor> getActorByFilm(String film_id);
	void updateActorLucene(String actor_id);
}
