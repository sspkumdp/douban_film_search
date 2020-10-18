package com.pku.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pku.bean.Actor;
import com.pku.bean.Film;
import com.pku.bean.SearchResult;
import com.pku.service.IFilmService;
import com.pku.service.SearchService;

@Controller
@RequestMapping("/film")
public class FilmController {

	@Autowired
	private IFilmService filmService;

	/**
	 * @RequestMapping(value="/showFilm") public String showFilm(Model model) {
	 *                                    List<Film>
	 *                                    fs=filmService.getFilmUnLucene();
	 *                                    model.addAttribute("films",fs); return
	 *                                    "index"; }
	 **/

	@RequestMapping(value = "/searchFilm", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public SearchResult<Film> searchFilm(Model model, String search_str, int start, int limits) {
		SearchResult<Film> fs = SearchService.searchFilm(search_str, start, limits);
		return fs;
	}

	@RequestMapping(value = "/searchActor", produces = "text/html;charset=UTF-8")
	public @ResponseBody SearchResult<Actor> searchActor(Model model, String search_str, int start, int limits) {
		SearchResult<Actor> as = SearchService.searchActor(search_str, start, limits);
		return as;
	}

}
