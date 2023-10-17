package ThirdLab;

public class Ticket {
  private int cinemaId;
  private int hallId;
  private int[] seat = new int[2]; 
  private String movieTitle;

  public Ticket(int cinemaId, int hallId, String movieTitle, int row, int column) {
    this.cinemaId = cinemaId;
    this.hallId = hallId;
    this.movieTitle = movieTitle;
    this.seat[0] = row;
    this.seat[1] = column;
  }

  public int getCinema() {
    return this.cinemaId;
  }

  public int getHall() {
    return this.hallId;
  }

  public int[] getSeat() {
    return this.seat;
  }

  public String getMovieTitle() {
    return this.movieTitle;
  }
}
