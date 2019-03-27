/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author DELL
 */
public class MovieUserWatched {

    private int recordID;
    private String userName;
    private int movieId;
    private String timeStamp;
    private List<Movie> movie;
    private Movie m;

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public MovieUserWatched() {

    }

    public MovieUserWatched(int recordID, String userName, int movieId, String timeStamp) {
        this.recordID = recordID;
        this.userName = userName;
        this.movieId = movieId;
        this.timeStamp = timeStamp;
    }

    public MovieUserWatched(String userName, String timeStamp, int movieId, List<Movie> movie) {
        this.userName = userName;
        this.movieId = movieId;
        this.timeStamp = timeStamp;
        this.movie = movie;
    }

    public MovieUserWatched(String userName, String timeStamp, int movieId, Movie m) {
        this.userName = userName;
        this.movieId = movieId;
        this.timeStamp = timeStamp;
        this.m = m;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = dateFormat.format(timeStamp);
    }

    public List<Movie> getMovie() {
        return movie;
    }

    public Movie getM() {
        return m;
    }

    @Override
    public String toString() {
        return "MovieUserWatched{" + "recordID=" + recordID + ", userName=" + userName
                + ", movieId=" + movieId + ", timeStamp=" + timeStamp
                + ", movie=" + movie.toString() + ", dateFormat=" + dateFormat + '}';
    }

}
