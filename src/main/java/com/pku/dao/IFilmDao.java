package com.pku.dao;

import java.util.List;

import com.pku.bean.Film;

public interface IFilmDao {
	List<Film> getFilmById(String film_id);
	List<Film> getFilmUnLucene();
	void updateFilmLucene(String film_id);
}
