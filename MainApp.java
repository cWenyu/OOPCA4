/** OOP Feb 2019
 * This App demonstrates the use of a Data Access Object (DAO)
 * to separate Business logic from Database specific logic.
 * It uses DAOs, Data Transfer Objects (DTOs), and
 * a DaoInterface to define a contract between Business Objects
 * and DAOs.
 *
 * "Use a Data Access Object (DAO) to abstract and encapsulate all
 * access to the data source. The DAO manages the connection with
 * the data source to obtain and store data" Ref: oracle.com
 *
 * Here we use one DAO per database table.
 *
 * Use the SQL script included with this project to create the
 * required MySQL user_database and user table
 */
package BusinessObjects;

import DAOs.MovieDaoInterface;
import DAOs.MySqlMovieDao;
import DTOs.User;
import DTOs.Movie;
import Daos.MySqlUserDao;
import Daos.UserDaoInterface;
import Exceptions.DaoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner k = new Scanner(System.in);
        System.out.println("please enter command: ");
        System.out.println("If you want to exit type 'Q' ");
        String command = null;
        while (k.hasNextLine()) {
            command = k.nextLine();
            if (command.contains(" ")) {
                String[] lineWords = command.split(" ");
                String subCommand = lineWords[0].toUpperCase();
                String title = "";
                String updateTitle = "";
                int idOrYear = Integer.parseInt(lineWords[1]);
                int j = 2;
                for (int i = 1; i < lineWords.length; i++) {
                    title += lineWords[i] + " ";
                    if (j < lineWords.length) {
                        updateTitle += lineWords[j] + " ";
                        j++;
                    }

                }
                switch (subCommand) {
                    case "FINDMOVIEBYTITLE":
                        findMovieByTitle(title);
                        break;

                    case "DELETEMOVIE":
                        deleteMovie(idOrYear);
                        break;

                    case "UPDATEMOVIETITLE":
                        updateMovieTitle(idOrYear, updateTitle);
                        break;

                    case "FINDMOVIEBYID":
                        findMovieById(idOrYear);
                        break;

                    case "FINDMOVIEBYYEAR":
                        findMovieByYear(idOrYear);
                        break;
                }
            } else if (command.equals("Q")) {
                break;
            } else {
                switch (command) {
                    case "TESTMOVIES":
                        testMovies();
                        break;

                    case "TOPTENMOVIES":
                        topTenMovies();
                        break;

                    case "INSERTNEWMOVIE":
                        insertNewMovie();
                        break;

                }

            }
        }
    }

    public static void getUsers() {
        UserDaoInterface IUserDao = new MySqlUserDao();
        try {
            List<User> users = IUserDao.findAllUsers();

            if (users.isEmpty()) {
                System.out.println("There are no Users");
            }

            for (User user : users) {
                System.out.println("User: " + user.toString());
            }

            // test dao - with good username and password
            User user = IUserDao.findUserByUsernamePassword("smithj", "password");
            if (user != null) {
                System.out.println("User found: " + user);
            } else {
                System.out.println("Username with that password not found");
            }

            // test dao - with bad username
            user = IUserDao.findUserByUsernamePassword("madmax", "thunderdome");
            if (user != null) {
                System.out.println("User found: " + user);
            } else {
                System.out.println("Username with that password not found");
            }

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static String testMovies() {
        MovieDaoInterface iMovieDao = new MySqlMovieDao();
        String jsonString = "{"
                + "\"movies\":"
                + "[ ";
        try {
            List<Movie> movies = iMovieDao.testMovies();

            if (movies.isEmpty()) {
                System.out.println("There are no movies");
            }

            int i = 1;
            for (Movie movie : movies) {

                jsonString += movie.toJson();

                if (i < movies.size()) {
                    jsonString += ",";
                    i++;
                } else {
                    jsonString += " ";
                }

            }
            jsonString += "]"
                    + "}";

            System.out.println(jsonString);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return jsonString;

    }

    public static void findMovieByTitle(String title) {
        MovieDaoInterface iMovieDao = new MySqlMovieDao();

        try {
            List<Movie> movies = iMovieDao.findMovieByTitle(title);
            if (movies.isEmpty()) {
                System.out.println("There is no moive you searched, please check again.");
            } else {
                String jsonString = "{"
                        + "\"movies\":"
                        + "[ ";
                int i = 1;
                for (Movie movie : movies) {
                    jsonString += movie.toJson();

                    if (i < movies.size()) {
                        jsonString += ",";
                        i++;
                    } else {
                        jsonString += " ";
                    }

                }
                jsonString += "]"
                        + "}";
                System.out.println(jsonString);
            }

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void topTenMovies() {
        MovieDaoInterface iMovieDao = new MySqlMovieDao();

        try {
            List<Movie> movies = iMovieDao.topTenMovies();

            if (movies.isEmpty()) {
                System.out.println("There are no movies");
            } else {
                String jsonString = "{"
                        + "\"movies\":"
                        + "[ ";
                int i = 1;
                for (Movie movie : movies) {

                    jsonString += movie.toJson();

                    if (i < movies.size()) {
                        jsonString += ",";
                        i++;
                    } else {
                        jsonString += " ";
                    }

                }
                jsonString += "]"
                        + "}";

                System.out.println(jsonString);
            }

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void updateMovieTitle(int id, String title) {
        MovieDaoInterface iMovieDao = new MySqlMovieDao();
        try {
            iMovieDao.updateMovieTitle(id, title);

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMovie(int id) {
        MovieDaoInterface iMovieDao = new MySqlMovieDao();
        try {
            iMovieDao.deleteMovie(id);

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void insertNewMovie() {
        MovieDaoInterface iMovieDao = new MySqlMovieDao();
        try {
            iMovieDao.insertNewMovie();

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void findMovieById(int id) {
        MovieDaoInterface iMovieDao = new MySqlMovieDao();

        try {
            Movie m = iMovieDao.findMovieById(id);
            if (m.equals(null)) {
                System.out.println("There is no moive you searched, please check again.");
            } else {
                System.out.println(m.toJson());
            }

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void findMovieByYear(int year) {
        MovieDaoInterface iMovieDao = new MySqlMovieDao();

        try {
            List<Movie> movies = iMovieDao.findMovieByYear(year);
            if (movies.isEmpty()) {
                System.out.println("There is no moive you searched, please check again.");
            } else {
                String jsonString = "{"
                        + "\"movies\":"
                        + "[ ";
                int i = 1;
                for (Movie movie : movies) {
                    jsonString += movie.toJson();

                    if (i < movies.size()) {
                        jsonString += ",";
                        i++;
                    } else {
                        jsonString += " ";
                    }

                }
                jsonString += "]"
                        + "}";
                System.out.println(jsonString);
            }

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

}
