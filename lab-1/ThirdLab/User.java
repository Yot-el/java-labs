package ThirdLab;

import java.util.ArrayList;

public abstract class User {
  private boolean modifyPermission;
  private ArrayList<Ticket> tickets = new ArrayList<Ticket>();

  public User(boolean modifyPermission) {
    this.modifyPermission = modifyPermission;
  }

  public boolean getModifyPermission() {
    return this.modifyPermission;
  }

  public void showActions() {
    System.out.println("Выберите действие:");
    System.out.println("0: Посмотреть купленные билеты");
    System.out.println("1: Посмотреть ближайший сеанс");
    System.out.println("2: Купить билет");
  };

  public void buyTicket(int cinemaId, int hallId, String movieTitle, int row, int column) {
    Ticket ticket = new Ticket(cinemaId, hallId, movieTitle, row, column);
    tickets.add(ticket);
  }

  public void showTickets() {
    if (tickets.size() == 0) {
      System.out.println("У вас нет купленных билетов");
      return;
    }

    System.out.println("Ваши купленные билеты:");
    for (Ticket ticket : tickets) {
      System.out.printf("Название: %s, кинотеатр %d, зал %d \n", ticket.getMovieTitle(), ticket.getCinema(), ticket.getHall());
    }
  }

  public void showNextMovie(ArrayList<Cinema> cinemas) {

  }
}


