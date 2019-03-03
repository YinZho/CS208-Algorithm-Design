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

  static int count;
  static int[] tmp;

  public void readData() {
    int n = in.nextInt();
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) {
      arr[i] = in.nextInt();
    }

    System.out.println(countInversePairs(arr));
  }

  public int countInversePairs(int[] arr) {
    if (arr.length == 1) {
      return 0;
    }
    tmp = new int[arr.length];
    SortAndCount(arr, 0, arr.length - 1);
    return count;
  }

  public void SortAndCount(int[] arr, int left, int right) {
    if (left == right) {
      return;
    }
    int mid = (left + right) / 2;
    SortAndCount(arr, left, mid);
    SortAndCount(arr, mid + 1, right);
    MergeAndCount(arr, left, mid + 1, right);
  }

  public void MergeAndCount(int[] arr, int left, int mid, int right) {

    int i = left;
    int j = mid;
    int k = left;

    while (i < mid && j <= right) {
      if (arr[i] <= arr[j]) {
        tmp[k] = arr[i];
        i++;
      } else {
        tmp[k] = arr[j];
        j++;
        count += mid - i;
        if (count > 1000000007) {
          count %= 1000000007;
        }
      }
      k++;
    }

    while (i < mid) {
      tmp[k] = arr[i];
      i++;
      k++;
    }
    while (j <= right) {
      tmp[k] = arr[j];
      j++;
      k++;
    }

    for (i = left; i <= right; i++) {
      arr[i] = tmp[i];
    }
  }

  public static void main(String args[]) throws IOException {
    out = new PrintWriter(System.out);
    in = new InputReader(System.in);
    Main main = new Main();
    main.readData();
    out.close();
  }

}