package BusinessObjects;

/**
 * SERVER February 2019 DL 08/03/19
 *
 * Server accepts client connections, creates a ClientHandler to handle the
 * Client communication, creates a socket and passes the socket to the handler,
 * runs the handler in a separate Thread.
 *
 *
 * The handler reads requests from clients, and sends replies to clients, all in
 * accordance with the rules of the protocol. as specified in
 * "ClientServerBasic" sample program
 *
 * The following PROTOCOL is implemented:
 *
 * If ( the Server receives the request "Time", from a Client ) then : the
 * server will send back the current time
 *
 * If ( the Server receives the request "Echo message", from a Client ) then :
 * the server will send back the message
 *
 * If ( the Server receives the request it does not recognize ) then : the
 * server will send back the message "Sorry, I don't understand"
 *
 * This is an example of a simple protocol, where the server's response is based
 * on the client's request.
 *
 *
 */
import static BusinessObjects.MainApp.findMovieByTitle;
import static BusinessObjects.MainApp.iMovieDao;
import static BusinessObjects.MainApp.splitCommand;
import static BusinessObjects.MainApp.testMovies;
import DAOs.MovieDaoInterface;
import DAOs.MySqlMovieDao;
import DTOs.Movie;
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

public class Server {

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
            MovieDaoInterface iMovieDao = new MySqlMovieDao();
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
                t.start();                                                  // and run it in its own thread

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
                                message = toJson(m);
                            }
                            socketWriter.println(message);
                            break;

                        case "FINDMOVIEBYYEAR":
                            movies = findMovieByYear(Integer.parseInt(details[1]));
                            if (movies.isEmpty()) {
                                message = emptyMessage;
                            } else {
                                message = toJson(m);
                            }
                            socketWriter.println(message);
                            break;
                        case "FINDMOVIEBYDIRECTOR":
                            movies = findMovieByDirector(details[1]);
                            if (movies.isEmpty()) {
                                message = emptyMessage;
                            } else {
                                message = toJson(m);
                            }
                            socketWriter.println(message);
                            break;

                        case "TOPTENMOVIES":
                            movies = topTenMovies();
                            if (movies.isEmpty()) {
                                message = emptyMessage;
                            } else {
                                message = toJson(m);
                            }
                            socketWriter.println(message);
                            break;

                        case "DELETEMOVIE":
                            message = deleteMovie(Integer.parseInt(details[1]));
                            socketWriter.println(message);
                            break;

                        case "UPDATEMOVIETITLE":
                            message = updateMovieTitle(Integer.parseInt(details[1]), details[2]);
                            socketWriter.println(message);
                            break;

                        case "INSERTNEWMOVIE":
                            message = insertNewMovie();
                            socketWriter.println(message);
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
                case "UPDATEMOVIETITLE":
                    String str1 = "";
                    for (int i = 2; i < lineWords.length; i++) {
                        str1 += lineWords[i];
                        if (i < lineWords.length - 1) {
                            str1 += " ";
                        }
                    }
                    details = new String[3];
                    details[0] = subCommand;
                    details[1] = lineWords[1];
                    details[2] = str1;
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

    public synchronized String updateMovieTitle(int id, String title) {
        String message = "";
        try {
            if (iMovieDao.findMovieById(id) == null) {
                message = "There is no movie you updated, please check ID again";
            } else {
                message = iMovieDao.updateMovieTitle(id, title);
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

    public synchronized String insertNewMovie() {
        String message = "";
        try {
            message = iMovieDao.insertNewMovie();

        } catch (DaoException e) {
            e.printStackTrace();
        }
        return message;
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
        return "{"
                + "\"id\":" + m.getId() + ","
                + "\"title\":\"" + m.getTitle() + "\","
                + "\"genre\":\"" + m.getGenre() + "\","
                + "\"director\":\"" + m.getDirector() + "\","
                + "\"runtime\":\"" + m.getRuntime() + "\","
                + "\"plot\":\"" + m.getPlot() + "\","
                + "\"location\":\"" + m.getLocation() + "\","
                + "\"poster\":\"" + m.getPoster() + "\","
                + "\"rating\":\"" + m.getRating() + "\","
                + "\"format\":\"" + m.getFormat() + "\","
                + "\"year\":" + m.getYear() + ","
                + "\"starring\":\"" + m.getStarring() + "\","
                + "\"copies\":" + m.getCopies() + ","
                + "\"barcode\":" + m.getBarcode() + ","
                + "\"userRating\":" + m.getUserRating()
                + "}";
    }

}
