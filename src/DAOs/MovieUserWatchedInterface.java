/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import DTOs.Movie;
import DTOs.MovieUserWatched;
import Exceptions.DaoException;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface MovieUserWatchedInterface {

    public MovieUserWatched findMovieWatchedByUserName(String userName) throws DaoException;

    public MovieUserWatched watchMovie(String userName, int movieID) throws DaoException;

    public List<Movie> recommandMovie(String userName) throws DaoException;
}
