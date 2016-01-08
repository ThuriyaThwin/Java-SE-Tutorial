package com.jdc.cinema.service.imp;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.entity.Cinema;
import com.jdc.cinema.entity.CinemaMovie;
import com.jdc.cinema.entity.Movie;
import com.jdc.cinema.entity.SeatType;
import com.jdc.cinema.service.ReportService;
import com.jdc.cinema.vh.ReportVO;

public class ReportServiceImp implements ReportService {

	private Dao<Movie> movieDao;
	private Dao<Cinema> cinemaDao;
	private Dao<CinemaMovie> cmDao;
	private Dao<SeatType> stDao;

	private Function<ResultSet, Integer> convDate = rs -> {
		try {
			Date maxDate = rs.getDate(1);
			return maxDate.toLocalDate().getYear();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	};

	private static ReportService SERVICE;
	
	public static ReportService getService() {
		
		if(SERVICE == null)
			SERVICE = new ReportServiceImp();
		
		return SERVICE;
	}
	
	private ReportServiceImp() {
		movieDao = Dao.getDao(Movie.class);
		cinemaDao = Dao.getDao(Cinema.class);
		cmDao = Dao.getDao(CinemaMovie.class);
		stDao = Dao.getDao(SeatType.class);
	}

	@Override
	public List<String> getYears() {
		int maxYear = this.getMaxYear();
		int minYear = this.getMinYear();
		return IntStream.rangeClosed(minYear, maxYear)
				.mapToObj(a -> String.valueOf(a))
				.collect(Collectors.toList());
	}

	private int getMinYear() {
		String sql = "select min(date_to) from cinema_movie";
		List<Object> param = new ArrayList<>();
		return cmDao.find(sql, param, convDate).get(0);
	}

	private int getMaxYear() {
		String sql = "select max(date_to) from cinema_movie";
		List<Object> param = new ArrayList<>();
		return cmDao.find(sql, param, convDate).get(0);
	}

	@Override
	public List<String> getMonths() {
		return Arrays.asList(Month.values()).stream().map(a -> a.toString()).collect(Collectors.toList());
	}

	@Override
	public List<Cinema> getAllCinema() {
		return cinemaDao.getAll();
	}

	@Override
	public List<ReportVO> find(Cinema cinema, Movie movie, LocalDate from, LocalDate dateTo) {

		List<ReportVO> list = new ArrayList<>();
		List<SeatType> seatTypes = stDao.getAll();
		for (SeatType type : seatTypes) {
			List<ReportVO> tmpList = this.getReports(cinema, movie, from, dateTo, type);
			if(tmpList != null)
				list.addAll(this.getReports(cinema, movie, from, dateTo, type));
		}
		return list;
	}

	@Override
	public List<Movie> getAllMovies(LocalDate from, LocalDate dateTo) {
		String where = "date_from <= ? and date_to >= ?";
		
		List<Object> param = Arrays.asList(Date.valueOf(dateTo), Date.valueOf(from));
		
		return findMovies(cmDao.getWhere(where, param));
	}

	@Override
	public List<Movie> getAllMovies(LocalDate target) {
		String where = "date_from <= ? and date_to >= ?";
		List<Object> param = Arrays.asList(Date.valueOf(target), Date.valueOf(target));
		List<CinemaMovie> cms = cmDao.getWhere(where, param);
		return findMovies(cms);
	}

	private List<Movie> findMovies(List<CinemaMovie> cms) {

		List<Object> movieIds = cms.stream().map(a -> a.getMovieId()).distinct().collect(Collectors.toList());
		StringBuffer sb = new StringBuffer();
		sb.append("id in (");

		for (int i = 0; i < movieIds.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}

			sb.append("?");
		}

		sb.append(")");
		
		if(movieIds.size() > 0) 
			return movieDao.getWhere(sb.toString(), movieIds);
		
		return new ArrayList<>();
	}

	private List<ReportVO> getReports(Cinema cinema, Movie movie, LocalDate from, LocalDate dateTo,
			SeatType type) {

		String select = "select c.name cinema, m.name movie, t.`date` showDate, st.time_from time_from, st.time_to time_to, t.status status, t.price price from ticket t ";
		String joinCinema = "left join cinema c on t.cinema_id = c.id ";
		String joinMovie = "left join movie m on t.movie_id = m.id ";
		String joinShowTime = "left join show_time st on t.show_time_id = st.id ";
		String joinSeat = "left join seat s on t.seat_id = s.id ";

		StringBuilder sql = new StringBuilder();
		List<Object> param = new ArrayList<>();

		sql.append(select).append(joinCinema).append(joinMovie).append(joinShowTime).append(joinSeat);

		sql.append("where s.seat_type_id = ? ");
		param.add(type.getId());

		if (cinema != null) {
			sql.append("and t.cinema_id = ? ");
			param.add(cinema.getId());
		}

		if (movie != null) {
			sql.append("and t.movie_id = ? ");
			param.add(movie.getId());
		}

		if (from != null) {
			sql.append("and t.date >= ? ");
			param.add(Date.valueOf(from));
		}

		if (dateTo != null) {
			sql.append("and t.date <= ? ");
			param.add(Date.valueOf(dateTo));
		}

		return this.getReports(sql.toString(), param, type);
	}

	private List<ReportVO> getReports(String sql, List<Object> param, SeatType seatType) {
		List<Map<String, Object>> list = stDao.find(sql, param, this::convert);
		list = list.stream().map(a -> {
			a.put("seatType", seatType.getType());
			return a;
		}).collect(Collectors.toList());
		return getReports(list);
	}
	
	private Map<String, Object> convert(ResultSet rs) {
		try {
			Map<String, Object> data = new HashMap<>();
			data.put("cinema", rs.getObject("cinema"));
			data.put("movie", rs.getObject("movie"));
			data.put("showDate", rs.getObject("showDate"));
			data.put("time_from", rs.getObject("time_from"));
			data.put("time_to", rs.getObject("time_to"));
			data.put("status", rs.getObject("status"));
			data.put("price", rs.getObject("price"));
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<ReportVO> getReports(List<Map<String, Object>> list) {
		Map<String, List<Map<String, Object>>> map = new TreeMap<>();
		list.forEach(a -> {
			String key = getKey(a);
			List<Map<String, Object>> subList = map.get(key);
			
			if(null == subList) {
				subList = new ArrayList<>();
				map.put(key, subList);
			}
			
			subList.add(a);
		});
		
		return getReports(map);
	}

	private List<ReportVO> getReports(Map<String, List<Map<String, Object>>> map) {
		
		Function<String, ReportVO> mapToReportObject = a -> {
			ReportVO vo = new ReportVO(a);
			List<Map<String, Object>> value = map.get(a);
			List<Map<String, Object>> soldOutList = value.stream()
					.filter(b -> Integer.valueOf(b.get("status").toString()) == 1)
					.collect(Collectors.toList());
			
			vo.setTotalSeats(value.size());
			vo.setSoldOut(soldOutList.size());
			vo.setTotal(soldOutList.stream()
					.mapToDouble(b -> Double.valueOf(b.get("price").toString())).sum());
			
			return vo;
		};
		
		if(null != map) {
			return map.keySet().stream()
					.map(mapToReportObject).collect(Collectors.toList());
		}
		
		return null;
	}

	private String getKey(Map<String, Object> a) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(a.get("cinema")).append("\t");
		sb.append(a.get("movie")).append("\t");
		sb.append(a.get("showDate")).append("\t");
		sb.append(a.get("time_from").toString().substring(0, 5))
			.append("-").append(a.get("time_to").toString().substring(0, 5)).append("\t");
		sb.append(a.get("seatType"));
		
		return sb.toString();
	}

}
