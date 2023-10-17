package FirstLab;
import java.util.Scanner;

public class FirstLab {
    public static void main (String[] args) {
        // firstTask();
        // secondTask();
        // fourthTask(); 
        // fifthTask();
    }

    public static void firstTask() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.next());

        int steps = 0;

        while (n != 1) {
            if (n % 2 == 0) {
                n = n / 2;
            }
            else {
                n = 3*n + 1;
            }
            steps += 1;
        }

        System.out.println(steps);
        scanner.close();
    }

    public static void secondTask() {
        Scanner scanner = new Scanner(System.in);
        int result = 0;

        int integerCount = Integer.parseInt(scanner.next());

        for (int i = 0; i < integerCount; i++) {
            if (i % 2 == 0) {
                result += Integer.parseInt(scanner.next());
            }
            else {
                result -= Integer.parseInt(scanner.next());
            }
        }

        System.out.println(result);
        scanner.close();
    }

    public static void thirdTask() {
    }

    public static void fourthTask() {
        Scanner scanner = new Scanner(System.in);

        int roadsCount = Integer.parseInt(scanner.next());
        int result = 0;
        int roadIndex = 0;

        for (int i = 1; i <= roadsCount; i++) {
            int tunnelsCount = Integer.parseInt(scanner.next());
            int maxHeight = (int) Double.POSITIVE_INFINITY;

            for (int j = 1; j <= tunnelsCount; j++) {
                int currentHeight = Integer.parseInt(scanner.next());
                maxHeight = currentHeight < maxHeight ? currentHeight : maxHeight; 
            }

            if (maxHeight > result) {
                result = maxHeight;
                roadIndex = i;
            }
        }

        System.out.printf("%d %d", roadIndex, result);
        scanner.close();
    }

    public static void fifthTask() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.next());
        int digitsSumm = 0;
        int digitsMul = 1;

        for (int i = 0; i < 3; i++) {
            int digit = n % 10;
            n = n / 10;
            digitsSumm += digit;
            digitsMul *= digit;
        }

        if (digitsMul % 2 == 0 && digitsSumm % 2 == 0) {
            System.out.println("Является дважды четным");
        }
        else {
            System.out.println("Не является дважды четным");
        }

        scanner.close();
    }
}