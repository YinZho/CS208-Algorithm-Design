import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

  public static int num;
  public static int[][] dalao_rank;
  public static int[][] menxin_rank;
  public static int[][] menxin_inverse;
  public static Map<Integer, Integer> match = new HashMap<>();

  static PrintWriter out;
  static InputReader in;

  public static void DoMatching() {

    List<Integer> FreeDalao = new LinkedList<>();
    for (int i = 0; i < num; i++) {
      FreeDalao.add(i);
    }

    int MatchStatus[] = new int[num];

    while (!FreeDalao.isEmpty()) {
      Integer CurrentDalao = FreeDalao.remove(0);
      for (int i = MatchStatus[CurrentDalao]; i < num; i++) {
        int menxin = dalao_rank[CurrentDalao][i];
        MatchStatus[CurrentDalao]++;
        if (match.get(menxin) == null) {
          match.put(menxin, CurrentDalao);
          break;
        } else {
          Integer OtherDalao = match.get(menxin);
          if (menxin_inverse[menxin][OtherDalao] > menxin_inverse[menxin][CurrentDalao]) {
            match.put(menxin, CurrentDalao);
            FreeDalao.add(OtherDalao);
            break;
          }
        }
      }
    }
  }

  public static void main(String[] args) throws IOException {
    out = new PrintWriter(System.out);
    in = new InputReader(System.in);
    num = in.nextInt();

    String[] menxin = new String[num];
    Map<String, Integer> dalao_map = new HashMap<>();
    Map<String, Integer> menxin_map = new HashMap<>();
    for (int i = 0; i < num; i++) {
      dalao_map.put(in.next(), i);
    }
    for (int i = 0; i < num; i++) {
      String name = in.next();
      menxin_map.put(name, i);
      menxin[i] = name;
    }
    dalao_rank = new int[num][num];
    menxin_rank = new int[num][num];
    menxin_inverse = new int[num][num];
    for (int i = 0; i < num; i++) {
      for (int j = 0; j < num; j++) {
        dalao_rank[i][j] = menxin_map.get(in.next());
      }
    }
    for (int i = 0; i < num; i++) {
      for (int j = 0; j < num; j++) {
        menxin_rank[i][j] = dalao_map.get(in.next());
      }
    }
    for (int i = 0; i < num; i++) {
      for (int j = 0; j < num; j++) {
        menxin_inverse[i][menxin_rank[i][j]] = j;
      }
    }
    DoMatching();
    Map<String, String> inverse = new HashMap<>();
    for (Map.Entry m : match.entrySet()) {
      inverse.put(m.getValue().toString(), m.getKey().toString());
    }
    for (int i = 0; i < num; i++) {
      out.print(menxin[Integer.parseInt(inverse.get(i + ""))] + " ");
    }
    out.close();
  }
}
