package com.pku.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pku.bean.Actor;
import com.pku.bean.Film;
import com.pku.dao.IActorDao;
import com.pku.dao.IFilmDao;
import com.pku.service.IActorService;
import com.pku.service.IFilmService;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;
@Component
public class LuceneTask {
	@Resource
	private IFilmDao filmDao;
	@Resource
	private IActorDao actorDao;
	public final static String targetDir="D:\\work\\lucene\\douban_film";
	public void run() {
		IndexWriterConfig config=new IndexWriterConfig(new IKAnalyzer());
		config.setOpenMode(OpenMode.CREATE_OR_APPEND);
		IndexWriter writer=null;
		try {
			Directory d=FSDirectory.open(Paths.get(targetDir));
			writer=new IndexWriter(d,config);
			List<Film> films=filmDao.getFilmUnLucene();
			System.out.println("index film...");
			for(Film film :films) {
				Document doc=new Document();
				doc.add(new TextField("film_id",check(film.getFilm_id()),Field.Store.YES));
				doc.add(new TextField("film_name",check(film.getFilm_name()),Field.Store.YES));
				doc.add(new TextField("director",check(film.getDirector()),Field.Store.YES));
				doc.add(new TextField("screenwriter",check(film.getScreenwriter()),Field.Store.YES));
				doc.add(new TextField("mainactors",check(film.getMainactors()),Field.Store.YES));
				doc.add(new TextField("film_type",check(film.getFilm_type()),Field.Store.YES));
				doc.add(new TextField("area",check(film.getArea()),Field.Store.YES));
				doc.add(new TextField("lang",check(film.getLang()),Field.Store.YES));
				doc.add(new TextField("film_date",check(film.getFilm_date()),Field.Store.YES));
				doc.add(new TextField("film_time",check(film.getFilm_time()),Field.Store.YES));
				doc.add(new TextField("film_alias",check(film.getFilm_alias()),Field.Store.YES));
				doc.add(new TextField("film_summary",check(film.getFilm_summary()),Field.Store.YES));
				doc.add(new TextField("score",check(film.getScore()),Field.Store.YES));
				writer.addDocument(doc);
				filmDao.updateFilmLucene(film.getFilm_id());
			}
			System.out.println("done.");
			System.out.println("index actor...");
			List<Actor> actors=actorDao.getActorUnLucene();
			for(Actor actor:actors) {
				Document doc=new Document();
				doc.add(new TextField("actor_id",check(actor.getActor_id()),Field.Store.YES));
				doc.add(new TextField("main_works",check(actor.getMain_works()),Field.Store.YES));
				doc.add(new TextField("actor_name",check(actor.getActor_name()),Field.Store.YES));
				doc.add(new TextField("gender",check(actor.getGender()),Field.Store.YES));
				doc.add(new TextField("xingzuo",check(actor.getXingzuo()),Field.Store.YES));
				doc.add(new TextField("birthday",check(actor.getBirthday()),Field.Store.YES));
				doc.add(new TextField("birtharea",check(actor.getBirtharea()),Field.Store.YES));
				doc.add(new TextField("occupation",check(actor.getOccupation()),Field.Store.YES));
				doc.add(new TextField("more_name",check(actor.getMore_name()),Field.Store.YES));
				doc.add(new TextField("more_foreign_name",check(actor.getMore_foreign_name()),Field.Store.YES));
				doc.add(new TextField("web_url",check(actor.getWeb_url()),Field.Store.YES));
				doc.add(new TextField("imdb_id",check(actor.getImdb_id()),Field.Store.YES));
				writer.addDocument(doc);
				actorDao.updateActorLucene(actor.getActor_id());
			}
			System.out.println("done.");
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(writer!=null) {
				try {
					writer.close();
				}catch(IOException e2) {
					e2.printStackTrace();
				}
			}
		}
		
		
	}
	String check(String s) {
		return s==null?"":s;
	}
}
