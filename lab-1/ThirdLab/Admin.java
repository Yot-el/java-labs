package ThirdLab;

public class Admin extends User {
  public Admin() {
    super(true);
  }

  @Override
  public void showActions() {
    System.out.println("Выберите действие:");
    System.out.println("1: Посмотреть ближайший сеанс со свободными местами");
    System.out.println("2: Купить билет");
    System.out.println("3: Добавить кинотеатр");
    System.out.println("4: Добавить зал в кинотеатр");
    System.out.println("5: Cоздать сеанс фильма");
  };
}
