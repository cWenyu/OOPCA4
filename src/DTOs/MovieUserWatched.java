/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.text.SimpleDateFormat ;

/**
 *
 * @author DELL
 */
public class MovieUserWatched {

    private int recordID;
    private String userName;
    private int movieId;
    private String timeStamp;

    transient SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public MovieUserWatched() {

    }

    public MovieUserWatched(int recordID, String userName, int movieId, String timeStamp) {
        this.recordID = recordID;
        this.userName = userName;
        this.movieId = movieId;
        this.timeStamp = timeStamp;
    }

    public MovieUserWatched(String userName, int movieId, String timeStamp) {
        this.userName = userName;
        this.movieId = movieId;
        this.timeStamp = timeStamp;
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = dateFormat.format(timeStamp);
    }

    @Override
    public String toString() {
        return "MovieUserWatched{" + "recordID=" + recordID + ", userName=" + userName
                + ", movieId=" + movieId + ", timeStamp=" + timeStamp + '}';
    }

}
