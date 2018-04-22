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

class Region implements Comparable<Region> {

  public int left;
  public int right;

  public Region(int l, int r) {
    left = l;
    right = r;
  }

  @Override
  public int compareTo(Region region) {
    int value = this.right - region.right;
    if (value > 0) {
      return 1;
    }
    if (value < 0) {
      return -1;
    }
    return this.left - region.left;

  }
}


public class Main {

  static PrintWriter out;
  static InputReader in;
  static int n;
  static int officeHour;
  static ArrayList<Region> arrayList;

  public static void ReadData() {
    n = in.nextInt();
    for (int i = 0; i < n; i++) {
      arrayList.add(new Region(in.nextInt(), in.nextInt()));
    }
  }

  public static void MinTime() {
    arrayList = new ArrayList<Region>();

    ReadData();

    Collections.sort(arrayList);

    Iterator<Region> iterator = arrayList.iterator();

    Region region = iterator.next();
    int firstMax = region.right;
    int secondMax = firstMax - 1;
    officeHour = 2;

    while (iterator.hasNext()) {
      region = iterator.next();
      if (firstMax < region.left) {
        officeHour += 2;
        firstMax = region.right;
        secondMax = firstMax - 1;
      } else if (secondMax < region.left) {
        officeHour += 1;
        secondMax = firstMax;
        firstMax = region.right;
      }
    }

    System.out.println(officeHour);

  }

  public static void main(String args[]) throws IOException {
    out = new PrintWriter(System.out);
    in = new InputReader(System.in);

    MinTime();

    out.close();
  }

}