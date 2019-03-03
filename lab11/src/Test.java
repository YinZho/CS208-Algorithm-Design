import java.util.Random;

public class Test {

  public static void main(String[] args) {
    Random random = new Random();
    int n = random.nextInt(50);
    System.out.println(n);
    for (int i = 0; i < n; i++) {
      System.out.print(random.nextInt(105)+1 + " ");
    }
  }
}
