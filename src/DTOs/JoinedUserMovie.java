/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.util.List;

/**
 *
 * @author DELL
 */
public class JoinedUserMovie {

    private String userName;
    private List<Movie> movies;
    private Movie m;

    public JoinedUserMovie(){}
    
    public JoinedUserMovie(String userName, List<Movie> movies) {
        this.userName = userName;
        this.movies = movies;
    }

    public JoinedUserMovie(String userName, Movie m) {
        this.userName = userName;
        this.m = m;
    }

    public String getUserName() {
        return userName;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public Movie getM() {
        return m;
    }
    
}
