package SecondLab;
import java.util.Arrays;
import java.util.stream.IntStream;

public class SecondLab {
  public static void main (String[] args) {
    // 1
    findMaxSubString("abccabc");

    // 2
    int[] firstArr = {1, 2, 4, 10};
    int[] secondArr = {0, 3, 5, 6};
    int[] result = ArraysJoin(firstArr, secondArr);
    System.out.println(Arrays.toString(result));

    // 3
    int[] arr = {-1, 0, 3, 1, 0, -5, 10, 15};
    System.out.println(maxSubArraySum(arr));

    // 4
    int[][] tArr = {{9, 8, 7, 6}, {3, 2, 1, 0}};
    int[][] newTArr = rotateArray(tArr);
    for (int[] row : newTArr) {
      System.out.println(Arrays.toString(row));
    }

    // 5
    System.out.println(Arrays.toString(findPair(arr, -6)));

    // 6
    System.out.println(findSumm(tArr));

    // 7
    System.out.println(Arrays.toString(findMax(newTArr)));

    // 8
    int[][] newTTArr = rotateArray2(tArr);
    for (int[] row : newTTArr) {
      System.out.println(Arrays.toString(row));
    }
  }

  public static void findMaxSubString(String string) {
    System.out.println("1 задание");
    char[] charArr = string.toCharArray();
    String result = "";
    String current = "";

    for (char letter : charArr) {
      if (current.isEmpty() || !(current.charAt(current.length() - 1) == letter)) {
        current += letter;
      }
      else {
        result = current.length() > result.length() ? current : result;
        current = "" + letter;
      }
    }

    result = current.length() > result.length() ? current : result;

    System.out.println(result);
  }

  public static int[] ArraysJoin(int[] firstArr, int[] secondArr) {
    System.out.println("2 задание");
    int[] newArr = IntStream.concat(IntStream.of(firstArr), IntStream.of(secondArr)).toArray();
    Arrays.sort(newArr);
    return newArr;
  }

  public static int maxSubArraySum(int[] arr) {
    System.out.println("3 задание");
    int result = 0;
    int current = 0;

    for (int elem: arr) {
      if (current + elem >= current) {
        current += elem;
      }
      else {
        result = current > result ? current : result;
        current = 0;
      }
    }

    result = current > result ? current : result;
    return result;
  }

  public static int[][] rotateArray(int[][] arr) {
    System.out.println("4 задание");
    int[][] newArr = new int[arr[0].length][arr.length];

    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        newArr[j][i] = arr[i][j];
      }
    }

    for (int i = 0; i < newArr.length; i++) {
      for (int j = 0; j < newArr[i].length / 2; j++) {
        int temp = newArr[i][j];
        newArr[i][j] = newArr[i][newArr[i].length - 1 - j];
        newArr[i][newArr[i].length - 1 - j] = temp;
      }
    }

    return newArr;
  }

  public static int[] findPair(int[] arr, int target) {
    System.out.println("5 задание");
    int i = 0;
    int j = arr.length - 1;
    Arrays.sort(arr);

    while (i < j) {
      if (arr[i] + arr[j] == target) {
        int[] result = {arr[i], arr[j]};
        return result;
      }
      else if (arr[i] + arr[j] < target) {
        i += 1;
      }
      else {
        j -= 1;
      }
    }

    return null;
  }

  public static int findSumm(int[][] arr) {
    System.out.println("6 задание");
    int result = 0;

    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        result += arr[i][j];
      }
    }

    return result;
  }

  public static int[] findMax(int[][] arr) {
    System.out.println("7 задание");

    int[] result = new int[arr.length];
    for (int i = 0; i < arr.length; i++) {
      int max = arr[i][0];

      for (int j = 0; j < arr[i].length; j++) {
        if (max < arr[i][j]) {
          max = arr[i][j];
        }
      }

      result[i] = max;
    }

    return result;
  }

  public static int[][] rotateArray2(int[][] arr) {
    System.out.println("8 задание");
    int[][] newArr = new int[arr[0].length][arr.length];

    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        newArr[j][i] = arr[i][j];
      }

      for (int k = 0; k < newArr[k].length; k++) {
        int temp = newArr[k][i];
        newArr[k][i] = newArr[newArr.length - 1 - k][i];
        newArr[newArr.length - 1 - k][i] = temp;
      }
    }

    return newArr;
  }
}
