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
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author DELL
 */
public class MySqlMovieUserWatchedDao extends DAOs.MySqlMovieDao implements MovieUserWatchedInterface {

    public List<MovieUserWatched> findMovieWatchedByUserName(String userName) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MovieUserWatched> moviesWatched = new ArrayList<>();

        try {
            con = this.getConnection();

            String query = "SELECT * FROM movie_user_watched WHERE userName = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, userName);

            rs = ps.executeQuery();
            while (rs.next()) {
                int recordID = rs.getInt("reordID");
                String userName1 = rs.getString("userName");
                int movieID = rs.getInt("movieID");
                String timeStamp = rs.getString("timeStamp");
                MovieUserWatched movieWatched = new MovieUserWatched(recordID,userName1, movieID, timeStamp);
                moviesWatched.add(movieWatched);
            }
        } catch (SQLException e) {
            throw new DaoException("findMovieWatchedByUserName() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                throw new DaoException("findMovieWatchedByUserName() " + e.getMessage());
            }

            return moviesWatched;
        }
    }

    public MovieUserWatched watchMovie(String userName, int movieID) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        MovieUserWatched movieWatched = new MovieUserWatched();

        try {
            con = this.getConnection();

            String query = "INSERT INTO movie_user_watched "+
                    "(userName, movieID, timeStamp) VALUES (?,?,?)";
            ps = con.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, userName);
            ps.setInt(2, movieID);

            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String timeStamp = dateFormat.format(date);
            ps.setString(3, timeStamp);

            ps.executeUpdate();

            int id = 0;
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt(1);
            }
            
            movieWatched = new MovieUserWatched(id,userName, movieID,timeStamp);

        } catch (SQLException e) {
            throw new DaoException("watchMovie() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                throw new DaoException("watchMovie() " + e.getMessage());
            }

            return movieWatched;
        }
    }
}
