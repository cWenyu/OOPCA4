/** Feb 2019
 * MovieDaoInterface
 *
 * Declares the methods that all MovieDAO types must implement,
 * be they MySql User DAOs or Oracle User DAOs etc...
 *
 * Classes from the Business Layer (users of this DAO interface)
 * should use reference variables of this interface type to avoid
 * dependencies on the underlying concrete classes (e.g. MySqlUserDao).
 *
 * More sophistocated implementations will use a factory
 * method to instantiate the appropriate DAO concrete classes
 * by reading database configuration information from a
 * configuration file (that can be changed without altering source code)
 *
 * Interfaces are also useful when testing, as concrete classes
 * can be replaced by mock DAO objects.
 */
package DAOs;

import DTOs.Movie;
import Exceptions.DaoException;
import java.util.List;

public interface MovieDaoInterface {

    public List<Movie> testMovies() throws DaoException;

    public List<Movie> findMovieByTitle(String title) throws DaoException;

    public List<Movie> topTenMovies() throws DaoException;

    public Movie findMovieById(int id) throws DaoException;

    public List<Movie> findMovieByYear(int year) throws DaoException;

    public String updateMovie(int id, String target, String detail) throws DaoException;

    public Movie insertNewMovie() throws DaoException;

    public String deleteMovie(int id) throws DaoException;

    public List<Movie> findMovieByDirector(String directorName) throws DaoException;

    public Movie insertMovie(String title, String genre, String director, String runtime,
            String plot, String location, String poster, String rating, String format,
            int year, String starring, int copies, String barcode, String userRating) throws DaoException;
}
