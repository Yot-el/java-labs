import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class Anagram {
  public static void main (String[] args) {
    Scanner scanner = new Scanner(System.in);

    String firstWord = scanner.next().trim();
    String secondWord = scanner.next().trim();

    boolean isAnagram = checkAnagram(firstWord, secondWord);

    if (isAnagram) {
      System.out.println("Являются анаграммой");
    }
    else {
      System.out.println("Не являются анаграммой");
    }

    scanner.close();
  }

  public static boolean checkAnagram(String firstWord, String secondWord) {
    Map<Character, Integer> firstLetterMap = new HashMap<Character, Integer>();
    Map<Character, Integer> secondLetterMap = new HashMap<Character, Integer>();

    for (char element : firstWord.toCharArray()){
      if (firstLetterMap.get(element) == null) {
        firstLetterMap.put(element, 1);
      }
      else {
        firstLetterMap.put(element, firstLetterMap.get(element) + 1);
      }
    }

    for (char element : secondWord.toCharArray()){
      if (secondLetterMap.get(element) == null) {
        secondLetterMap.put(element, 1);
      }
      else {
        secondLetterMap.put(element, secondLetterMap.get(element) + 1);
      }
    }

    for (Entry<Character, Integer> firstLetterSet : firstLetterMap.entrySet()) {
      char key = firstLetterSet.getKey();
      if (secondLetterMap.get(key) == null || firstLetterSet.getValue() != secondLetterMap.get(key)) {
        return false;
      }
    }

    return true;
  }
}
