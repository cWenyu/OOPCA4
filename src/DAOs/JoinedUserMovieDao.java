/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import DTOs.JoinedUserMovie;
import DTOs.Movie;
import DTOs.MovieUserWatched;
import Exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class JoinedUserMovieDao extends Daos.MySqlDao implements JoinedUserMovieInterface {

    public JoinedUserMovie findMovieWatchedByUserName(String userName) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        MovieUserWatched moviesWatched = new MovieUserWatched();
        List<Movie> movies = new ArrayList<>();
        JoinedUserMovie jum = new JoinedUserMovie();

        try {
            con = this.getConnection();
            String query = "SELECT * FROM movie_user_watched,movies "
                    + "WHERE movie_user_watched.movieID = movies.id and movie_user_watched.userName = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, userName);

            rs = ps.executeQuery();
            while (rs.next()) {
                int movieID = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String director = rs.getString("director");
                String runtime = rs.getString("runtime");
                String plot = rs.getString("plot");
                String location = rs.getString("location");
                String poster = rs.getString("poster");
                String rating = rs.getString("rating");
                String format = rs.getString("format");
                int year = rs.getInt("year");
                String starring = rs.getString("starring");
                int copies = rs.getInt("copies");
                String barcode = rs.getString("barcode");
                String userRating = rs.getString("user_rating");

                Movie m = new Movie(movieID, title, genre, director, runtime,
                        plot, location, poster, rating, format, year,
                        starring, copies, barcode, userRating);
                movies.add(m);
            }
            jum = new JoinedUserMovie(userName, movies);
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

            return jum;
        }
    }
}
