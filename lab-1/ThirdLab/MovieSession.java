package ThirdLab;
import java.util.Date;

public class MovieSession {
  private boolean[][] seats;
  private int freeSeatsCount;
  public Date movieStart = new Date();
  public String movieTitle;
  public int movieLength;

  public MovieSession(Date movieStart, int movieLength, String movieTitle) {
    if (movieTitle.length() == 0) throw new Error("Название фильма не может быть пустой строкой");
    if (movieLength < 60) throw new Error("Продолжительность фильма не может быть меньше 60 минут");

    this.movieStart = movieStart;
    this.movieLength = movieLength;
    this.movieTitle = movieTitle;
  }

  public void setSeatsConfig(int rowsCount, int columnsCount) {
    this.seats = new boolean[rowsCount][columnsCount];
    freeSeatsCount = rowsCount * columnsCount;
  }

  public void showSeats() {
    System.out.println("План зала (x - кресло забронировано, 0 - кресло свободно)");

    System.out.print(" ");
    for (int i = 0; i < seats[0].length; i++) {
      System.out.printf(" %d", i);
    }

    System.out.println("");
    for (int i = 0; i < seats.length; i++) {
      System.out.printf("%d ", i);
      for (int j = 0; j < seats[i].length; j++) {
        System.out.printf("%s ", this.checkSeatBook(i, j) ? "x" : "0");
      }
      System.out.println("");
    }
  }

  private boolean checkSeatBook(int row, int column) {
    return this.seats[row][column];
  }

  public void bookSeat(int row, int column) {
    if (this.checkSeatBook(row, column)) {
      throw new Error("Это место уже забронировано");
    }

    this.seats[row][column] = true;
    freeSeatsCount -= 1;
  }

  public boolean hasFreeSeats() {
    if (freeSeatsCount == 0) {
      return false;
    }

    return true;
  }
}
