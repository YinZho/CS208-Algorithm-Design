import java.util.Random;

public class Test {

  public static void main(String[] args) {
    Random random = new Random();
    int degree = random.nextInt(3) + 1;
    int n = random.nextInt(1000) + 1;
    System.out.println(degree);
    System.out.println(n);
    for (int i = 0; i < n; i++) {
      switch (degree) {
        case 1:
          System.out.println(random.nextInt((int) Math.pow(10, 5)));
          break;
        case 2:
          System.out.println(
              random.nextInt((int) Math.pow(10, 5)) + ";" + random.nextInt((int) Math.pow(10, 5)));
          break;
          case 3:
          System.out.println(random.nextInt((int) Math.pow(10, 5)) + ";" + random
              .nextInt((int) Math.pow(10, 5)) + ";" + random.nextInt((int) Math.pow(10, 5)));


      }
    }
  }
}
