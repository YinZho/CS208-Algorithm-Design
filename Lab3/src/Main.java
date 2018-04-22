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

  public static int n;
  public static int m;
  public static int[] ans;
  public static int ans_p;
  static PrintWriter out;
  static InputReader in;
  static Graph graph;
  static HashMap<Character, Integer> hm;

  public static void main(String args[]) throws IOException {
    out = new PrintWriter(System.out);
    in = new InputReader(System.in);
    ReadData();

    out.close();
  }

  public static int TopologicalSort() {
    int num = hm.size();
    int indgree[] = new int[num];
    int del_n = 0;
    ans_p = 0;

    ArrayList<Integer>[] adjClone = (ArrayList<Integer>[]) graph.adj;

    for (int i = 0; i < num; i++) {
      for (int node : adjClone[i]) {
        indgree[node]++;
      }
    }

    int zero = 0;
    for (int i = 0; i < num; i++) {
      if (indgree[i] == 0) {
        zero++;
      }
    }

    boolean flag = false;
    // mark delete node
    boolean[] mark = new boolean[num];
    for (int i = 0; i < mark.length; i++) {
      mark[i] = true;
    }

    while (zero > 0) {
      if (zero > 1) {
        flag = true;
      }

      for (int i = 0; i < num; i++) {
        if (mark[i] && indgree[i] == 0) {
          mark[i] = false;
          del_n++;
          ans[ans_p++] = i;
          for (int node : adjClone[i]) {
            indgree[node]--;
          }
          break;
        }
      }

      zero = 0;
      for (int i = 0; i < num; i++) {
        if (mark[i] && indgree[i] == 0) {
          zero++;
        }
      }
    }

    if (del_n < num) {
      return 2;
    }
    if (!flag && del_n == n) {
      return 1;
    }
    return 0;
  }

  public static void ReadData() {
    while (true) {
      n = in.nextInt();
      m = in.nextInt();

      // *** initialize ans size ***
      ans = new int[26];

      if (n == 0 && m == 0) {
        break;
      }

      graph = new Graph(n);
      hm = new HashMap<>();

      // *** store topologicalSort return value ***
      int value = 0;
      // *** record steps
      int steps = 0;

      boolean flag = false;

      // *** build char-to-int map ***
      int count = 0;
      for (int i = 1; i <= m; i++) {
        String line = in.next();

        if (hm.size() < n && hm.get(line.charAt(0)) == null) {

          hm.put(line.charAt(0), count);
          count++;
        }
        if (hm.size() < n && hm.get(line.charAt(2)) == null) {
          hm.put(line.charAt(2), count);
          count++;
        }

        if (!flag) {

          graph.addEdge(hm.get(line.charAt(0)), hm.get(line.charAt(2)));
          value = TopologicalSort();

          switch (value) {
            case 1:
              steps = i;
              flag = true;
              break;
            case 2:
              steps = i;
              flag = true;
              break;
          }
        }


      }

      switch (value) {
        case 0:
          System.out.println("Preference sequence cannot be determined.");
          break;
        case 1:
          System.out.print("Preference sequence determined after " + steps + " relations: ");
          HashMap<Integer, Character> hm_new = new HashMap<>();
          Iterator it = hm.entrySet().iterator();
          while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            hm_new.put((int) pair.getValue(), (char) pair.getKey());
          }
          for (int i = 0; i < ans_p; i++) {
            System.out.print(hm_new.get(ans[i]));
          }
          System.out.println(".");
          break;
        case 2:
          System.out.println("Inconsistency found after " + steps + " relations.");
          break;


      }
    }


  }

}


class Graph {

  int V;
  List<Integer> adj[];

  public Graph(int V) {
    this.V = V;
    adj = new ArrayList[V];
    for (int i = 0; i < V; i++) {
      adj[i] = new ArrayList<Integer>();
    }
  }

  public void addEdge(int u, int v) {

    adj[u].add(v);
  }
}
