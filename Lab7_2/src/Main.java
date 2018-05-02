import java.io.*;
import java.sql.SQLOutput;
import java.util.*;
import java.util.logging.SimpleFormatter;

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

}


public class Main {

  static PrintWriter out;
  static InputReader in;
  static HashSet<String> hm;

  public static void readData() {
    StringBuilder sb = new StringBuilder();
    int num = in.nextInt();

    while (num != -1) {
      String str = Integer.toBinaryString(num);
      for (int i = 0; i < 8 - str.length(); i++) {
        sb.append(0);
      }
      sb.append(str);
      num = in.nextInt();
    }

    StringBuilder tail = dealSB(sb);
    int length = Integer.parseInt(tail.substring(0, 32), 2);
    int start = 32;
    int end = 33;

    int count = 0;

    while (true) {

      while (!hm.contains(tail.substring(start, end).toString())) {
        end++;
      }
      count++;
      start = end;
      end = start + 1;

      if (end >= 32 + length) {
        break;
      }

    }

    System.out.println(count);
  }


  public static StringBuilder dealSB(StringBuilder sb) {
    int cnt = 0;
    hm = new HashSet<>();
    StringBuilder tail = new StringBuilder();

    for (int i = 0; i < sb.length(); ) {
      String str = sb.substring(i, i + 8);
      i = i + 8;
      cnt++;

      int length = Integer.parseInt(str.toString(), 2);

      String key = sb.substring(i, i + length);
      i += length;

      hm.add(key);

      if (cnt == 256) {
        tail.append(sb.substring(i, sb.length() - 1));
        break;
      }
    }
    return tail;
  }


  public static void main(String args[]) throws IOException {
    out = new PrintWriter(System.out);
    in = new InputReader(System.in);
    readData();
    out.close();
  }

}