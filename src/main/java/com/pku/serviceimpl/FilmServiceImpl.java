package com.pku.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pku.bean.Film;
import com.pku.dao.IFilmDao;
import com.pku.service.IFilmService;

@Service
public class FilmServiceImpl implements IFilmService {
	@Autowired
	private IFilmDao filmDao;
	

	@Override
	public Film getFilmById(String film_id) {
		List<Film> list=filmDao.getFilmById(film_id);
		if(list==null ||list.size()==0)return null;
		return list.get(0);
	}
	
	@Override
	public List<Film> getFilmUnLucene() {
		// TODO Auto-generated method stub
		return filmDao.getFilmUnLucene();
	}

	@Override
	public void updateFilmLucene(String film_id) {
		// TODO Auto-generated method stub
		filmDao.updateFilmLucene(film_id);
	}
	
	
}
