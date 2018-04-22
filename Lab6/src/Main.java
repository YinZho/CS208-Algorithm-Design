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

class Vertex {

  int degree;
  int water_tissue;

  public Vertex(int w) {
    this.water_tissue = w;
  }
}

class Road implements Comparable<Road> {

  int src;
  int dest;
  int length;

  public Road(int r1, int r2, int l) {
    this.src = r1;
    this.dest = r2;
    this.length = l;
  }

  public int compareTo(Road r) {
    return this.length - r.length;
  }
}

class subset {

  public subset(int p, int r) {
    this.parent = p;
    this.rank = r;
  }

  int parent;
  int rank;

}


public class Main {

  static PrintWriter out;
  static InputReader in;

  static int graph[][];
  static Vertex[] vertices;
  static ArrayList<Road> roads;
  static int ans;

  static int n;

  public static void ReadData() {
    n = in.nextInt();
    int m = in.nextInt();
    graph = new int[n][n];
    vertices = new Vertex[n];
    roads = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      vertices[i] = new Vertex(in.nextInt());
    }
    for (int i = 0; i < m; i++) {
      int src = in.nextInt() - 1;
      int dest = in.nextInt() - 1;
      int weight = in.nextInt();

      graph[src][dest] = weight;
      roads.add(new Road(src, dest, weight));
      vertices[src].degree++;
      vertices[dest].degree++;

    }
    for (int i = 0; i < roads.size(); i++) {
      Road road = roads.get(i);
      roads.get(i).length =
          (Math.abs(vertices[road.src].water_tissue - vertices[road.dest].water_tissue) + 1)
              * graph[road.src][road.dest] * (
              Math.abs(vertices[road.src].degree - vertices[road.dest].degree) + 1);
    }

    for (int i = 0; i < n; i++) {
      System.out.println(i + " : " + vertices[i].degree + "\t" + vertices[i].water_tissue);
    }
  }

  public static int find(subset subsets[], int i) {
    if (subsets[i].parent != i) {
      subsets[i].parent = find(subsets, subsets[i].parent);
    }

    return subsets[i].parent;
  }

  public static void Union(subset subsets[], int x, int y) {
    int xroot = find(subsets, x);
    int yroot = find(subsets, y);

    if (subsets[xroot].rank < subsets[yroot].rank) {
      subsets[xroot].parent = yroot;
    } else if (subsets[xroot].rank > subsets[yroot].rank) {
      subsets[yroot].parent = xroot;
    } else {
      subsets[yroot].parent = xroot;
      subsets[xroot].rank++;
    }
  }


  public static void KuskalAlgorithm() {
    Collections.sort(roads);

    subset[] subsets = new subset[n];

    for (int i = 0; i < n; ++i) {
      subsets[i] = new subset(i, 0);
    }

    int e = 0;
    int i = 0;
    while (e < n - 1) {

      int x = find(subsets, roads.get(i).src);
      int y = find(subsets, roads.get(i).dest);

      if (x != y) {
        System.out.println(
            "src : " + roads.get(i).src + "\tdest : " + roads.get(i).dest + "\tweight : " + roads
                .get(i).length);
        ans += roads.get(i).length;
        e++;
        Union(subsets, x, y);
      }

      i++;

    }

    System.out.println(ans);


  }

  public static void main(String args[]) throws IOException {
    out = new PrintWriter(System.out);
    in = new InputReader(System.in);
    ReadData();
    KuskalAlgorithm();
    out.close();
  }

}