package ThirdLab;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
  static User user;
  static ArrayList<Cinema> cinemas = new ArrayList<Cinema>();
  static Scanner scanner = new Scanner(System.in, "Cp866");
  static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
  public static void main (String[] args) {
    System.out.println("Введите: 1, чтобы войти как администратор; 2, чтобы войти как пользователь");
    int userType = scanner.nextInt();
    scanner.nextLine();

    try {
      initDefaultCinemas();
    }
    catch (ParseException e) {
      e.printStackTrace();
    }

    switch (userType) {
      case 1:
        user = new Admin();
        break;
      case 2:
        user = new Customer();
        break;
    }

    showUserActions();
  }

  public static void showUserActions() {
    user.showActions();
    int action = scanner.nextInt();
    scanner.nextLine();

    try {
      switch (action) {
      case 0:
        user.showTickets();
        showUserActions();
        break;
      case 1:
        showNextMovie();
        break;
      case 2:
        buyTicket();
        break;
      case 3:
        addCinema();
        break;
      case 4:
        addCinemaHall();
        break;
      case 5:
        addMovie();
        break;
      default:
        showUserActions();
      }
    }
    catch (Error e) {
      System.out.println(e.getMessage());
      showUserActions();
    }
  }

  public static void showNextMovie() {
    MovieSession currentMovie = new MovieSession(new Date(0), 0, "");
    int cinemaId = -1;
    int hallId = -1;

    for (int i = 0; i < cinemas.size(); i++) {
      Cinema cinema = cinemas.get(i);
      for (int j = 0; j < cinema.getHallsCount(); j++) {
        CinemaHall hall = cinema.getHall(j);
        MovieSession movie = hall.getNextMovie();

        if (movie != null) {
          if (movie.movieStart.before(currentMovie.movieStart) || currentMovie.movieStart.getTime() == 0) {
            currentMovie = movie;
            cinemaId = i;
            hallId = j;
          }
        }
      }
    }

    if (cinemaId == -1) {
      System.out.println("Ближайшего сеанса не найдено");
      showUserActions();
      return;
    }

    System.out.println("Ближайший сеанс со свободными местами:");
    System.out.println(currentMovie.movieTitle);
    System.out.printf("Время начала %s, длительность %d минут\n", formatter.format(currentMovie.movieStart.getTime()), currentMovie.movieLength);
    System.out.printf("Кинотеатр %d, зал %d \n", cinemaId, hallId);
    showUserActions();
  }

  public static void buyTicket() {
    Cinema cinema = getCinema();
    
    cinema.showHalls();
    System.out.println("Введите номер кинозала");
    int hallId = scanner.nextInt();
    scanner.nextLine();

    CinemaHall hall;
    try {
      hall = cinema.getHall(hallId);
    }
    catch (Error e) {
      System.out.println(e.getMessage());
      showUserActions();
      return;
    }

    hall.showMovies(formatter);

    System.out.println("Введите название интересующего фильма:");
    String movieTitle = scanner.nextLine();

    MovieSession movie = hall.getMovie(movieTitle);
    movie.showSeats();

    System.out.println("Введите номер строки и столбца нужного кресла:");
    int row = scanner.nextInt();
    scanner.nextLine();
    int column = scanner.nextInt();
    scanner.nextLine();

    try {
      movie.bookSeat(row, column);
      user.buyTicket(cinemas.indexOf(cinema), hallId, movieTitle, row, column);
      user.showTickets();
      showUserActions();
    }
    catch (Error e) {
      System.out.println(e.getMessage());
      showUserActions();
      return;
    }
  }

  public static void addCinema() {
    if (!user.getModifyPermission()) throw new Error("Вы не обладаете необходимыми правами для этого действия");

    Cinema newCinema = new Cinema();
    cinemas.add(newCinema);
    
    System.out.printf("Номер нового кинотеатра - %d \n\n", cinemas.size() - 1);
    showUserActions();
  }

  public static void addCinemaHall() {
    if (!user.getModifyPermission()) throw new Error("Вы не обладаете необходимыми правами для этого действия");

    System.out.println("Введите количество рядов:");
    int row = scanner.nextInt();
    scanner.nextLine();
    System.out.println("Введите количество сидений в 1 ряду:");
    int column = scanner.nextInt();
    scanner.nextLine();

    CinemaHall newCinemaHall = new CinemaHall(row, column);

    Cinema cinema;
    try {
      cinema = getCinema();
    }
    catch (Error e) {
      System.out.println(e.getMessage());
      showUserActions();
      return;
    }

    cinema.addCinemaHall(newCinemaHall);
    System.out.printf("Номер нового зала - %d \n\n", cinema.getHallsCount() - 1);
    showUserActions();
  }

  public static void addMovie() {
    if (!user.getModifyPermission()) throw new Error("Вы не обладаете необходимыми правами для этого действия");

    Cinema cinema;
    try {
      cinema = getCinema();
    }
    catch (Error e) {
      System.out.println(e.getMessage());
      showUserActions();
      return;
    }

    cinema.showHalls();
    System.out.println("Введите номер кинозала");
    int hallId = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Введите название фильма");
    String title = scanner.nextLine();

    System.out.println("Введите дату (пример: 2018-11-30 16:30)");
    String stringDate = scanner.nextLine();

    System.out.println("Введите продолжительность фильма в минутах");
    int length = scanner.nextInt();
    scanner.nextLine();

    Date date;
    try {
      date = formatter.parse(stringDate);
    }
    catch (ParseException e) {
      System.out.println("Произошла ошибка при форматировании даты");
      showUserActions();
      return;
    }

    MovieSession movie = new MovieSession(date, length, title);

    try {
      cinema.addMovie(hallId, movie);
    }
    catch (Error e) {
      System.out.println(e.getMessage());
      showUserActions();
      return;
    }

    showUserActions();
  }

  public static void showCinemas() {
    if (cinemas.size() == 0) {
      System.out.println("На данный момент нет доступных кинотеатров");
      showUserActions();
      return;
    }

    System.out.println("Номера доступных кинотеатров:");
    for (int i = 0; i < cinemas.size(); i++) {
      System.out.printf("%d ", i);
    }
    System.out.println("");
  }

  public static Cinema getCinema() {
    showCinemas();
    System.out.println("Введите номер кинотеатра");
    int cinemaId = scanner.nextInt();
    scanner.nextLine();

    Cinema cinema;
    try {
      cinema = cinemas.get(cinemaId);
    }
    catch (IndexOutOfBoundsException e) {
      throw new Error("Такого кинотеатра не существует");
    }

    return cinema;
  }

  public static void initDefaultCinemas() throws ParseException {
    for (int i = 0; i <= 1; i++) {
      Cinema cinema = new Cinema();
      for (int j = 0; j <= 1; j++) {
        CinemaHall hall = new CinemaHall(5, 5);
        cinema.addCinemaHall(hall);
      }
      cinemas.add(cinema);
    }

    MovieSession firstMovie = new MovieSession(formatter.parse("2023-10-05 15:30"), 120, "Звездные войны");
    MovieSession secondMovie = new MovieSession(formatter.parse("2023-10-10 10:30"), 60, "Гарри Поттер");
    MovieSession thirdMovie = new MovieSession(formatter.parse("2023-10-05 09:00"), 120, "Матрица");

    cinemas.get(0).getHall(0).addMovie(firstMovie);
    cinemas.get(0).getHall(0).addMovie(thirdMovie);
    cinemas.get(1).getHall(0).addMovie(firstMovie);
    cinemas.get(1).getHall(0).addMovie(secondMovie);
    cinemas.get(1).getHall(1).addMovie(secondMovie);
  }
}