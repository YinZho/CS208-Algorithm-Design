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

class ClosestPair {

  Node node1;
  Node node2;
  double distence;

  public ClosestPair() {

  }

  public ClosestPair(Node node1, Node node2, double distence) {
    this.node1 = node1;
    this.node2 = node2;
    this.distence = distence;
  }

  public void setClosestPair(Node node1, Node node2, double distence) {
    this.node1 = node1;
    this.node2 = node2;
    this.distence = distence;
  }


  @Override
  public String toString() {
    if (node1.z == 0) {
      if (node1.x < node2.x) {
        return node1.x + ";" + node1.y + "\n" + node2.x + ";" + node2.y;
      } else {
        return node2.x + ";" + node2.y + "\n" + node1.x + ";" + node1.y;
      }

    } else {
      if (node1.x < node2.x) {
        return node1.x + ";" + node1.y + ";" + node1.z + "\n" + node2.x + ";" + node2.y + ";"
            + node2.z;
      } else {
        return node2.x + ";" + node2.y + ";" + node2.z + "\n" + node1.x + ";" + node1.y + ";"
            + node1.z;
      }

    }
  }
}

class Node {

  int x;
  int y;
  int z;

  public Node(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public double distence(Node node) {
    return
        Math.pow(this.x - node.x, 2) + Math.pow(this.y - node.y, 2) + Math.pow(this.z - node.z, 2);
  }

  @Override
  public String toString() {
    return "[" + x + ", " + y + ", " + z + "]";
  }
}


public class Main {

  static PrintWriter out;
  static InputReader in;
  static int degree;
  static int n;

  public static void ReadData() {
    degree = in.nextInt();
    n = in.nextInt();

    switch (degree) {
      case 1:
        StartClosestPair_1D();
        break;
      case 2:
        StartClosestPair_2D();
        break;
      case 3:
        StartClosestPair_3D();
    }
  }

  public static Comparator<Node> compareX = new Comparator<Node>() {
    @Override
    public int compare(Node o1, Node o2) {
      return o1.x - o2.x;
    }
  };

  public static Comparator<Node> compareY = new Comparator<Node>() {
    @Override
    public int compare(Node o1, Node o2) {
      return o1.y - o2.y;
    }
  };

  public static Comparator<Node> compareYZ = new Comparator<Node>() {
    @Override
    public int compare(Node o1, Node o2) {
      if (o1.y != o2.y) {
        return o1.y - o2.y;
      } else {
        return o1.z - o2.z;
      }
    }

  };


  public static void StartClosestPair_1D() {
    int[] nodes = new int[n];
    for (int i = 0; i < n; i++) {
      nodes[i] = in.nextInt();
    }
    Arrays.sort(nodes);
    int min = Integer.MAX_VALUE;
    int point1 = 0;
    int point2 = 0;
    for (int i = 1; i < n; i++) {
      int distence = nodes[i] - nodes[i - 1];
      if (min > distence) {
        point1 = nodes[i - 1];
        point2 = nodes[i];
        min = distence;
      }
    }
    System.out.println(point1 + "\n" + point2);
  }

  static Node[] nodes;

  public static void StartClosestPair_2D() {
    nodes = new Node[n];
    for (int i = 0; i < n; i++) {
      String[] strs = in.next().split(";");
      nodes[i] = new Node(Integer.valueOf(strs[0]), Integer.valueOf(strs[1]), 0);
    }
    Arrays.sort(nodes, compareX);
    System.out.println(ClosestPair_2D(0, nodes.length - 1));
  }


  public static ClosestPair ClosestPair_2D(int left, int right) {

    int length = right - left + 1;

    if (length == 1) {
      return new ClosestPair(null, null, Double.MAX_VALUE);
    } else if (length == 2) {
      return new ClosestPair(nodes[left], nodes[right], nodes[left].distence(nodes[right]));
    } else {

      int mid = (left + right) / 2;

      ClosestPair cp_left = ClosestPair_2D(left, mid);
      ClosestPair cp_right = ClosestPair_2D(mid + 1, right);

      ClosestPair closestPair;

      if (cp_left.distence < cp_right.distence) {
        closestPair = cp_left;
      } else {
        closestPair = cp_right;
      }

      Node[] nodes_tmp = new Node[7];

      int k = 0;

      // 找出中位数附近的点
      for (int i = mid - 3; i <= mid + 3; i++) {
        if (i < n && i >= 0) {
          if (Math.pow(nodes[i].x - nodes[mid].x, 2) <= closestPair.distence) {
            nodes_tmp[k] = nodes[i];
            k++;
          }
        }
      }

      Arrays.sort(nodes_tmp, 0, k, compareY);
      // 中位数左边的和右边的计算距离
      int f;
      for (int i = 0; i < k - 1; i++) {
        f = i + 1;
        if (f < k) {
          while (Math.pow(nodes_tmp[f].y - nodes_tmp[i].y, 2) < closestPair.distence) {
            if (nodes_tmp[i].distence(nodes_tmp[f]) < closestPair.distence) {
              closestPair
                  .setClosestPair(nodes_tmp[i], nodes_tmp[f],
                      nodes_tmp[i].distence(nodes_tmp[f]));

            }
            f++;
            if (f >= k) {
              break;
            }
          }
        }
      }
      return closestPair;
    }
  }


  public static void StartClosestPair_3D() {
    nodes = new Node[n];
    for (int i = 0; i < n; i++) {
      String[] strs = in.next().split(";");
      nodes[i] = new Node(Integer.valueOf(strs[0]), Integer.valueOf(strs[1]),
          Integer.valueOf(strs[2]));
    }
    Arrays.sort(nodes, compareX);
    System.out.println(ClosestPair_3D(0, nodes.length - 1));
  }

  public static ClosestPair ClosestPair_3D(int left, int right) {
    int length = right - left + 1;

    if (length == 1) {
      return new ClosestPair(null, null, Double.MAX_VALUE);
    } else if (length == 2) {
      return new ClosestPair(nodes[left], nodes[right], nodes[left].distence(nodes[right]));
    } else {
      int mid = (left + right) / 2;
      Node[] nodes_1 = new Node[mid - left];
      Node[] nodes_2 = new Node[right - mid + 1];

      ClosestPair cp_left = ClosestPair_3D(left, mid - 1);
      ClosestPair cp_right = ClosestPair_3D(mid, right);

      ClosestPair closestPair;
      if (cp_left.distence < cp_right.distence) {
        closestPair = cp_left;
      } else {
        closestPair = cp_right;
      }

      Node[] nodes_tmp = new Node[13];
      int k = 0; // index of nodes_tmp
      for (int i = mid - 6; i < mid + 6; i++) {
        if (i >= 0 && i < n) {
          if (Math.pow(nodes[i].y - nodes[mid].y, 2) + Math.pow(nodes[i].z - nodes[mid].z, 2)
              <= closestPair.distence) {
            nodes_tmp[k++] = nodes[i];
          }
        }
      }

      Arrays.sort(nodes_tmp, 0, k, compareYZ);

      int f;

      for (int i = 0; i < k - 1; i++) {
        f = i + 1;
        if (f < k) {
          while (Math.pow(nodes_tmp[f].y - nodes_tmp[i].y, 2) + Math
              .pow(nodes_tmp[f].z - nodes_tmp[i].z, 2) < closestPair.distence) {
            if (nodes_tmp[i].distence(nodes_tmp[f]) < closestPair.distence) {
              closestPair
                  .setClosestPair(nodes_tmp[i], nodes_tmp[f], nodes_tmp[i].distence(nodes_tmp[f]));
            }
            f++;
            if (f >= k) {
              break;
            }
          }
        }
      }

      return closestPair;
    }
  }

  public static void main(String args[]) throws IOException {
    out = new PrintWriter(System.out);
    in = new InputReader(System.in);
    ReadData();
    out.close();
  }

}