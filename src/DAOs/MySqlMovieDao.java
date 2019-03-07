package DAOs;

import DTOs.Movie;
import Exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.jdbc.Statement;


public class MySqlMovieDao extends MySqlDao implements MovieDaoInterface{
	@Override
	public Movie findMovieById(int id) throws DaoException{
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Movie m = null;
		
		try {
			
			con = this.getConnection();
			
			String query = "SELECT * FROM movies WHERE id = ? ";
			
			ps = con.prepareStatement(query);
			
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				
				int ID = rs.getInt("id");
				
				String title = rs.getString("title");
				
				String genre = rs.getString("genre");
				
				String director = rs.getString("director");
				
				String runtime = rs.getString("runtime");
				
				String plot = rs.getString("plot");
				
				String location = rs.getString("location");
				
				String poster = rs.getString("poster");
				
				String rating = rs.getString("rating");
				
				String format = rs.getString("format");
				
				String year = rs.getString("year");
				
				String starring = rs.getString("starring");
				
				int copies = rs.getInt("copies");
				
				String barcode = rs.getString("barcode");
				
				String user_rating = rs.getString("user_rating");
				
				m = new Movie(ID,title,genre,director,runtime,plot,location,poster,rating,format,year,starring,copies,barcode,user_rating);
				
			}
			
		}
		
		catch(SQLException e) {
			
			throw new DaoException("findMovieById() " + e.getMessage());
			
		}finally {
			
			try {
				
				if(rs != null) {
					
					rs.close();
					
				}
				if(ps != null) {
					
					ps.close();
					
				}
				if(con != null) {
					
					freeConnection(con);
					
				}
				
			}catch(SQLException e) {
				
				throw new DaoException("findMovieById() " + e.getMessage());
				
			}
			
			
		}
		
		return m;
		
	}
	@Override
	
	public List<Movie> findMovieByTitle(String title) throws DaoException{
		
		Connection con = null;
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		List<Movie> movies = new ArrayList<>();
		
		try {
			
			con = this.getConnection();
			
			String query = "SELECT * FROM movies WHERE title = ?";
			
			ps = con.prepareStatement(query);
			
			ps.setString(1, title);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				int ID = rs.getInt("id");
				
				String Title = rs.getString("title");
				
				String genre = rs.getString("genre");
				
				String director = rs.getString("director");
				
				String runtime = rs.getString("runtime");
				
				String plot = rs.getString("plot");
				
				String location = rs.getString("location");
				
				String poster = rs.getString("poster");
				
				String rating = rs.getString("rating");
				
				String format = rs.getString("format");
				
				String year = rs.getString("year");
				
				String starring = rs.getString("starring");
				
				int copies = rs.getInt("copies");
				
				String barcode = rs.getString("barcode");
				
				String user_rating = rs.getString("user_rating");
				
				Movie m = new Movie(ID,Title,genre,director,runtime,plot,location,poster,rating ,format,year,starring,copies,barcode,user_rating);
				
				movies.add(m);
				
				
			}
			
			
		}catch(SQLException e) {
			
			throw new DaoException("findMovieByTitle " + e.getMessage());
			
		}finally {
		
		try {
			
			if(rs != null) {
				
				rs.close();
				
			}
			if(ps != null) {
				
				ps.close();
				
			}
			if(con != null) {
				
				freeConnection(con);
				
			}
			
		}catch(SQLException e) {
			
			throw new DaoException("findMovieByTitle() " + e.getMessage());
			
		}
		
		
	}
	
	return movies;
		
	}
	@Override
	public List<Movie> findMoviesByYear(String year) throws DaoException{
		
		Connection con = null;
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		List<Movie> movies = new ArrayList<>();
		
		try {
			
			con = this.getConnection();
			
			String query = "SELECT * FROM movies WHERE year = ?";
			
			ps = con.prepareStatement(query);
			
			ps.setString(1, year);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				int ID = rs.getInt("id");
				
				String Title = rs.getString("title");
				
				String genre = rs.getString("genre");
				
				String director = rs.getString("director");
				
				String runtime = rs.getString("runtime");
				
				String plot = rs.getString("plot");
				
				String location = rs.getString("location");
				
				String poster = rs.getString("poster");
				
				String rating = rs.getString("rating");
				
				String format = rs.getString("format");
				
				String Year = rs.getString("year");
				
				String starring = rs.getString("starring");
				
				int copies = rs.getInt("copies");
				
				String barcode = rs.getString("barcode");
				
				String user_rating = rs.getString("user_rating");
				
				Movie m = new Movie(ID,Title,genre,director,runtime,plot,location,poster,rating ,format,Year,starring,copies,barcode,user_rating);
				
				movies.add(m);
				
				
			}
			
			
		}catch(SQLException e) {
			
			throw new DaoException("findMovieByTitle " + e.getMessage());
			
		}finally {
		
		try {
			
			if(rs != null) {
				
				rs.close();
				
			}
			if(ps != null) {
				
				ps.close();
				
			}
			if(con != null) {
				
				freeConnection(con);
				
			}
			
		}catch(SQLException e) {
			
			throw new DaoException("findMovieByTitle() " + e.getMessage());
			
		}
		
		
	}
	
	return movies;
		
		
	}
	public void insertMovie() throws DaoException{
		
		Movie m = insertM();
		
		Connection con = null;
		
		PreparedStatement stmt = null;
		
		ResultSet rs = null;
		
		try {
			
					con = this.getConnection();
					
					String query = "INSERT INTO movies (title,genre,director,runtime,plot,location,poster,rating,format,year,starring,copies,barcode,user_rating)" 
							+" value(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					
					stmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
					
					stmt.setString(1, m.getTitle());
					
					stmt.setString(2, m.getGenre());
					
					stmt.setString(3, m.getDirector());
					
					stmt.setString(4, m.getRuntime());
					
					stmt.setString(5, m.getPlot());
					
					stmt.setString(6, m.getLocation());
					
					stmt.setString(7, m.getPoster());
					
					stmt.setString(8, m.getRating());
					
					stmt.setString(9, m.getFormat());
					
					stmt.setString(10, m.getYear());
					
					stmt.setString(11, m.getStarring());
					
					stmt.setInt(12, m.getCopies());
					
					stmt.setString(13, m.getBarcode());
					
					stmt.setString(14, m.getUser_rating());
					
					int add = stmt.executeUpdate();
					
					rs = stmt.getGeneratedKeys();
					
					int id = 0;
					
					if(rs.next()) {
						
						id = rs.getInt(1);
						
					}
					
					if(add > 0) {
						
						System.out.println("New Movie " + m.getTitle() + " has been added successfully which id is " + id);
						
					}
					
		}catch(SQLException e) {
			
			System.out.println("Erro Message : " + e.getMessage());
			
		}
		
	}
	public Movie insertM() {
		
		boolean s = true;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the Movie Information");
		
		System.out.println("Title:(required)");

		String title = sc.nextLine();
		
		while(s) {
					
				if(title.isEmpty()) {
						
					System.out.println("Title:(requried)");
						
					title = sc.nextLine();
						
				}else {
						
					s = false;
					
					System.out.print("√");
					
					System.out.println();
						
				}
					
			}
		
		System.out.println("Genre:(genre,genre,genre...)(requried)");
		
		String genre = sc.nextLine();
		
		while(s) {
			
			if(genre.isEmpty()) {
				
				System.out.println("Genre:(requried)");
				
				genre = sc.nextLine();
				
			}else {
				
				s = false;
				
				System.out.print("√");
				
				System.out.println();
				
			}
			
		}
		
		System.out.println("Director:(requried)");
		
		String director = sc.nextLine();
		
		while(s) {
			
			if(director.isEmpty()) {
				
				System.out.println("Director:(requried)");
				
				director = sc.nextLine();
				
			}else {
				
				s = false;
				
				System.out.print("√");
				
				System.out.println();
				
			}
			
		}
		
		System.out.println("Runtime:(number only)(required)");
		
		String runtime = sc.nextLine();

		while(s) {
			
			if(runtime.isEmpty()||isNum(runtime)) {
				
				System.out.println("Runtime : (requried)");
				
				runtime = sc.nextLine();
				
			}else {
				
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

		String year = sc.nextLine();
		
		while(s) {
			
			if(year.isEmpty() || !isNum(year)) {
				
				System.out.println("Year:(requried)");
				
				year = sc.nextLine();
				
			}else {
				
				s = false;
				
				System.out.print("√");
				
				System.out.println();
				
			}
			
		}
		
		
		System.out.println("Starring");
		
		String starring = sc.nextLine();
		
		System.out.println("Copies(required,default 1)");
		
		String copies = sc.nextLine();
		
		while(s) {
			
			if(!isNum(copies)) {
				
				System.out.println("Copies(required,default 1)");
				
				copies = sc.nextLine();
				
			}else {
				
				s = false;
				
			}
			
		}
		
		int copy = 0;
		
		if(copies.isEmpty()) {
			
			copy = 1;
			
		}else {
			
			copy = Integer.parseInt(copies);
			
		}
		
		System.out.println("Barcode");
		
		String barcode = sc.nextLine();
		
		System.out.println("Usering Rating");
		
		String user_rating = sc.nextLine();
		
		Movie m = new Movie(title , genre, director, runtime, plot, location, poster, rating, format, year, starring, copy, barcode, user_rating);
		
		return m;
		
	}
	
	@Override
	public void updateMovie(int id, String bupdate, String aupdate) throws DaoException {
		
		Connection con = null;
		
		PreparedStatement stmt = null;
		
		try {
			
			con = this.getConnection();
			
			String c = bupdate.toLowerCase();
			
			String qurey = "UPDATE movies SET " + c + "= ? WHERE id = " + id;
			
			stmt = con.prepareStatement(qurey);
			
			if(c == "copies") {
				
				stmt.setInt(1, Integer.parseInt(aupdate));
				
			}else {
				
				stmt.setString(1, aupdate);
				
			}
			
			int update = stmt.executeUpdate();
			
			if(update > 0) {
				
				System.out.println("Update Successful");
				
			}
			
		}catch(SQLException e) {
			
			System.out.println("Error Message : " + e.getMessage());
			
		}
		
	}
	
	public static boolean isNum(String s) {
		
		for(char c : s.toCharArray()) {
			
			if(!Character.isDigit(c)) return false;
			
		}
		
		return true;
		
	}
	
	@Override
	public void deleteMovie(int id) throws DaoException{
		
		Connection con = null;
		
		PreparedStatement stmt = null;
		
		try {
			
			con = this.getConnection();
			
			String query = "DELETE FROM movies WHERE id = ?";
			
			stmt = con.prepareStatement(query);
			
			stmt.setInt(1, id);
			
			int delete = stmt.executeUpdate();
			
			if(delete > 0) {
				
				System.out.println("Deleted");
				
			}
			
			
			
		}catch(SQLException e) {
			
			System.out.println("Error Message : " + e.getMessage());
			
		}
	}
	

	@Override
	public List<Movie> findMovieByDirector(String director) throws DaoException {
		
		Connection con = null;
		
		PreparedStatement stmt = null;
		
		ResultSet rs = null;
		
		ArrayList<Movie> m = new ArrayList<>();
		
		try {
			
			con = this.getConnection();
			
			String query = "SELECT * FROM movies WHERE director = ? ";
			
			stmt = con.prepareStatement(query);
			
			stmt.setString(1, director);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				int ID = rs.getInt("id");
				
				String Title = rs.getString("title");
				
				String genre = rs.getString("genre");
				
				String directors = rs.getString("director");
				
				String runtime = rs.getString("runtime");
				
				String plot = rs.getString("plot");
				
				String location = rs.getString("location");
				
				String poster = rs.getString("poster");
				
				String rating = rs.getString("rating");
				
				String format = rs.getString("format");
				
				String year = rs.getString("year");
				
				String starring = rs.getString("starring");
				
				int copies = rs.getInt("copies");
				
				String barcode = rs.getString("barcode");
				
				String user_rating = rs.getString("user_rating");
				
				Movie movie = new Movie(ID,Title,genre,directors,runtime,plot,location,poster,rating ,format,year,starring,copies,barcode,user_rating);
				
				m.add(movie);
				
			}
			
			
			
		}catch(SQLException e) {
			
			throw new DaoException("findMovieByDirector" + e.getMessage());
			
		}finally {
			
			try {
				
				if(rs != null) {
					
					rs.close();
					
				}
				if(stmt != null) {
					
					stmt.close();
					
				}
				if(con != null) {
					
					freeConnection(con);
					
				}
				
			}catch(SQLException e) {
				
				throw new DaoException("findMovieByTitle() " + e.getMessage());
				
			}
		
		
		}
		return m;
	}
}
	
	
