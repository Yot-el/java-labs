import java.util.Scanner;

public class Submatrix {
  static int[][] matrix;

  public static void main (String[] args) {
    getMatrix();
    
    int maxSumm = 0;
    int[] matrixIndexes = {0, 0, 0, 0};

    for (int i = 0; i < matrix[0].length; i++) {
        for (int j = 0; j < matrix.length; j++) {
          for (int k = i; k < matrix[0].length; k++) { // Элементы from i to k Строки
            for (int l = j; l < matrix.length; l++) { // Элементы from j to l Столбцы
              int matrixElementSumm = getSumm(i, k, j, l);
              
              if (maxSumm < matrixElementSumm) {
                maxSumm = matrixElementSumm;
                matrixIndexes[0] = i;
                matrixIndexes[1] = k;
                matrixIndexes[2] = j;
                matrixIndexes[3] = l;
              }
            }
          }
        }
      } 
    // Индексы матрицы i k j l
    System.out.format("Индексы подматрицы: строка от %d до %d, столбец от %d до %d", matrixIndexes[0], matrixIndexes[1], matrixIndexes[2], matrixIndexes[3]);
    System.out.println("\nСумма " + maxSumm);
  }

  public static int getSumm(int i, int k, int j, int l) {
    int result = 0;

    for (int row = i; row <= k; row++) {
      for (int column = j; column <= l; column++) {
        result += matrix[column][row];
      }
    }

    return result;
  }

  public static void getMatrix() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Введите кол-во элементов в строке");
    int row = scanner.nextInt();
    scanner.nextLine();
    System.out.println("Введите кол-во элементов в столбце");
    int column = scanner.nextInt();
    scanner.nextLine();

    int[][] newMatrix = new int[column][row];

    System.out.println("Матрица заполняется в порядке строк");
    for (int i = 0; i < column; i++) {
      for (int j = 0; j < row; j++) {
        int n = scanner.nextInt();
        scanner.nextLine();

        newMatrix[i][j] = n;
      }
    }

    matrix = newMatrix;

    scanner.close();
  }
}
