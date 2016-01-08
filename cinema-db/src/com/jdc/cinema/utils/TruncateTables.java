package com.jdc.cinema.utils;

public class TruncateTables {
	
	public static void main(String[] args) {
//		ConnectionManager.clearTable("seat", "floor", "cinema", "show_time", "movie");
		ConnectionManager.clearTable("ticket", "movie_show_time", "cinema_movie");
	}

}
