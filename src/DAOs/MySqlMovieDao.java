/** Feb 2019
 *
 * Data Access Object (DAO) for movie table with MySQL-specific code
 * This 'concrete' class implements the 'MovieDaoInterface'.
 *
 * The DAO will contain the SQL query code to interact with the database,
 * so, the code here is specific to a particular database (e.g. MySQL or Oracle etc...)
 * No SQL queries will be used in the Business logic layer of code, thus, it
 * will be independent of the database specifics.
 *
 */
package DAOs;

import DTOs.Movie;
import Exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MySqlMovieDao extends Daos.MySqlDao implements MovieDaoInterface {

    @Override
    public List<Movie> testMovies() throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();

        try {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM movies LIMIT 5";
            ps = con.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
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

                Movie m = new Movie(id, title, genre, director, runtime,
                        plot, location, poster, rating, format, year,
                        starring, copies, barcode, userRating);
                movies.add(m);
            }
        } catch (SQLException e) {
            throw new DaoException("testMovies() " + e.getMessage());
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
                throw new DaoException("testMovies() " + e.getMessage());
            }
        }
        return movies;     // may be empty
    }

    @Override
    public List<Movie> findMovieByTitle(String movieTitle) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();
        try {
            con = this.getConnection();

            String query = "SELECT * FROM movies WHERE title = ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, movieTitle);

            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
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
                Movie m = new Movie(id, title, genre, director, runtime,
                        plot, location, poster, rating, format, year,
                        starring, copies, barcode, userRating);
                movies.add(m);
            }
        } catch (SQLException e) {
            throw new DaoException("findMovieByTitle()" + e.getMessage());
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
                throw new DaoException("findMovieByTitle() " + e.getMessage());
            }
        }
        return movies;     // u may be null 
    }

    @Override
    public List<Movie> topTenMovies() throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();

        try {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM movies ORDER BY user_rating DESC LIMIT 10";
            ps = con.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
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
                Movie m = new Movie(id, title, genre, director, runtime,
                        plot, location, poster, rating, format, year,
                        starring, copies, barcode, userRating);
                movies.add(m);
            }
        } catch (SQLException e) {
            throw new DaoException("topTenMovies() " + e.getMessage());
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
                throw new DaoException("topTenMovies() " + e.getMessage());
            }
        }
        return movies;     // may be empty
    }

    @Override
    public Movie findMovieById(int id) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie m = null;
        try {
            con = this.getConnection();

            String query = "SELECT * FROM movies WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            if (rs.next()) {
                int mid = rs.getInt("id");
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
                m = new Movie(mid, title, genre, director, runtime,
                        plot, location, poster, rating, format, year,
                        starring, copies, barcode, userRating);
            }

        } catch (SQLException e) {
            throw new DaoException("findMovieById()" + e.getMessage());
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
                throw new DaoException("findMovieById() " + e.getMessage());
            }
        }
        return m;
    }

    @Override
    public List<Movie> findMovieByYear(int year) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();
        try {
            con = this.getConnection();

            String query = "SELECT * FROM movies WHERE year = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, year);

            rs = ps.executeQuery();
            if (rs.next()) {
                int mid = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String director = rs.getString("director");
                String runtime = rs.getString("runtime");
                String plot = rs.getString("plot");
                String location = rs.getString("location");
                String poster = rs.getString("poster");
                String rating = rs.getString("rating");
                String format = rs.getString("format");
                int myear = rs.getInt("year");
                String starring = rs.getString("starring");
                int copies = rs.getInt("copies");
                String barcode = rs.getString("barcode");
                String userRating = rs.getString("user_rating");
                Movie m = new Movie(mid, title, genre, director, runtime,
                        plot, location, poster, rating, format, myear,
                        starring, copies, barcode, userRating);
                movies.add(m);
            }
        } catch (SQLException e) {
            throw new DaoException("findMovieByYear()" + e.getMessage());
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
                throw new DaoException("findMovieByYear() " + e.getMessage());
            }
        }
        return movies;     // u may be null 
    }

    @Override
    public String updateMovie(int id, String target, String detail) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String message;
        try {
            con = this.getConnection();

            String query1 = "SELECT " + target + " FROM movies WHERE id = ? ";
            ps = con.prepareStatement(query1);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            String str11 = "";
            while (rs.next()) {
                str11 = rs.getString(target);
            }

            String query = "UPDATE movies SET " + target + "= ? WHERE id = ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, detail);
            ps.setInt(2, id);
            ps.executeUpdate();

            message = "id " + id + "," + target + ": " + str11 + " has been updated to " + detail;

        } catch (SQLException e) {
            throw new DaoException("updateMovie()" + e.getMessage());
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
                throw new DaoException("updateMovie() " + e.getMessage());
            }
        }
        return message;
    }

    @Override
    public Movie insertNewMovie() throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String message;
        Movie m = insertM();
        try {
            con = this.getConnection();

            String query = "INSERT INTO movies "
                    + "(title,genre,director,runtime,plot,location,poster,rating,format,year,starring,copies,barcode,user_rating)"
                    + " value(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, m.getTitle());
            ps.setString(2, m.getGenre());
            ps.setString(3, m.getDirector());
            ps.setString(4, m.getRuntime());
            ps.setString(5, m.getPlot());
            ps.setString(6, m.getLocation());
            ps.setString(7, m.getPoster());
            ps.setString(8, m.getRating());
            ps.setString(9, m.getFormat());
            ps.setInt(10, m.getYear());
            ps.setString(11, m.getStarring());
            ps.setInt(12, m.getCopies());
            ps.setString(13, m.getBarcode());
            ps.setString(14, m.getUserRating());

            ps.executeUpdate();

            int id = 0;
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt(1);
            }
            message = "new movie " + m.getTitle() + " has been added successfully, the id is " + id;

        } catch (SQLException e) {
            throw new DaoException("insertNewMovie()" + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                throw new DaoException("insertNewMovie() " + e.getMessage());
            }
        }
        return m;
    }

    public Movie insertM() {

        boolean s = true;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the Movie Information");
        System.out.println("Title:(required)");
        String title = sc.nextLine();
        while (s) {
            if (title.isEmpty()) {
                System.out.println("Title:(requried)");
                title = sc.nextLine();
            } else {
                s = false;
                System.out.print("√");
                System.out.println();
            }
        }
        System.out.println(s);
        System.out.println("Genre:(genre,genre,genre...)(requried)");
        String genre = sc.nextLine();

        if (genre.isEmpty()) {
            System.out.println("Genre:(requried)");
            genre = sc.nextLine();
        } else {
            s = false;
            System.out.print("√");
            System.out.println();
        }

        System.out.println("Director:(requried)");
        String director = sc.nextLine();
        while (s) {
            if (director.isEmpty()) {
                System.out.println("Director:(requried)");
                director = sc.nextLine();
            } else {
                s = false;
                System.out.print("√");
                System.out.println();
            }
        }
        System.out.println("Runtime:(number only)(required)");
        String runtime = sc.nextLine();
        while (s) {
            if (runtime.isEmpty() || isNum(runtime)) {
                System.out.println("Runtime : (requried)");
                runtime = sc.nextLine();
            } else {
                s = false;
                System.out.print("√");
                System.out.println();
            }
        }
        runtime += " min";
        System.out.println("Plot:(No double quote)");
        String plot = sc.nextLine();
        System.out.println("Location");
        String location = sc.nextLine();
        System.out.println("Poster");
        String poster = sc.nextLine();
        System.out.println("Rating");
        String rating = sc.nextLine();
        System.out.println("Format");
        String format = sc.nextLine();
        System.out.println("Year(required)");
        String myear = sc.nextLine();
        int year = 0;
        while (s) {
            if (myear.isEmpty() || !isNum(myear)) {
                System.out.println("Year:(requried)");
                year = Integer.parseInt(sc.nextLine());
            } else {
                s = false;
                System.out.print("√");
                System.out.println();
            }
        }
        System.out.println("Starring");
        String starring = sc.nextLine();
        System.out.println("Copies(required,default 1)");
        String mcopies = sc.nextLine();
        int copy = 0;
        while (s) {
            if (!isNum(mcopies)) {
                System.out.println("Copies(required,default 1)");
                mcopies = sc.nextLine();
            } else {
                s = false;
            }
        }
        int copies = Integer.parseInt(mcopies);
        if (copies == 0) {
            copy = 1;
        } else {
            copy = copies;
        }

        System.out.println("Barcode");
        String barcode = sc.nextLine();
        System.out.println("Usering Rating");
        String user_rating = sc.nextLine();
        Movie m = new Movie(title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copy, barcode, user_rating);

        return m;

    }

    @Override
    public String deleteMovie(int id) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String message;
        try {
            con = this.getConnection();
            String query1 = "SELECT title FROM movies WHERE id = ? ";
            ps = con.prepareStatement(query1);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            String title = "";
            while (rs.next()) {
                title = rs.getString("title");
            }

            String query = "DELETE FROM movies WHERE id = ? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.execute();

            message = "id: " + id + ", title: " + title + " has been deleted";

        } catch (SQLException e) {
            throw new DaoException("deleteMovie()" + e.getMessage());
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
                throw new DaoException("deleteMovie() " + e.getMessage());
            }
        }
        return message;
    }

    public static boolean isNum(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public List<Movie> findMovieByDirector(String directorName) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();
        try {
            con = this.getConnection();

            String query = "SELECT * FROM movies WHERE director like ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + directorName + "%");

            rs = ps.executeQuery();
            if (rs.next()) {
                int mid = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String director = rs.getString("director");
                String runtime = rs.getString("runtime");
                String plot = rs.getString("plot");
                String location = rs.getString("location");
                String poster = rs.getString("poster");
                String rating = rs.getString("rating");
                String format = rs.getString("format");
                int myear = rs.getInt("year");
                String starring = rs.getString("starring");
                int copies = rs.getInt("copies");
                String barcode = rs.getString("barcode");
                String userRating = rs.getString("user_rating");
                Movie m = new Movie(mid, title, genre, director, runtime,
                        plot, location, poster, rating, format, myear,
                        starring, copies, barcode, userRating);
                movies.add(m);
            }
        } catch (SQLException e) {
            throw new DaoException("findMovieByDirector()" + e.getMessage());
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
                throw new DaoException("findMovieByDirector() " + e.getMessage());
            }
        }
        return movies;
    }

    public Movie insertMovie(String title, String genre, String director, String runtime,
            String plot, String location, String poster, String rating, String format,
            int year, String starring, int copies, String barcode, String userRating) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String message;
        Movie m = new Movie();
        try {
            con = this.getConnection();

            String query = "INSERT INTO movies "
                    + "(title,genre,director,runtime,plot,location,poster,rating,format,year,starring,copies,barcode,user_rating)"
                    + " value(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, title);
            ps.setString(2, genre);
            ps.setString(3, director);
            ps.setString(4, runtime);
            ps.setString(5, plot);
            ps.setString(6, location);
            ps.setString(7, poster);
            ps.setString(8, rating);
            ps.setString(9, format);
            ps.setInt(10, year);
            ps.setString(11, starring);
            ps.setInt(12, copies);
            ps.setString(13, barcode);
            ps.setString(14, userRating);

            ps.executeUpdate();

            int id = 0;
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt(1);
            }
            m = new Movie(id, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, userRating);

        } catch (SQLException e) {
            throw new DaoException("insertNewMovie()" + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                throw new DaoException("insertNewMovie() " + e.getMessage());
            }
        }
        return m;
    }
}
