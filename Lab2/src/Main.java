
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

}

public class Main {

  static PrintWriter out;
  static InputReader in;
  static int n, m, k;
  static Node[][] map;
  static Coodinate[] targets;
  static int[] steps;
  static int count;

  static int[][] CloseSet;
  static PriorityQueue<Node> OpenSet;

  public static int ReadData() {
    n = in.nextInt();
    m = in.nextInt();
    map = new Node[n][m];
    for (int i = 0; i < n; i++) {
      char[] str = in.next().toCharArray();
      for (int j = 0; j < m; j++) {
        switch (str[j]) {
          case 'r':
            map[i][j] = new Node(new Coodinate(i, j));
            break;
          case 'w':
            map[i][j] = null;
            break;
        }
      }
    }
    k = in.nextInt();
    targets = new Coodinate[k];
    boolean flag = false;
    for (int i = 0; i < k; i++) {
      int x = in.nextInt();
      int y = in.nextInt();
      if (x < 0 || x >= n || y < 0 || y >= m || map[x][y] == null || map[0][0] == null) {
        flag = true;
      } else {
        targets[i] = new Coodinate(x, y);
      }
    }

    if (flag) {
      return -1;
    }
    return 0;
  }

  public static int AStarAlgorithm(Coodinate start, Coodinate end) {

    OpenSet = new PriorityQueue<>();
    CloseSet = new int[n][m];

    OpenSet
        .add(new Node(start, 0, CalculateH(start.x, start.y, end)));
    map[start.x][start.y].isInOpenSet = count;

    while (!OpenSet.isEmpty()) {

      Node current = OpenSet.poll();

      if (current.coo.x == end.x && current.coo.y == end.y) {
        return current.G;
      }

      int x, y;
      x = current.coo.x;
      y = current.coo.y;
      CloseSet[x][y] = 1;

      // right
      addNeighborInOpen(x, y + 1, current, end);
      // up
      addNeighborInOpen(x - 1, y, current, end);
      // left
      addNeighborInOpen(x, y - 1, current, end);
      // down
      addNeighborInOpen(x + 1, y, current, end);
      current.G = 0;

    }

    return -1;
  }

  public static void addNeighborInOpen(int x, int y, Node current, Coodinate end) {
    if (canAddInOpen(x, y)) {
      if (map[x][y].isInOpenSet != count) {
        map[x][y].G = current.G + 1;
        map[x][y].H = CalculateH(x, y, end);
        OpenSet.add(map[x][y]);
        map[x][y].isInOpenSet = count;

      } else {
        if (map[x][y].G > current.G + 1) {
          OpenSet.remove(map[x][y]);
          map[x][y].G = current.G + 1;
          OpenSet.add(map[x][y]);
        }
      }
    }
  }

  public static int CalculateH(int x, int y, Coodinate end) {
    return Math.abs(x - end.x) + Math.abs(y - end.y);
  }


  public static boolean canAddInOpen(int x, int y) {
    if (x < 0 || x >= n || y < 0 || y >= m || map[x][y] == null || CloseSet[x][y] == 1) {
      return false;
    }
    return true;
  }


  public static void main(String[] args) throws IOException {
    out = new PrintWriter(System.out);
    in = new InputReader(System.in);

    boolean flag = false;

    if (ReadData() == -1) {
      out.print(-1);
    } else {
      steps = new int[k];
      for (int i = 0; i < k; i++) {
        count = i;
        if (i == 0) {
          steps[i] = AStarAlgorithm(new Coodinate(0, 0), targets[i]);
        } else {
          steps[i] = AStarAlgorithm(targets[i - 1], targets[i]);
        }
        if (steps[i] == -1) {
          flag = true;
          break;
        }
      }
      if (flag) {
        out.print(-1);
      } else {
        int ans = 0;
        for (int e : steps
            ) {
          ans += e;
        }
        out.print(ans);
      }
    }
    out.close();

  }
}

class Coodinate {

  int x;
  int y;

  public Coodinate(int x, int y) {
    this.x = x;
    this.y = y;

  }
}


class Node implements Comparable<Node> {

  public Coodinate coo;
  public int G;
  public int H;
  public int isInOpenSet;

  public Node(Coodinate coo) {
    this.coo = coo;
    this.G = Integer.MAX_VALUE;
  }

  public Node(Coodinate coo, int G, int H) {
    this.coo = coo;
    this.G = G;
    this.H = H;
  }

  @Override
  public int compareTo(Node o) {
    if (o == null) {
      return -1;
    }
    return (this.G + this.H) - (o.G + o.H);
  }
}