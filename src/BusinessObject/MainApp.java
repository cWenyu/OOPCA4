package BusinessObject;
import DAOs.MovieDaoInterface;
import DAOs.MySqlMovieDao;

import java.util.List;
import DTOs.Movie;
import Exception.DaoException;
import java.util.Scanner;


public class MainApp {
	
	static MovieDaoInterface IMovieDao = new MySqlMovieDao(); 
	
	public static void main(String[] args) {
		
		String c;
		
		int i = 0;
		System.out.println("Hello");
		
		do {
			
			System.out.println();
			
			System.out.print("Command:");
		
			Scanner sc = new Scanner(System.in);
			
			c = sc.nextLine();
			
			String[] a;
			
			a = splitC(c);
			
			switch(a[0].toUpperCase()){
				
				case "GETMOVIEBYYEAR":
					getMovieByYear(a[1]);
					i = 1;
					break;
				
				case "GETMOVIEBYTITLE":
					getMovieByTitle(a[1]);
					i=1;
					break;
					
				case "FINDMOVIEBYDIRECTOR":
					findMovieByDirector(a[1]);
					i=1;
					break;
					
				case "GETMOVIEBYID":
					getMovieById(Integer.parseInt(a[1]));
					i=1;
					break;
				
				case "INSERTMOVIE":
					insertMovie();
					i=1;
					break;
					
				case "DELETEMOVIE":
					deleteMovie(Integer.parseInt(a[1]));
					i = 1;
					break;
					
				case "UPDATEMOVIE":
					updateMovie(Integer.parseInt(a[1]),a[2],a[3]);
					i=1;
					break;
					
				case "QUIT":
					System.out.println("Bye");
					i= 0;
					
					
			}
			
			
		}while(i == 1);
		
	}
	
	public static void getMovieByTitle(String title) {
		
//		MovieDaoInterface IMovieDao = new MySqlMovieDao(); 
		
		try {
			
			List<Movie> movies = IMovieDao.findMovieByTitle(title);
			
			if(movies.isEmpty()) {
				
				System.out.println("There is no such movie named + " + title);
				
			}else {
			
				String jsonString = "{\"movies\":\n[" ;
	
				int i = 0;
				
				for(Movie m : movies) {
					
					jsonString = jsonString + m.toJson();
					
					if(i < movies.size()-1) {
						
						jsonString += ",\n";
						
						i++;
						
					}
					
				}
				
				System.out.println(jsonString);
				
				System.out.print("]}");
				
			}
			
		}catch(DaoException e) {
			
			e.printStackTrace();
			
		}
		
		
	}
	
	public static void getMovieById(int id) {
		
//		MovieDaoInterface IMovieDao = new MySqlMovieDao();
		
		try {
			
			Movie m = IMovieDao.findMovieById(id);
			
			if(m.equals(null)) {
				
				System.out.println("There is no such movie " + id);
				
			}
			
			System.out.println(m.toJson());
			
		}catch(DaoException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void findMovieByDirector(String director) {
		
		try {
			
			List<Movie> movies = IMovieDao.findMovieByDirector(director);
			
			if(movies.isEmpty()) {
				
				System.out.println("There is no such Movies");
				
			}
			
			String jsonString = "{\"moives\":\n[" ;

			int i = 0;
			
			for(Movie m : movies) {
				
				jsonString = jsonString + m.toJson();
				
//				System.out.println(jsonString);
				
				if(i < movies.size()-1) {
					
					jsonString += ",\n";
					
					i++;
					
				}
				
			}
			
			System.out.println(jsonString);
			
			System.out.print("]}");
			
		}catch(DaoException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void getMovieByYear(String year) {
		
//		MovieDaoInterface IMovieDao = new MySqlMovieDao();
	
		try {	

			List<Movie> movies = IMovieDao.findMoviesByYear(year);
			
			if(movies.isEmpty()) {
				
				System.out.println("There are no Moives");
				
			}
			
//			System.out.println(movies);
			
			String jsonString = "{\"moives\":\n[" ;

			int i = 0;
			
			for(Movie m : movies) {
				
				jsonString = jsonString + m.toJson();
				
//				System.out.println(jsonString);
				
				if(i < movies.size()-1) {
					
					jsonString += ",\n";
					
					i++;
					
				}
				
			}
			
			System.out.println(jsonString);
			
//			System.out.println(jsonString.length());
			

			System.out.print("]}");
		
		}catch(DaoException e) {
		
			e.printStackTrace();
		
		}
	
	}
	
	public static void insertMovie() {
		
//		MovieDaoInterface IMovie = new MySqlMovieDao();
		
		try {
			
			IMovieDao.insertMovie();
		
		}catch(DaoException e) {
			
			e.printStackTrace();
			
		}
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
