package com.pku.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.pku.bean.Actor;
import com.pku.bean.Film;
import com.pku.bean.SearchResult;
import com.pku.quartz.LuceneTask;

public class SearchService {

	private static String[] film_field = null;
	private static String[] actor_field = null;
	private static Path indexPath = null;
	private static IndexSearcher searcher = null;
	private static Analyzer analyzer = null;
	private final static int MAXQUERY=1000;
	
	public static SearchResult<Film> searchFilm(String key,int start,int limits){
		if(searcher==null) {
			try {
				indexPath=Paths.get(LuceneTask.targetDir);
				IndexReader reader = DirectoryReader.open(FSDirectory.open(indexPath));
				analyzer=new IKAnalyzer();
				searcher = new IndexSearcher(reader);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		if(film_field==null) {
			film_field=new String[] {"film_name","film_alias","film_summary","director","mainactors"};
		}

		Map<String , Float> weights = new HashMap<String, Float>();
		weights.put("film_name", 1.0f); //电影名
		weights.put("film_alias", 0.8f); //电影别名
		weights.put("film_summary",0.5f); //电影简介
		weights.put("director",0.4f);    //导演
		weights.put("mainactors",0.3f);  //主演
		MultiFieldQueryParser  parser = new MultiFieldQueryParser(film_field,analyzer,weights);
		try {
			Query q=parser.parse(key);
			TopDocs topDocs=searcher.search(q, MAXQUERY);
			ScoreDoc[] sdcs=topDocs.scoreDocs;
			SearchResult<Film> re=new SearchResult<Film>();
			re.setCount(sdcs.length);
			if(start>=sdcs.length)return re;
			List<Film> result=new ArrayList<Film>();
			for(int i=start;i<sdcs.length&& i<start+limits;i++) {
				ScoreDoc sd=sdcs[i];
				int docid=sd.doc;
				Document d=searcher.doc(docid);
				Film f=new Film();
				f.setFilm_name(d.get("film_name"));
				f.setDirector(d.get("director"));
				f.setFilm_alias(d.get("film_alis"));
				f.setFilm_summary(d.get("film_summary"));
				f.setFilm_date(d.get("film_date"));
				f.setFilm_type(d.get("film_type"));
				f.setFilm_time(d.get("film_time"));
				f.setFilm_id(d.get("film_id"));
				f.setLang(d.get("lang"));
				f.setArea(d.get("area"));
				f.setScore(d.get("score"));
				result.add(f);
			}
			re.setList(result);
			return re;
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new SearchResult<Film>();
	}
	
	public static SearchResult<Actor> searchActor(String key,int start,int limits){
		if(searcher==null) {
			try {
				indexPath=Paths.get(LuceneTask.targetDir);
				IndexReader reader = DirectoryReader.open(FSDirectory.open(indexPath));
				analyzer=new IKAnalyzer();
				searcher = new IndexSearcher(reader);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		if(actor_field==null) {
			actor_field=new String[] {"actor_name","more_name","more_foreign_name","main_works"};
		}

		Map<String , Float> weights = new HashMap<String, Float>();
		weights.put("actor_name", 1.0f);  //演员名字
		weights.put("more_name", 0.7f);   //其他名字
		weights.put("more_foreign_name",0.5f);  //外文名
		weights.put("main_works",0.5f);   //主要演出电影
		
		MultiFieldQueryParser  parser = new MultiFieldQueryParser(actor_field,analyzer,weights);
		try {
			Query q=parser.parse(key);
			TopDocs topDocs=searcher.search(q, MAXQUERY);
			ScoreDoc[] sdcs=topDocs.scoreDocs;
			SearchResult<Actor> re=new SearchResult<Actor>();
			re.setCount(sdcs.length);
			if(start>=sdcs.length)return re;
			List<Actor> result=new ArrayList<Actor>();
			for(int i=start;i<sdcs.length&& i<start+limits;i++) {
				ScoreDoc sd=sdcs[i];
				int docid=sd.doc;
				Document d=searcher.doc(docid);
				Actor f=new Actor();
				f.setActor_name(d.get("actor_name"));
				f.setActor_id(d.get("actor_id"));
				f.setMain_works(d.get("main_works"));
				f.setGender(d.get("gender"));
				f.setXingzuo(d.get("xingzuo"));
				f.setBirthday(d.get("birthday"));
				f.setBirtharea(d.get("birtharea"));
				f.setOccupation(d.get("occupation"));
				f.setMore_name(d.get("more_name"));
				f.setMore_foreign_name(d.get("more_foreign_name"));
				f.setImdb_id(d.get("imdb_id"));
				f.setWeb_url(d.get("web_url"));
				result.add(f);
			}
			re.setList(result);
			return re;
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new SearchResult<Actor>();
	}
}
