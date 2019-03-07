package DAOs;

import DTOs.Movie;
import Exception.DaoException;

import java.util.List;

public interface MovieDaoInterface {
	
	public List<Movie> findMovieByTitle(String title) throws DaoException;
	public Movie findMovieById(int id) throws DaoException;
	public List<Movie> findMoviesByYear(String year) throws DaoException;
	public void insertMovie() throws DaoException;
	public void updateMovie(int id, String bupdate, String aupdate) throws DaoException;
	public void deleteMovie(int id) throws DaoException;
	public List<Movie> findMovieByDirector(String director) throws DaoException;
	
}
