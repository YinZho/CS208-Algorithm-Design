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


  static int[] letterIds = new int[256];

  static InputReader in;
  static PrintWriter out;

  public static void main(String[] args) throws IOException {
    in = new InputReader(System.in);
    out = new PrintWriter(System.out);

    char[] str = "ABCDE".toCharArray();
    for (int i = 0; i < str.length; i++) {
      letterIds[str[i]] = i;
    }

    int n = in.nextInt();
    int m = in.nextInt();
    int k = in.nextInt();
    String S = in.next();
    String T = in.next();
    long[][] text = new long[5][n];
    long[][] pattern = new long[5][m];
    int[][] dels = new int[5][n];
    for (int i = 0; i < n; i++) {
      char c = S.charAt(i);
      int id = letterIds[c];
      int start = Math.max(0, i - k);
      int end = i + k + 1;
      dels[id][start] += 1;
      if (end < n) {
        dels[id][end] -= 1;
      }
    }
    int[] curDels = new int[5];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < 5; j++) {
        curDels[j] += dels[j][i];
        if (curDels[j] > 0) {
          text[j][i] = 1;
        }
      }
    }
    for (int i = 0; i < m; i++) {
      char c = T.charAt(i);
      int id = letterIds[c];
      pattern[id][m - 1 - i] = 1;
    }
    long[][] polys = new long[5][];
    for (int i = 0; i < 5; i++) {
      polys[i] = FFT.mult(text[i], pattern[i]);
    }
    long ans = 0;
    for (int i = 0; i < polys[0].length; i++) {
      long cnt = 0;
      for (int j = 0; j < 5; j++) {
        cnt += polys[j][i];
      }
      if (cnt == m) {
        ans++;
      }
    }
    out.println(ans);
    out.close();
  }

  static class FFT {

    static int max = (1 << 21) + 1;
    static double[] ws_r = new double[max];
    static double[] ws_i = new double[max];
    static int[] dp = new int[max];
    static int n, k;
    static int lastk = -1;

    static void fft(boolean rev) {
      if (lastk != k) {
        lastk = k;
        dp[0] = 0;

        for (int i = 1, g = -1; i < n; ++i) {
          if ((i & (i - 1)) == 0) {
            ++g;
          }
          dp[i] = dp[i ^ (1 << g)] ^ (1 << (k - 1 - g));
        }

        ws_r[1] = 1;
        ws_i[1] = 0;
        for (int i = 0; i < k - 1; ++i) {
          double alf = Math.PI / n * (1 << (k - 1 - i));
          double cur_r = Math.cos(alf), cur_i = Math.sin(alf);

          int p2 = (1 << i), p3 = p2 * 2;
          for (int j = p2; j < p3; ++j) {
            ws_r[j * 2] = ws_r[j];
            ws_i[j * 2] = ws_i[j];

            ws_r[j * 2 + 1] = ws_r[j] * cur_r - ws_i[j] * cur_i;
            ws_i[j * 2 + 1] = ws_r[j] * cur_i + ws_i[j] * cur_r;
          }
        }
      }
      for (int i = 0; i < n; ++i) {
        if (i < dp[i]) {
          double ar = a_r[i];
          double ai = a_i[i];
          a_r[i] = a_r[dp[i]];
          a_i[i] = a_i[dp[i]];
          a_r[dp[i]] = ar;
          a_i[dp[i]] = ai;

        }
      }
      if (rev) {
        for (int i = 0; i < n; ++i) {
          a_i[i] = -a_i[i];
        }
      }
      for (int len = 1; len < n; len <<= 1) {
        for (int i = 0; i < n; i += len) {
          int wit = len;
          for (int it = 0, j = i + len; it < len; ++it, ++i, ++j) {
            double tmp_r = a_r[j] * ws_r[wit] - a_i[j] * ws_i[wit];
            double tmp_i = a_r[j] * ws_i[wit] + a_i[j] * ws_r[wit];
            wit++;
            a_r[j] = a_r[i] - tmp_r;
            a_i[j] = a_i[i] - tmp_i;
            a_r[i] += tmp_r;
            a_i[i] += tmp_i;
          }
        }
      }
    }

    static double[] a_r = new double[max];
    static double[] a_i = new double[max];

    static long[] mult(long[] arr_a, long[] arr_b) {
      int na = arr_a.length, nb = arr_b.length;

      n = 1;
      k = 0;
      while (n < na + nb - 1) {
        n <<= 1;
        ++k;
      }

      for (int i = 0; i < n; ++i) {
        if (i < na){
          a_r[i] = arr_a[i];
        }else {
          a_r[i] = 0;
        }
        if (i < nb){
          a_i[i] = arr_b[i];
        }else {
          a_i[i] = 0;
        }
      }
      fft(false);
      a_r[n] = a_r[0];
      a_i[n] = a_i[0];
      double r_i = -1.0 / (n * 4.0);
      for (int i = 0; i <= n - i; ++i) {
        double tmp_r = a_r[i] * a_r[i] - a_i[i] * a_i[i];
        double tmp_i = a_r[i] * a_i[i] * 2.0;
        double tmp2_r = a_r[n - i] * a_r[n - i] - a_i[n - i] * a_i[n - i];
        double tmp2_i = a_r[n - i] * a_i[n - i] * -2.0;
        tmp_r = tmp_r - tmp2_r;
        tmp_i = tmp_i - tmp2_i;
        a_r[i] = -tmp_i * r_i;
        a_i[i] = tmp_r * r_i;

        a_r[n - i] = a_r[i];
        a_i[n - i] = -a_i[i];
      }
      fft(true);
      int res = 0;
      long[] ans = new long[max];
      for (int i = 0; i < n; ++i) {
        long val = (long) Math.round(a_r[i]);
        if (val != 0) {
          while (res < i) {
            ans[res++] = 0;
          }
          ans[res++] = val;
        }
      }
      return ans;
    }
  }
}