/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

/**
 *
 * @author DELL
 */
public class MovieUserWatched {

    private int userId;
    private String userName;
    private int movieId;

    public MovieUserWatched() {

    }

    public MovieUserWatched(int userId, String userName, int movieId) {
        this.userId = userId;
        this.userName = userName;
        this.movieId = movieId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "MovieUserWatched{" + "userId=" + userId + ", userName=" + userName + ", movieId=" + movieId + '}';
    }
    
}
