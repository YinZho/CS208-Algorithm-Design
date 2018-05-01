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

class HuffmanNode implements Comparable<HuffmanNode> {

  int value;
  int frequency;

  HuffmanNode left;
  HuffmanNode right;

  public HuffmanNode(int v, int f) {
    this.value = v;
    this.frequency = f;
    this.left = null;
    this.right = null;
  }

  @Override
  public int compareTo(HuffmanNode hn) {
    return this.frequency - hn.frequency;
  }
}

public class Main {

  static PrintWriter out;
  static InputReader in;
  static int[] bytes;
  static int[] deep;

  public static void readData() {
    bytes = new int[256];

    int num = in.nextInt();
    while (num != -1) {
      bytes[num]++;
      num = in.nextInt();
    }
  }

  public static int huffmanCoding() {
    double ans = 0;

    PriorityQueue<HuffmanNode> hn_pq = new PriorityQueue<>();
    for (int i = 0; i < bytes.length; i++) {
      if (bytes[i] != 0) {
        hn_pq.add(new HuffmanNode(i, bytes[i]));
      }
    }

    HuffmanNode root = null;

    while (hn_pq.size() > 1) {

      HuffmanNode x = hn_pq.poll();
      HuffmanNode y = hn_pq.poll();

      HuffmanNode f = new HuffmanNode(-1, x.frequency + y.frequency);
      f.left = x;
      f.right = y;

      root = f;

      hn_pq.add(f);
    }

    deep = new int[256];

    Compression(root, 0);

    for (int i = 0; i < bytes.length; i++) {
      if (bytes[i] != 0) {
        ans += bytes[i] * deep[i];
      }
    }

    return (int) Math.ceil(ans / 8);
  }

  public static void Compression(HuffmanNode root, int count) {

    if (root.left == null && root.right == null && root.value >= 0) {
      deep[root.value] = count;
      return;
    }

    Compression(root.left, count + 1);
    Compression(root.right, count + 1);
  }


  public static void main(String args[]) throws IOException {
    out = new PrintWriter(System.out);
    in = new InputReader(System.in);

    readData();
    System.out.println(huffmanCoding());

    out.close();
  }

}