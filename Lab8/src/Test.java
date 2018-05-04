import java.util.Random;
import java.util.Set;

public class Test {

  public static void main(String[] args) {
    Random random = new Random();
    int n = random.nextInt(100000) + 1;
    int[] arr = GetRandomSequence0(n);
    for (int i = 0; i < n; i++) {
      System.out.print(arr[i] + " ");
    }
  }

  public static int[] GetRandomSequence0(int total) {
    int[] hashtable = new int[total];
    int[] output = new int[total];

    Random random = new Random();
    for (int i = 0; i < total; i++) {
      int num = random.nextInt(total);
      while (hashtable[num] > 0) {
        num = random.nextInt(total);
      }

      output[i] = num;
      hashtable[num] = 1;
    }

    return output;
  }
}
