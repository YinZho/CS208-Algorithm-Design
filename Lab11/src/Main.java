import java.io.*;
import java.util.*;

class InputReader {

  public BufferedReader br;
  public StringTokenizer tokenizer;

  public InputReader(InputStream stream) throws FileNotFoundException {
    br = new BufferedReader(new InputStreamReader(stream), 327680);
    tokenizer = null;
  }

  public String next() {
    while (tokenizer == null || !tokenizer.hasMoreTokens()) {
      try {
        tokenizer = new StringTokenizer(br.readLine());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return tokenizer.nextToken();
  }

  public int nextInt() {
    try {
      int c = br.read();
      while (c <= 32) {
        c = br.read();
      }
      boolean negative = false;
      if (c == '-') {
        negative = true;
        c = br.read();
      }
      int x = 0;
      while (c > 32) {
        x = x * 10 + c - '0';
        c = br.read();
      }
      return negative ? -x : x;
    } catch (IOException e) {
      return -1;
    }
  }

  public long nextLong() {
    try {
      int c = br.read();
      while (c <= 32) {
        c = br.read();
      }
      boolean negative = false;
      if (c == '-') {
        negative = true;
        c = br.read();
      }
      long x = 0;
      while (c > 32) {
        x = x * 10 + c - '0';
        c = br.read();
      }
      return negative ? -x : x;
    } catch (IOException e) {
      return -1;
    }
  }

}


public class Main {

  static PrintWriter out;
  static InputReader in;
  static int sumPoint_old;
  static int sumPoint_new;
  static int max;
  static int n;
  static int[][] sum;


  public static void readData() {
    sum = new int[2001][2001];
    sum[0][0] = 1;
    n = in.nextInt();
    int num;
    sumPoint_old = 0;
    for (int i = 0; i < n; i++) {

      num = in.nextInt();
      sumPoint_new += num;

      for (int j = 0; j <= sumPoint_old; j++) {
        for (int k = sumPoint_new; k >= num ; k--) {
          if (sum[j][k-num] == 1)
            sum[j][k] = 1;
        }
      }

      for (int j = 0; j <= sumPoint_new; j++) {
        for (int k = 0; k <= sumPoint_new; k++) {
          if (sum[k][j] == 1)
            sum[j][k] = 1;
          if (j == k && sum[j][k] == 1 && j > max)
            max = j;
        }
      }

      sumPoint_old = sumPoint_new;

    }

    if (max == 0) {
      out.println("Go back to school.");
    } else {
      out.println(max);
    }
  }


  public static void main(String args[]) throws IOException {
    out = new PrintWriter(System.out);
    in = new InputReader(System.in);
    readData();
    out.close();
  }

}