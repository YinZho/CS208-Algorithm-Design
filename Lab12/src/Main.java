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
  static int N;
  static int M;
  static int K;
  static int[][] chain;
  static int[][][] right;
  static int[][][] DyeOnce;
  static int[][] sum;
  static int[][] OPT;

  public static void ReadData() {

    N = in.nextInt();
    M = in.nextInt();
    K = in.nextInt();

    chain = new int[52][52];
    sum = new int[52][52];
    right = new int[52][2502][52];
    DyeOnce = new int[52][52][52];
    OPT = new int[52][2502];

    if (N * M <= K) {
      System.out.println(0);
    } else {
      for (int i = 1; i <= N; i++) {
        for (int j = 1; j <= M; j++) {
          chain[i][j] = in.nextInt();
          sum[i][j] = sum[i][j - 1] + chain[i][j];
        }
      }

      PartI();
      PartII();
      System.out.println(N * M - OPT[N][K]);
    }

  }

  public static void PartI() {
    for (int i = 1; i <= N; i++) {
      for (int j = 0; j < M; j++) {
        for (int k = 0; k <= j; k++) {
          int blue = sum[i][j + 1] - sum[i][k] - (j + 1 - k);
          int red = 2 * (j + 1 - k) - (sum[i][j + 1] - sum[i][k]);
          DyeOnce[i][k + 1][j + 1] = Math.max(blue, red);
        }
      }
    }

    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= K; j++) {
        for (int k = 0; k <= M; k++) {
          for (int l = 0; l <= k; l++) {
            if (right[i][j][k + 1] < right[i][j - 1][l] + DyeOnce[i][l + 1][k + 1]) {
              right[i][j][k + 1] = right[i][j - 1][l] + DyeOnce[i][l + 1][k + 1];
            }
          }
        }
      }
    }
  }

  public static void PartII() {
    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= K; j++) {
        OPT[i][j] = right[i][j][M];
      }
    }
    for (int k = 2; k <= N; k++) {
      for (int w = 0; w <= K; w++) {
        OPT[k][w] = OPT[k - 1][w];
        for (int i = 1; i < w; i++) {
          if (OPT[k][w] < OPT[k - 1][i] + right[k][w - i][M]) {
            OPT[k][w] = OPT[k - 1][i] + right[k][w - i][M];
          }
        }
      }
    }

  }


  public static void main(String args[]) throws IOException {
    out = new PrintWriter(System.out);
    in = new InputReader(System.in);
    ReadData();
    out.close();
  }

}