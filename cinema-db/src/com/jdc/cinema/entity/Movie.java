package com.jdc.cinema.entity;

import com.jdc.cinema.dao.Column;
import com.jdc.cinema.dao.Entity;
import com.jdc.cinema.dao.ID;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.dao.Param;

public class Movie implements Entity{

	private static final long serialVersionUID = 1L;
	
	@ID(generated=true)
	private long id;
	private String name;
	private String actors;
	private String director;
	private String description;
	@Column("movie_type_id")
	private long movieTypeId;

	@Override
	public Param getInsertParam() {
		return Param.getInstance("id", id)
			.param("name", name)
			.param("actors", actors)
			.param("director", director)
			.param("description", description)
			.param("movie_type_id", movieTypeId);
	}

	@Override
	public IdParam getIdParam() {
		return IdParam.getInstance("id", id);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getMovieTypeId() {
		return movieTypeId;
	}

	public void setMovieTypeId(long movieTypeId) {
		this.movieTypeId = movieTypeId;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
