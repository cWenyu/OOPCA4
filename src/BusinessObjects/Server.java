package BusinessObjects;

import DAOs.JoinedUserMovieDao;
import DAOs.JoinedUserMovieInterface;
import DAOs.MovieDaoInterface;
import DAOs.MovieUserWatchedInterface;
import DAOs.MySqlMovieDao;
import DAOs.MySqlMovieUserWatchedDao;
import DTOs.JoinedUserMovie;
import DTOs.Movie;
import DTOs.MovieUserWatched;
import Exceptions.DaoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Server {

    static MovieDaoInterface iMovieDao = new MySqlMovieDao();
    static MovieUserWatchedInterface iMovieWatchedDao = new MySqlMovieUserWatchedDao();
    static JoinedUserMovieInterface iJoinedUserMovieDao = new JoinedUserMovieDao();
    static Gson gson = new Gson();

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
//            MovieDaoInterface iMovieDao = new MySqlMovieDao();
//            MovieUserWatchedInterface iMovieWatchedDao = new MySqlMovieUserWatchedDao();
//            JoinedUserMovieInterface iJoinedUserMovieDao = new JoinedUserMovieDao();

            ServerSocket ss = new ServerSocket(8080);  // set up ServerSocket to listen for connections on port 8080

            System.out.println("Server: Server started. Listening for connections on port 8080...");

            int clientNumber = 0;  // a number for clients that the server allocates as clients connect

            while (true) // loop continuously to accept new client connections
            {
                Socket socket = ss.accept();    // listen (and wait) for a connection, accept the connection, 
                // and open a new socket to communicate with the client
                clientNumber++;

                System.out.println("Server: Client " + clientNumber + " has connected.");

                System.out.println("Server: Port# of remote client: " + socket.getPort());
                System.out.println("Server: Port# of this server: " + socket.getLocalPort());

                Thread t = new Thread(new ClientHandler(socket, iMovieDao, clientNumber)); // create a new ClientHandler for the client,
                t.start();  // and run it in its own thread

                Thread t1 = new Thread(new ClientHandler(socket, iMovieWatchedDao, clientNumber)); // create a new ClientHandler for the client,
                t1.start();

                Thread t2 = new Thread(new ClientHandler(socket, iJoinedUserMovieDao, clientNumber));
                t2.start();

                System.out.println("Server: ClientHandler started in thread for client " + clientNumber + ". ");
                System.out.println("Server: Listening for further connections...");
            }
        } catch (IOException e) {
            System.out.println("Server: IOException: " + e);
        }
        System.out.println("Server: Server exiting, Goodbye!");
    }

    public class ClientHandler implements Runnable // each ClientHandler communicates with one Client
    {

        BufferedReader socketReader;
        PrintWriter socketWriter;
        Socket socket;
        int clientNumber;

        public ClientHandler(Socket clientSocket, MovieDaoInterface iMovieDao, int clientNumber) {
            try {
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                this.socketReader = new BufferedReader(isReader);

                OutputStream os = clientSocket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

                this.clientNumber = clientNumber;  // ID number that we are assigning to this client

                this.socket = clientSocket;  // store socket ref for closing 

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public ClientHandler(Socket clientSocket, MovieUserWatchedInterface iMovieWatchedDao, int clientNumber) {
            try {
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                this.socketReader = new BufferedReader(isReader);

                OutputStream os = clientSocket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

                this.clientNumber = clientNumber;  // ID number that we are assigning to this client

                this.socket = clientSocket;  // store socket ref for closing 

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public ClientHandler(Socket clientSocket, JoinedUserMovieInterface iJoinedUserMovieDao, int clientNumber) {
            try {
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                this.socketReader = new BufferedReader(isReader);

                OutputStream os = clientSocket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

                this.clientNumber = clientNumber;  // ID number that we are assigning to this client

                this.socket = clientSocket;  // store socket ref for closing 

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            String command = "";
            String message;
            String emptyMessage = "There is no moive you searched, please check again.";
            try {
                while ((command = socketReader.readLine()) != null) {
                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + command);

                    String[] details = splitCommand(command);
                    String subCommand = details[0].toUpperCase();

                    List<Movie> movies = new ArrayList<>();

                    Movie m = new Movie();
                    MovieUserWatched movieWa = new MovieUserWatched();

                    JoinedUserMovie jum = new JoinedUserMovie();

                    switch (subCommand) {
                        case "TESTMOVIES":
                            movies = testMovies();
                            if (movies.isEmpty()) {
                                message = emptyMessage;
                            } else {
                                message = converToJson(movies);
                            }
                            socketWriter.println(message);
                            break;

                        case "FINDMOVIEBYID":
                            m = findMovieById(Integer.parseInt(details[1]));
                            if (m == null) {
                                message = emptyMessage;
                            } else {
                                message = toJson(m);
                            }
                            socketWriter.println(message);
                            break;

                        case "FINDMOVIEBYTITLE":
                            movies = findMovieByTitle(details[1]);
                            if (movies.isEmpty()) {
                                message = emptyMessage;
                            } else {
                                message = converToJson(movies);
                            }
                            socketWriter.println(message);
                            break;

                        case "FINDMOVIEBYYEAR":
                            movies = findMovieByYear(Integer.parseInt(details[1]));
                            if (movies.isEmpty()) {
                                message = emptyMessage;
                            } else {
                                message = converToJson(movies);
                            }
                            socketWriter.println(message);
                            break;

                        case "FINDMOVIEBYDIRECTOR":
                            movies = findMovieByDirector(details[1]);
                            if (movies.isEmpty()) {
                                message = emptyMessage;
                            } else {
                                message = converToJson(movies);
                            }
                            socketWriter.println(message);
                            break;

                        case "TOPTENMOVIES":
                            movies = topTenMovies();
                            if (movies.isEmpty()) {
                                message = emptyMessage;
                            } else {
                                message = converToJson(movies);
                            }
                            socketWriter.println(message);
                            break;

                        case "DELETEMOVIE":
                            message = deleteMovie(Integer.parseInt(details[1]));
                            socketWriter.println(message);
                            break;

                        case "UPDATEMOVIE":
                            message = updateMovie(Integer.parseInt(details[1]), details[2], details[3]);
                            socketWriter.println(message);
                            break;

                        case "INSERTMOVIE":
                            m = insertMovie(details[1], details[2], details[3], details[4], details[5], details[6], details[7],
                                    details[8], details[9], Integer.parseInt(details[10]), details[11], Integer.parseInt(details[12]), details[13], details[14]);
                            if (m == null) {
                                message = emptyMessage;
                            } else {
                                message = toJson(m);
                            }
                            socketWriter.println(message);
                            break;

//                        case "FINDMOVIEWATCHEDBYUSERNAME":
//                            jum = findMovieWatchedByUserName(details[1]);
//                            if (jum == null) {
//                                message = emptyMessage;
//                            } else {
//                                message = movieWatchedToJson(jum);
//                            }
//                            socketWriter.println(message);
//                            break;
                        case "MOVIEWATCH":
                            movieWa = movieWatch(details[1], Integer.parseInt(details[2]));
                            message = movieWatchedToJson(movieWa);
                            socketWriter.println(message);
                            break;

                        case "RECOMMANDMOVIE":
                            movies = recommandMovie(details[1]);
                            if (movies.isEmpty()) {
                                message = emptyMessage;
                            } else {
                                message = converToJson(movies);
                            }
                            socketWriter.println(message);
                            break;

                        case "Q":
                            socketWriter.println("Bye");
                            break;

                        default:
                            socketWriter.println("I'm sorry I don't understand :(");
                            break;
                    }
                }
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (DaoException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
        }
    }

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

    public synchronized List<Movie> testMovies() throws DaoException {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = iMovieDao.testMovies();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public synchronized Movie findMovieById(int id) {
        Movie m = new Movie();
        try {
            m = iMovieDao.findMovieById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return m;
    }

    public synchronized List<Movie> findMovieByTitle(String title) {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = iMovieDao.findMovieByTitle(title);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public synchronized List<Movie> topTenMovies() {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = iMovieDao.topTenMovies();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public synchronized String updateMovie(int id, String str1, String str2) {
        String message = "";
        try {
            if (iMovieDao.findMovieById(id) == null) {
                message = "There is no movie you updated, please check ID again";
            } else {
                message = iMovieDao.updateMovie(id, str1, str2);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return message;
    }

    public synchronized String deleteMovie(int id) {
        String message = "";
        try {
            if (iMovieDao.findMovieById(id) == null) {
                message = "There is no movie you deleted, please check ID again";
            } else {
                message = iMovieDao.deleteMovie(id);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return message;
    }

//    public synchronized Movie insertNewMovie() {
//        Movie m = new Movie();
//        try {
//            m = iMovieDao.insertNewMovie();
//
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
//        return m;
//    }
    public synchronized Movie insertMovie(String title, String genre, String director, String runtime,
            String plot, String location, String poster, String rating, String format,
            int year, String starring, int copies, String barcode, String userRating) {
        Movie m = new Movie();
        try {
            m = iMovieDao.insertMovie(title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, userRating);

        } catch (DaoException e) {
            e.printStackTrace();
        }
        return m;
    }

    public synchronized List<Movie> findMovieByYear(int year) {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = iMovieDao.findMovieByYear(year);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public synchronized List<Movie> findMovieByDirector(String directorName) {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = iMovieDao.findMovieByDirector(directorName);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static String converToJson(List<Movie> movies) {
        String jsonString = "{"
                + "\"movies\":"
                + "[ ";
        int i = 1;
        for (Movie movie : movies) {
            jsonString += toJson(movie);

            if (i < movies.size()) {
                jsonString += ",";
                i++;
            } else {
                jsonString += " ";
            }

        }
        jsonString += "]"
                + "}";
        return jsonString;
    }

    public static String toJson(Movie m) {
        return gson.toJson(m);
//        return "{"
//                + "\"id\":" + m.getId() + ","
//                + "\"title\":\"" + m.getTitle() + "\","
//                + "\"genre\":\"" + m.getGenre() + "\","
//                + "\"director\":\"" + m.getDirector() + "\","
//                + "\"runtime\":\"" + m.getRuntime() + "\","
//                + "\"plot\":\"" + m.getPlot() + "\","
//                + "\"location\":\"" + m.getLocation() + "\","
//                + "\"poster\":\"" + m.getPoster() + "\","
//                + "\"rating\":\"" + m.getRating() + "\","
//                + "\"format\":\"" + m.getFormat() + "\","
//                + "\"year\":" + m.getYear() + ","
//                + "\"starring\":\"" + m.getStarring() + "\","
//                + "\"copies\":" + m.getCopies() + ","
//                + "\"barcode\":" + m.getBarcode() + ","
//                + "\"userRating\":" + m.getUserRating()
//                + "}";
    }

    public static JoinedUserMovie findMovieWatchedByUserName(String userName) {
        JoinedUserMovie jum = new JoinedUserMovie();
        try {
            jum = iJoinedUserMovieDao.findMovieWatchedByUserName(userName);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return jum;
    }

    public static MovieUserWatched movieWatch(String userName, int movieID) {
        MovieUserWatched movieWa = new MovieUserWatched();
        try {
            movieWa = iMovieWatchedDao.watchMovie(userName, movieID);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return movieWa;
    }

    public static String movieWatchedJson(List<Movie> movies) {
        String jsonString = "[ ";
        int i = 1;
        for (Movie movie : movies) {
            jsonString += toJson(movie);

            if (i < movies.size()) {
                jsonString += ",";
                i++;
            } else {
                jsonString += " ";
            }

        }
        jsonString += "]";
        return jsonString;
    }

    public static String movieWatchedToJson(MovieUserWatched muw) {
        return gson.toJson(muw);
    }

//    public static String movieWatchedToJson(JoinedUserMovie jum) {
//        String s = "{"
//                + "\"user name\":\"" + jum.getUserName() + "\","
//                + "\"movies\":";
//
//        if (!jum.getMovies().isEmpty()) {
//            s += movieWatchedJson(jum.getMovies());
//        }
//        s += "}";
//        return s;
//    }
    public static List<Movie> recommandMovie(String userName) {
        List<Movie> movies = new ArrayList<>();
        List<Movie> recommandMovies = new ArrayList<>();
        try {
            movies = iJoinedUserMovieDao.findMovieWatchedByUserName(userName).getMovies();

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

            recommandMovies = iMovieDao.recommandedMovies(maxDirec, maxGenre, maxActor);

        } catch (DaoException e) {
            e.printStackTrace();
        }
        return recommandMovies;
    }
}
