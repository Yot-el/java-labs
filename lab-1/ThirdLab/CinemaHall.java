package ThirdLab;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CinemaHall {
  private ArrayList<MovieSession> movieSessions = new ArrayList<MovieSession>();
  int seatsRowCount;
  int seatsColumnsCount;

  public CinemaHall(int seatsRowsCount, int seatsColumnsCount) {
    if (seatsRowsCount <= 0 || seatsColumnsCount <= 0) {
      throw new Error("Зал не может быть создан с такими параметрами");
    }

    this.seatsColumnsCount = seatsColumnsCount;
    this.seatsRowCount = seatsRowsCount;
  }

  public void addMovie(MovieSession newMovie) {
    for (int i = 0; i < this.movieSessions.size(); i++) {
      MovieSession movie = movieSessions.get(i);

      // movie is after newMovie
      if (movie.movieStart.after(newMovie.movieStart)) {
        if (movie.movieStart.getTime() < newMovie.movieStart.getTime() + (long)newMovie.movieLength * 60000L) {
          throw new Error("В это время в зале идет другое кино");
        }
      }
      // movie is before newMovie
      else if (movie.movieStart.before(newMovie.movieStart)) {
        if (movie.movieStart.getTime() + (long)movie.movieLength * 60000L > newMovie.movieStart.getTime()) {
          throw new Error("В это время в зале идет другое кино");
        }
      }
      else {
        throw new Error("В это время в зале идет другое кино");
      }
    }

    newMovie.setSeatsConfig(seatsRowCount, seatsColumnsCount);
    movieSessions.add(newMovie);
    movieSessions.sort((a, b) -> a.movieStart.compareTo(b.movieStart));
  }

  public int getMoviesCount() {
    return movieSessions.size();
  }

  public MovieSession getMovie(String movieTitle) {
    for (MovieSession movie : movieSessions) {
      if (movie.movieTitle.equals(movieTitle)) {
        return movie;
      }
    }

    throw new Error("В этом зале не идет такого кино");
  }

  public MovieSession getNextMovie() {
    for (int i = 0; i < movieSessions.size(); i++) {
      if (movieSessions.get(i).hasFreeSeats()) {
        return movieSessions.get(i);
      }
    }

    return null;
  }

  public void showMovies(SimpleDateFormat formatter) {
    System.out.println("Доступные фильмы:");
    for (MovieSession movie : movieSessions) {
      if (movie.hasFreeSeats()) {
        System.out.printf("Название '%s', длительность %d минут, начало %s \n", movie.movieTitle, movie.movieLength, formatter.format(movie.movieStart.getTime()));
      }
    }
  }
}
