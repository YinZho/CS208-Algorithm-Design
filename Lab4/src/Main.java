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

  static int n;
  static int m;
  static int k;

  static int[] FlightTime;
  static int[] DownNum;
  static int[][] ArriveAndLeaveTime;
  static int[] InfluNum;
  static int[] Latest;

  static int TotalTime;

  public static void ReadData() {
    n = in.nextInt();
    m = in.nextInt();
    k = in.nextInt();

    FlightTime = new int[n];
    DownNum = new int[n + 1];
    ArriveAndLeaveTime = new int[2][n + 1];
    InfluNum = new int[n];
    Latest = new int[n];

    int startTime = 0;

    for (int i = 1; i < n; i++) {
      FlightTime[i] = in.nextInt();
    }

    for (int i = 0; i < m; i++) {
      int d = in.nextInt();
      int a = in.nextInt();
      int b = in.nextInt();

      startTime += d;

      if (d > Latest[a]) {
        Latest[a] = d;
      }
      DownNum[b]++;
    }

    CountArriveAndLeaveTime();

    for (int i = 1; i <= n; i++) {
      if (DownNum[i] > 0) {
        TotalTime += DownNum[i] * ArriveAndLeaveTime[0][i];
      }
    }

    TotalTime -= startTime;

  }

  public static void CountArriveAndLeaveTime() {
    for (int i = 1; i <= n; i++) {
      ArriveAndLeaveTime[0][i] = ArriveAndLeaveTime[1][i - 1] + FlightTime[i - 1];

      if (i < n) {
        ArriveAndLeaveTime[1][i] = Math.max(ArriveAndLeaveTime[0][i], Latest[i]);
      }
    }
  }

  public static void CountInfluNum() {
    InfluNum[n - 1] = DownNum[n];
    for (int i = n - 2; i > 0; i--) {
      InfluNum[i] = DownNum[i + 1];
      if (Latest[i + 1] < ArriveAndLeaveTime[0][i + 1]) {
        InfluNum[i] += InfluNum[i + 1];
      }
    }
  }

  public static void Refresh(int i) {
    FlightTime[i]--;
    ArriveAndLeaveTime[0][i + 1]--;
    CountArriveAndLeaveTime();
  }

  public static int CountMinTime() {
    for (int i = 0; i < k; i++) {
      CountInfluNum();
      int c = 0;
      int num = InfluNum[0];
      for (int j = 1; j < n; j++) {
        if (InfluNum[j] > num && FlightTime[j] > 0) {
          num = InfluNum[j];
          c = j;
        }
      }

      if (c == 0) {
        break;
      }

      Refresh(c);
      TotalTime -= InfluNum[c];
    }
    return TotalTime;
  }

  public static void main(String args[]) throws IOException {
    out = new PrintWriter(System.out);
    in = new InputReader(System.in);

    ReadData();
    CountMinTime();

    System.out.println(TotalTime);
  }

}