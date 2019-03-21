/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import DTOs.MovieUserWatched;
import Exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class MySqlMovieUserWatchedDao extends DAOs.MySqlMovieDao implements MovieUserWatchedInterface {
    public List<MovieUserWatched> findMovieWatchedByUserName(String userName) throws DaoException{
       Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MovieUserWatched> movieWatched = new ArrayList<>();

       return movieWatched;
    }
    
}
