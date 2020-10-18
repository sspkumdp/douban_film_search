package com.pku.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pku.bean.Actor;
import com.pku.dao.IActorDao;
import com.pku.service.IActorService;
@Service
public class ActorServiceImpl implements IActorService{
	@Autowired
	private IActorDao actorDao;
	@Override
	public Actor getActorById(String actor_id) {
		List<Actor> list=actorDao.getActorById(actor_id);
		if(list==null||list.size()==0)return null;
		return list.get(0);
	}

	@Override
	public List<Actor> getActorUnLucene() {
		return actorDao.getActorUnLucene();
	}

	@Override
	public List<Actor> getActorByFilm(String film_id) {
		return actorDao.getActorByFilm(film_id);
	}

	@Override
	public void updateActorLucene(String actor_id) {
		// TODO Auto-generated method stub
		actorDao.updateActorLucene(actor_id);
	}

}
