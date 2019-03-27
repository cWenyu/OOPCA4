/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import DTOs.Movie;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public class MySqlMovieUserWatchedDao extends DAOs.MySqlMovieDao implements MovieUserWatchedInterface {

    public MovieUserWatched findMovieWatchedByUserName(String userName) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        MovieUserWatched moviesWatched = new MovieUserWatched();
        List<Movie> movies = new ArrayList<>();
        try {
            con = this.getConnection();

            String query = "SELECT * FROM movie_user_watched,movies "
                    + "WHERE movie_user_watched.movieID = movies.id and movie_user_watched.userName = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, userName);

            rs = ps.executeQuery();
            while (rs.next()) {
                String userName1 = rs.getString("userName");
                int movieID = rs.getInt("movieID");
                String timeStamp = rs.getString("timeStamp");

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
                moviesWatched = new MovieUserWatched(userName1, timeStamp, movieID, movies);

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
        Movie m = new Movie();
        try {
            con = this.getConnection();

            String query = "INSERT INTO movie_user_watched "
                    + "(userName, movieID, timeStamp) VALUES (?,?,?)";
            ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
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

            String query1 = "SELECT * FROM movies WHERE id = ? ";
            ps = con.prepareStatement(query1);
            ps.setInt(1, movieID);
            rs = ps.executeQuery();
            while (rs.next()) {
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

                m = new Movie(movieID, title, genre, director, runtime,
                        plot, location, poster, rating, format, year,
                        starring, copies, barcode, userRating);
            }

            movieWatched = new MovieUserWatched(userName, timeStamp, movieID, m);

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

    public List<Movie> recommandMovie(String userName) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Movie> movies = new ArrayList<>();
        List<Movie> movies1 = new ArrayList<>();
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
            //fetch movie directors
            List<String> directors = new ArrayList<>();
            for (Movie m1 : movies) {
                if (m1.getDirector().contains(",")) {
                    String[] direc = m1.getDirector().split(",");
                    for (String s : direc) {
                        directors.add(s);
                    }
                } else {
                    directors.add(m1.getDirector());
                }
            }

            //get frequence of each director
            Map<String, Integer> mapD = new HashMap<>();
            for (String d : directors) {
                if (mapD.containsKey(d)) {
                    mapD.put(d, mapD.get(d).intValue() + 1);
                } else {
                    mapD.put(d, new Integer(1));
                }
            }
            //get max fre of director
            String maxDirec = null;
            int max = 0;
            Iterator<String> iter = mapD.keySet().iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                Integer fre = mapD.get(key);
                if (max > fre) {
                    max = max;
                } else {
                    max = fre;
                    maxDirec = key;
                }
            }

            //fetch movie genre
            List<String> genres = new ArrayList<>();
            for (Movie m1 : movies) {
                if (m1.getGenre().contains(",")) {
                    String[] genre = m1.getGenre().split(",");
                    for (String s : genre) {
                        genres.add(s);
                    }
                } else {
                    genres.add(m1.getGenre());
                }
            }

            //get frequence of each genre
            Map<String, Integer> mapG = new HashMap<>();
            for (String g : genres) {
                if (mapG.containsKey(g)) {
                    mapG.put(g, mapG.get(g).intValue() + 1);
                } else {
                    mapG.put(g, new Integer(1));
                }
            }
            //get max fre of genre
            String maxGenre = null;
            int maxG = 0;
            Iterator<String> iterG = mapG.keySet().iterator();
            while (iterG.hasNext()) {
                String key = iterG.next();
                Integer fre = mapG.get(key);
                if (maxG > fre) {
                    maxG = maxG;
                } else {
                    maxG = fre;
                    maxGenre = key;
                }
            }

            //fetch movie actor
            List<String> actors = new ArrayList<>();
            for (Movie m1 : movies) {
                if (m1.getStarring().contains(",")) {
                    String[] act = m1.getStarring().split(",");
                    for (String s : act) {
                        actors.add(s);
                    }
                } else {
                    actors.add(m1.getStarring());
                }
            }

            //get frequence of each actor
            Map<String, Integer> mapA = new HashMap<>();
            for (String a : actors) {
                if (mapA.containsKey(a)) {
                    mapA.put(a, mapA.get(a).intValue() + 1);
                } else {
                    mapA.put(a, new Integer(1));
                }
            }
            //get max fre of actor
            String maxActor = null;
            int maxA = 0;
            Iterator<String> iterA = mapA.keySet().iterator();
            while (iterA.hasNext()) {
                String key = iterA.next();
                Integer fre = mapA.get(key);
                if (maxA > fre) {
                    maxA = maxA;
                } else {
                    maxA = fre;
                    maxActor = key;
                }
            }

//            System.out.println(maxDirec + "  fre : " + max);
//            System.out.println(maxGenre + "  fre : " + maxG);
//            System.out.println(maxActor + "  fre : " + maxA);
            //use maxDirec,maxGenre,maxActor get movies
            String query1 = "SELECT * FROM movies WHERE director like ? OR "
                    + "genre like ? OR starring like ? ORDER BY RAND() LIMIT 5";
            ps = con.prepareStatement(query1);
            ps.setString(1, "%" + maxDirec + "%");
            ps.setString(2, "%" + maxGenre + "%");
            ps.setString(3, "%" + maxActor + "%");

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
                movies1.add(m);
            }

        } catch (SQLException e) {
            throw new DaoException("recommandMovie() " + e.getMessage());
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
                throw new DaoException("recommandMovie() " + e.getMessage());
            }
        }
        return movies1;
    }
}
