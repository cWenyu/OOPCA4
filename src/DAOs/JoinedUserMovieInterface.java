/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import DTOs.JoinedUserMovie;
import DTOs.Movie;
import Exceptions.DaoException;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface JoinedUserMovieInterface {
    
     public JoinedUserMovie findMovieWatchedByUserName(String userName) throws DaoException;
}
