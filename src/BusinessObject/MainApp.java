package BusinessObject;
import DAOs.MovieDaoInterface;
import DAOs.MySqlMovieDao;

import java.util.List;
import DTOs.Movie;
import Exception.DaoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainApp {
	
	static MovieDaoInterface IMovieDao = new MySqlMovieDao(); 
	
	public static void main(String[] args)
	{
		MainApp server = new MainApp();
		server.start();
	}

	public void start()
	{
		try {
			ServerSocket ss = new ServerSocket(8080); // set up ServerSocket to listen for connections on port 8080

			System.out.println("Server: Server started. Listening for connections on port 8080...");

			int clientNumber = 0; // a number for clients that the server allocates as clients connect

			while (true) // loop continuously to accept new client connections
			{
				Socket socket = ss.accept(); // listen (and wait) for a connection, accept the connection,
												// and open a new socket to communicate with the client
				clientNumber++;

				System.out.println("Server: Client " + clientNumber + " has connected.");

				System.out.println("Server: Port# of remote client: " + socket.getPort());
				System.out.println("Server: Port# of this server: " + socket.getLocalPort());

				Thread t = new Thread(new ClientHandler(socket, IMovieDao, clientNumber)); // create a new ClientHandler
																						// for the client,
				t.start(); // and run it in its own thread

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

		public ClientHandler(Socket clientSocket, MovieDaoInterface IMovieDao, int clientNumber) {
			try {
				InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
				this.socketReader = new BufferedReader(isReader);

				OutputStream os = clientSocket.getOutputStream();
				this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

				this.clientNumber = clientNumber; // ID number that we are assigning to this client

				this.socket = clientSocket; // store socket ref for closing

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		@Override
		public void run() {
			String command;
			try {
				while ((command = socketReader.readLine()) != null) {
					System.out.println(
							"Server: (ClientHandler): Read command from client " + clientNumber + ": " + command);

						String[] a;

						a = splitC(command);

					switch (a[0].toUpperCase()) {

						case "GETMOVIEBYYEAR":
							socketWriter.println(getMovieByYear(a[1]));
							break;

						case "GETMOVIEBYTITLE":
							socketWriter.println(getMovieByTitle(a[1]));
							break;

						case "FINDMOVIEBYDIRECTOR":
							socketWriter.println(findMovieByDirector(a[1]));
							break;
							
						case "GETMOVIEBYID":
							socketWriter.println(getMovieById(Integer.parseInt(a[1])));
							break;
							
						case "INSERTMOVIE":
							insertMovie();
							break;

						case "DELETEMOVIE":
							deleteMovie(Integer.parseInt(a[1]));
							break;

						case "UPDATEMOVIE":
							updateMovie(Integer.parseInt(a[1]), a[2], a[3]);
							break;

						}

				}

				socket.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
			System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
		}
	}

	
	public static String getMovieByTitle(String title) {
		
//		MovieDaoInterface IMovieDao = new MySqlMovieDao(); 
		
		String jsonString = "";
		
		try {
			
			List<Movie> movies = IMovieDao.findMovieByTitle(title);
			
			if(movies.isEmpty()) {
				
				System.out.println("There is no such movie named + " + title);
				
			}else {
			
				jsonString = "{\"movies\":\n[" ;
	
				int i = 0;
				
				for(Movie m : movies) {
					
					jsonString = jsonString + toJson(m);
					
					if(i < movies.size()-1) {
						
						jsonString += ",\n";
						
						i++;
						
					}
					
				}
				
				jsonString += "]}";
			}
			
		}catch(DaoException e) {
			
			e.printStackTrace();
			
		}
		
		return jsonString;
		
	}
	
	public static String getMovieById(int id) {
		
//		MovieDaoInterface IMovieDao = new MySqlMovieDao();
		
		String jsonString="";
		
		try {
			
			Movie m = IMovieDao.findMovieById(id);
			
			if(m.equals(null)) {
				
				System.out.println("There is no such movie " + id);
				
			}else {
			
				jsonString += toJson(m);
				
			}
		}catch(DaoException e) {
			
			e.printStackTrace();
			
		}
		
		return jsonString;
		
	}
	
	public static String findMovieByDirector(String director) {
		
		String jsonString = "";
		
		try {
			
			List<Movie> movies = IMovieDao.findMovieByDirector(director);
			
			if(movies.isEmpty()) {
				
				System.out.println("There is no such Movies");
				
			}
			
			jsonString = "{\"moives\":\n[" ;

			int i = 0;
			
			for(Movie m : movies) {
				
				jsonString = jsonString + toJson(m);
				
//				System.out.println(jsonString);
				
				if(i < movies.size()-1) {
					
					jsonString += ",\n";
					
					i++;
					
				}
				
			}
			
			jsonString += "]}";
			
		}catch(DaoException e) {
			
			e.printStackTrace();
			
		}
		
		return jsonString;
		
	}
	
	public static String getMovieByYear(String year) {
		
//		MovieDaoInterface IMovieDao = new MySqlMovieDao();
		
		String jsonString = "";
	
		try {	

			List<Movie> movies = IMovieDao.findMoviesByYear(year);
			
			if(movies.isEmpty()) {
				
				System.out.println("There are no Moives");
				
			}
			
//			System.out.println(movies);
			
			jsonString = "{\"moives\":\n[" ;

			int i = 0;
			
			for(Movie m : movies) {
				
				jsonString = jsonString + toJson(m);
				
//				System.out.println(jsonString);
				
				if(i < movies.size()-1) {
					
					jsonString += ",\n";
					
					i++;
					
				}
				
			}

			jsonString += "]}";
			
		}catch(DaoException e) {
		
			e.printStackTrace();
		
		}
		
		return jsonString;
	
	}
	
	public static void insertMovie() {
		
//		MovieDaoInterface IMovie = new MySqlMovieDao();
		
		try {
			
			IMovieDao.insertMovie();
		
		}catch(DaoException e) {
			
			e.printStackTrace();
			
		}
	}
	
	public static String toJson(Movie m) {
		
			String movieString = "{\"id\":\"" + m.getId() + "\","
					+ "\"title\":\"" + m.getTitle() + "\","
					+ "\"genre\":\"" + m.getGenre() + "\","
					+ "\"director\":\"" + m.getDirector() + "\","
					+ "\"runtime\":\"" + m.getRuntime() + "\","
					+ "\"plot\":\"" + m.getPlot() + "\","
					+ "\"location\":\"" + m.getLocation() + "\","
					+ "\"poster\":\"" + m.getPoster() + "\","
					+ "\"rating\":\"" + m.getRating() + "\","
					+ "\"format\":\"" + m.getFormat() + "\","
					+ "\"year\":\"" + m.getYear() + "\","
					+ "\"starring\":\"" + m.getStarring() + "\","
					+ "\"copies\":\"" + m.getCopies() + "\","
					+ "\"barcode\":\"" + m.getBarcode() + "\","
					+ "\"user_rating\":\"" + m.getUser_rating() + "\"}";
			
			
			return movieString;
		}
		
	
	
	public static String[] splitC(String c) {
		
		String[] a ;
		
		if(c.contains(" ")) {
			
			String[] first = c.split(" ");
			
			String com = first[0].toLowerCase();
			
			if(com.equals("getmoviebyid")|| com.equals("getmoviebyyear")||com.equals("deletemovie")) {
				
				a = first;
				
				return a;
				
			}else if(com.equals("getmoviebytitle") || com.equals("findmoviebydirector")){
				
				String x = "";
				
				for(int i = 1 ; i < first.length; i++) {

					x += " ";
					
					x += first[i]; 
					
				}
				
				x = x.substring(1);
				
				a = new String[2];
				
				a[0] = com;
				
				a[1] = x;
				
				return a;
				
			}else {
				
				a = new String[4];
				
				a[0] = com;
				
				a[1] = first[1];
				
				a[2] = first[2];
				
				String x = "";
				
				for(int i = 3 ; i < first.length; i++) {

					x += " ";
					
					x += first[i]; 
					
				}
				
				x = x.substring(1);
				
				
				a[3] = x;
				
				return a;
				
				
			}
			
		}else {
			
			a = new String[1];
			
			a[0] = c;
			
			return a;
			
		}
		
		
	}
	
	public static void deleteMovie(int id) {
		
		try {
			
			IMovieDao.deleteMovie(id);
			
		}catch(DaoException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void updateMovie(int id, String where, String to) {
		
		try {
			
			IMovieDao.updateMovie(id,where,to);
			
		}catch(DaoException e){
			
			e.printStackTrace();
			
		}
		
	}
	
}


