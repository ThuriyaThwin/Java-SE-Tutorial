package com.jdc.book.db.entity;

import com.jdc.book.db.dao.GeneratedKeyEntity;

public class Author implements GeneratedKeyEntity{
	private int id;        
	private String name;      
	private String biography; 
	private String photo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	} 
	
	@Override
	public String toString() {
		return name;
	}
}
