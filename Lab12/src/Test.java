import java.util.Random;

public class Test {

  public static void main(String[] args) {
    Random random = new Random();
    int n = random.nextInt(50)+1;
    int m = random.nextInt(50)+1;
    int k = random.nextInt(2500) + 1;
    while (k > n*m){
      k = random.nextInt(2500) + 1;
    }
    System.out.printf("%d %d %d\n",n, m ,k);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        System.out.print(random.nextInt(2)+1 + " ");
      }
      System.out.println();
    }
  }
}
