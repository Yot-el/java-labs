package ThirdLab;
import java.util.ArrayList;

public class Cinema {
  private ArrayList<CinemaHall> cinemaHalls = new ArrayList<CinemaHall>();

  public void addCinemaHall(CinemaHall hall) {
    this.cinemaHalls.add(hall);
  }

  public int getHallsCount() {
    return cinemaHalls.size();
  }

  public void showHalls() {
    System.out.println("Номера доступных залов:");
    for (int i = 0; i < this.getHallsCount(); i++) {
      System.out.printf("%d ", i);
    }
    System.out.println("");
  }

  public void addMovie(int hallId, MovieSession movie) {
    CinemaHall hall;
    hall = this.getHall(hallId);
    hall.addMovie(movie);
  }

  public CinemaHall getHall(int hallId) {
    CinemaHall hall;

    try {
      hall = this.cinemaHalls.get(hallId);
    }
    catch (IndexOutOfBoundsException e) {
      throw new Error("В этом кинотеатре нет такого кинозала");
    }

    return hall;
  }
}
