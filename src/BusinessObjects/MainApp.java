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
import DAOs.MovieUserWatchedInterface;
import DAOs.MySqlMovieDao;
import DAOs.MySqlMovieUserWatchedDao;
import DTOs.User;
import DTOs.Movie;
import DTOs.MovieUserWatched;
import Daos.MySqlUserDao;
import Daos.UserDaoInterface;
import Exceptions.DaoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    static MovieDaoInterface iMovieDao = new MySqlMovieDao();

    public static void main(String[] args) {
        Scanner k = new Scanner(System.in);
        System.out.println("Hello");
        System.out.println("If you want to exit type 'Q' ");

        //command line 
        String message = "please enter command:";
        System.out.print(message);
        String command;

        labelB:
        while (k.hasNextLine()) {
            command = k.nextLine();

            String[] details = splitCommand(command);
            String subCommand = details[0].toUpperCase();

            labelA:
            switch (subCommand) {
                case "FINDMOVIEBYTITLE":
                    findMovieByTitle(details[1]);
                    break;

                case "DELETEMOVIE":
                    deleteMovie(Integer.parseInt(details[1]));
                    break;

                case "UPDATEMOVIE":
                    updateMovie(Integer.parseInt(details[1]), details[2], details[3]);
                    break;

                case "FINDMOVIEBYID":
                    findMovieById(Integer.parseInt(details[1]));
                    break;

                case "FINDMOVIEBYYEAR":
                    findMovieByYear(Integer.parseInt(details[1]));
                    break;

                case "FINDMOVIEBYDIRECTOR":
                    findMovieByDirector(details[1]);
                    break;

                case "TESTMOVIES":
                    testMovies();
                    break;

                case "TOPTENMOVIES":
                    topTenMovies();
                    break;

                case "INSERTNEWMOVIE":
                    insertNewMovie();
                    break;

                case "INSERTMOVIE":
                    insertMovie(details[1], details[2], details[3], details[4], details[5], details[6], details[7],
                            details[8], details[9], Integer.parseInt(details[10]), details[11], Integer.parseInt(details[12]), details[13], details[14]);
                    System.out.println("insert");
                    break;
                case "FINDMOVIEWATCHEDBYUSERNAME":
                    findMovieWatchedByUserName(details[1]);
                    break;
                case "MOVIEWATCH":
                    movieWatch(details[1], Integer.parseInt(details[2]));
                    break;
                case "RECOMMANDMOVIE":
                    recommandMovie(details[1]);
                    break;
                default:
                    message = "";
                    break labelB;
            }
            System.out.print(message);
        }
    }

//    public static void getUsers() {
//        UserDaoInterface IUserDao = new MySqlUserDao();
//        try {
//            List<User> users = IUserDao.findAllUsers();
//
//            if (users.isEmpty()) {
//                System.out.println("There are no Users");
//            }
//
//            for (User user : users) {
//                System.out.println("User: " + user.toString());
//            }
//
//            // test dao - with good username and password
//            User user = IUserDao.findUserByUsernamePassword("smithj", "password");
//            if (user != null) {
//                System.out.println("User found: " + user);
//            } else {
//                System.out.println("Username with that password not found");
//            }
//
//            // test dao - with bad username
//            user = IUserDao.findUserByUsernamePassword("madmax", "thunderdome");
//            if (user != null) {
//                System.out.println("User found: " + user);
//            } else {
//                System.out.println("Username with that password not found");
//            }
//
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
//    }
    public static String[] splitCommand(String command) {
        String[] details = null;

        if (command.contains(" ")) {
            String[] lineWords = command.split(" ");
            String subCommand = lineWords[0].toUpperCase();

            switch (subCommand) {
                case "DELETEMOVIE":
                case "FINDMOVIEBYID":
                case "FINDMOVIEBYYEAR":
                case "FINDMOVIEBYTITLE":
                case "FINDMOVIEBYDIRECTOR":
                case "FINDMOVIEWATCHEDBYUSERNAME":
                case "RECOMMANDMOVIE":
                    String str = "";
                    for (int i = 1; i < lineWords.length; i++) {
                        str += lineWords[i];
                        if (i < lineWords.length - 1) {
                            str += " ";
                        }
                    }
                    details = new String[2];
                    details[0] = subCommand;
                    details[1] = str;
                    break;
                case "UPDATEMOVIE":
                    String str1 = "";
                    for (int i = 3; i < lineWords.length; i++) {
                        str1 += lineWords[i];
                        if (i < lineWords.length - 1) {
                            str1 += " ";
                        }
                    }
                    details = new String[4];
                    details[0] = subCommand;
                    details[1] = lineWords[1];
                    details[2] = lineWords[2];
                    details[3] = str1;
                    break;

                case "INSERTMOVIE":
                    details = new String[15];
                    details[0] = subCommand;
                    for (int i = 1; i < lineWords.length; i++) {
                        details[i] = lineWords[i];
                    }
                    break;

                case "MOVIEWATCH":
                    details = new String[3];
                    details[0] = subCommand;
                    for (int i = 1; i < lineWords.length; i++) {
                        details[i] = lineWords[i];
                    }
                    break;
            }
        } else {
            details = new String[1];
            details[0] = command;
        }

        return details;

    }

    public static void insertMovie(String title, String genre, String director, String runtime,
            String plot, String location, String poster, String rating, String format,
            int year, String starring, int copies, String barcode, String userRating) {

        try {

            iMovieDao.insertMovie(title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, userRating);

        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public static String testMovies() {
        //MovieDaoInterface iMovieDao = new MySqlMovieDao();
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
        // MovieDaoInterface iMovieDao = new MySqlMovieDao();

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
        //MovieDaoInterface iMovieDao = new MySqlMovieDao();

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

    public static void updateMovie(int id, String str1, String str2) {
        // MovieDaoInterface iMovieDao = new MySqlMovieDao();
        try {
            iMovieDao.updateMovie(id, str1, str2);

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMovie(int id) {
        //MovieDaoInterface iMovieDao = new MySqlMovieDao();
        try {
            iMovieDao.deleteMovie(id);

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void insertNewMovie() {
        // MovieDaoInterface iMovieDao = new MySqlMovieDao();
        try {
            iMovieDao.insertNewMovie();

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void findMovieById(int id) {
        //MovieDaoInterface iMovieDao = new MySqlMovieDao();

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
        // MovieDaoInterface iMovieDao = new MySqlMovieDao();

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

    public static void findMovieByDirector(String directorName) {
        try {
            List<Movie> movies = iMovieDao.findMovieByDirector(directorName);
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

    public static void findMovieWatchedByUserName(String userName) {
        MovieUserWatchedInterface iMovieWatchedDao = new MySqlMovieUserWatchedDao();
        try {
            MovieUserWatched moviesWa = iMovieWatchedDao.findMovieWatchedByUserName(userName);
            if (moviesWa == null) {
                System.out.println("There is no record you searched, please check again.");
            } else {
                for (Movie m : moviesWa.getMovie()) {
                    System.out.println(m.getTitle());
                }
//                System.out.println(moviesWa.getMovie());
            }

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void movieWatch(String userName, int movieID) {
        MovieUserWatchedInterface iMovieWatchedDao = new MySqlMovieUserWatchedDao();
        try {
            MovieUserWatched moviesWa = iMovieWatchedDao.watchMovie(userName, movieID);
            if (moviesWa == null) {
                System.out.println("There is no record you searched, please check again.");
            } else {
                System.out.println(moviesWa);
            }

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void recommandMovie(String userName) {
        MovieUserWatchedInterface iMovieWatchedDao = new MySqlMovieUserWatchedDao();
        try {
            System.out.println(iMovieWatchedDao.recommandMovie(userName));
            

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

}
