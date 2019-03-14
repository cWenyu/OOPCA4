package DTOs;

public class Movie {
	
	private int id;
	private String title;
	private String genre;
	private String director;
	private String runtime;
	private String plot;
	private String location;
	private String poster;
	private String rating;
	private String format;
	private String year;
	private String starring;
	private int copies;
	private String barcode;
	private String user_rating;
	
	public Movie(int id, String title, String genre, String director, String runtime, String plot,
			String location,String poster, String rating, String format, String year, String starring, int copies,
			String barcode, String user_rating) {
		this.id = id;
		this.title = title;
		this.genre = genre;
		this.director = director;
		this.runtime = runtime;
		this.plot = plot;
		this.location = location;
		this.poster = poster;
		this.rating = rating;
		this.format = format;
		this.year = year;
		this.starring = starring;
		this.copies = copies;
		this.barcode = barcode;
		this.user_rating = user_rating;
	}
	
	
	//only used in insert s
	public Movie(String title, String genre, String director, String runtime, String plot,
			String location,String poster, String rating, String format, String year, String starring, int copies,
			String barcode, String user_rating) {
		this.title = title;
		this.genre = genre;
		this.director = director;
		this.runtime = runtime;
		this.plot = plot;
		this.location = location;
		this.poster = poster;
		this.rating = rating;
		this.format = format;
		this.year = year;
		this.starring = starring;
		this.copies = copies;
		this.barcode = barcode;
		this.user_rating = user_rating;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getPoster() {
		return this.poster;
	}
	
	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getStarring() {
		return starring;
	}

	public void setStarring(String starring) {
		this.starring = starring;
	}

	public int getCopies() {
		return copies;
	}

	public void setCopies(int copies) {
		this.copies = copies;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String  getUser_rating() {
		return user_rating;
	}

	public void setUser_rating(String user_rating) {
		this.user_rating = user_rating;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", genre=" + genre + ", director=" + director + ", runtime="
				+ runtime + ", plot=" + plot + ", location=" + location + ", rating=" + rating + ", format=" + format
				+ ", year=" + year + ", starring=" + starring + ", copies=" + copies + ", barcode=" + barcode
				+ ", user_rating=" + user_rating + "]";
	}
	
//	public String toJson() {
//		
//		String movieString = "{\"id\":\"" + this.getId() + "\","
//				+ "\"title\":\"" + this.getTitle() + "\","
//				+ "\"genre\":\"" + this.getGenre() + "\","
//				+ "\"director\":\"" + this.getDirector() + "\","
//				+ "\"runtime\":\"" + this.getRuntime() + "\","
//				+ "\"plot\":\"" + this.getPlot() + "\","
//				+ "\"location\":\"" + this.getLocation() + "\","
//				+ "\"poster\":\"" + this.getPoster() + "\","
//				+ "\"rating\":\"" + this.getRating() + "\","
//				+ "\"format\":\"" + this.getFormat() + "\","
//				+ "\"year\":\"" + this.getYear() + "\","
//				+ "\"starring\":\"" + this.getStarring() + "\","
//				+ "\"copies\":\"" + this.getCopies() + "\","
//				+ "\"barcode\":\"" + this.getBarcode() + "\","
//				+ "\"user_rating\":\"" + this.getUser_rating() + "\"}";
//		
//		
//		return movieString;
//	}
	
	
	
}
