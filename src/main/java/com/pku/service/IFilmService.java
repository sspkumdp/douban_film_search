package com.pku.service;

import java.util.List;

import com.pku.bean.Film;

public interface IFilmService {
	Film getFilmById(String film_id);
	List<Film> getFilmUnLucene();
	void updateFilmLucene(String film_id);
}
