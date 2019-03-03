import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
class D2 implements Comparable<D2>{
  double x,y;
  @Override
  public int compareTo(D2 d) {
    return (int)(this.x-d.x);
  }
}
class D3 implements Comparable<D3>{
  double x,y,z;
  @Override
  public int compareTo(D3 d) {
    return (int)(this.y - d.y);
  }
}
class P1{
  double a;
  double b;
  double dis;
  P1(){};
  P1(double a,double b){
    this.a = a;
    this.b = b;
    this.dis = Math.abs(a-b);
  }
}
class P2{
  D2 a,b;
  double dis;
  P2(){};
  P2(D2 a,D2 b){
    this.a = a;
    this.b = b;
    this.dis = (a.x - b.x)*(a.x - b.x)+(a.y - b.y)*(a.y - b.y);
  }
}
class P3{
  D3 a,b;
  double dis;
  P3(){};
  P3(D3 a,D3 b){
    this.a = a;
    this.b = b;
    this.dis = (a.x - b.x)*(a.x - b.x)+(a.y - b.y)*(a.y - b.y)+(a.z - b.z)*(a.z - b.z);
  }
}
public class Main1 {
  static int[] Dim1;
  static D2[] Dim2;
  static D3[] Dim3;
  static D2[] tmp2;
  static D3[] tmp3;
  static InputReader input;
  static int Dim;
  static int n;
  static int cnt2;
  static int cnt3;
  static P1 p1;
  static P2 p2;
  static P3 p3;
  public static void main(String[] args) throws FileNotFoundException {
    input = new InputReader(System.in);
    Dim = input.nextInt();
    n = input.nextInt();
    if(Dim==1){
      init1();
    }else if(Dim==2){
      init2();
    }else{
      init3();
    }
  }
  public static void init1(){
//		long start  = System.currentTimeMillis();
    p1 = new P1();
    p1.dis = Double.MAX_VALUE;
    Dim1 = new int[n];
    for(int i=0;i<n;i++){
      Dim1[i] = input.nextInt();
    }
    Arrays.sort(Dim1);
    P1 res = solve1(0,n-1);
    if(res.a>res.b){
      System.out.println((int)res.b);
      System.out.println((int)res.a);
    }else{
      System.out.println((int)res.a);
      System.out.println((int)res.b);
    }
//		long end  = System.currentTimeMillis();
//		System.out.println(end - start);
  }
  public static P1 solve1(int l,int r) {
    if(r==l){
      return p1;
    }
    if(r-l==1){
      return new P1(Dim1[l],Dim1[r]);
    }
    int mid = (l+r)/2;
    P1 lp = solve1(l,mid);
    P1 rp = solve1(mid+1,r);
    P1 min = null;
    if(lp.dis<rp.dis){
      min = lp;
    }
    else{
      min = rp;
    }
    P1 judge = new P1(Dim1[mid],Dim1[mid+1]);
    if(judge.dis<min.dis){
      min = judge;
    }
    return min;
  }
  public static void init2(){
//		long start = System.currentTimeMillis();
    p2 = new P2();
    p2.dis = Double.MAX_VALUE;
    Dim2 = new D2[n];
    tmp2 = new D2[n];
    for(int i=0;i<n;i++){
      Dim2[i] = new D2();
      Dim2[i].x = input.nextInt();
      Dim2[i].y = input.nextInt();
    }
    Arrays.sort(Dim2);
    P2 res = solve2(0,n-1);
    if((int)res.a.x>(int)res.b.x){
      System.out.println((int)res.b.x+";"+(int)res.b.y);
      System.out.println((int)res.a.x+";"+(int)res.a.y);
    }else{
      System.out.println((int)res.a.x+";"+(int)res.a.y);
      System.out.println((int)res.b.x+";"+(int)res.b.y);
    }
//		long end = System.currentTimeMillis();
//		System.out.println(end - start);
  }
  public static P2 solve2(int l, int r) {
    if(r==l){
      return p2;
    }
    if(r-l==1){
      return new P2(Dim2[l],Dim2[r]);
    }
    int mid = (l+r)/2;
    P2 lp = solve2(l,mid);
    P2 rp = solve2(mid+1,r);
    P2 min = null;
    cnt2 = 0;
    if(lp.dis<rp.dis){
      min = lp;
    }else{
      min = rp;
    }
    for(int i=l;i<=r;i++){
      if((Dim2[mid].x - Dim2[i].x)*(Dim2[mid].x - Dim2[i].x) <= min.dis){
        tmp2[cnt2++] = Dim2[i];
      }
    }
    Arrays.sort(tmp2,0,cnt2,new Comparator<D2>(){
      @Override
      public int compare(D2 d1, D2 d2) {
        return (int)(d1.y - d2.y);
      }

    });
    for(int i=0;i<cnt2;i++){
      for(int j=i+1;j<cnt2&&(tmp2[i].y - tmp2[j].y)*(tmp2[i].y - tmp2[j].y)<min.dis;j++){
        if((tmp2[i].x - tmp2[j].x)*(tmp2[i].x - tmp2[j].x)+(tmp2[i].y - tmp2[j].y)*(tmp2[i].y - tmp2[j].y)<min.dis){
          min = new P2(tmp2[i],tmp2[j]);
        }
      }
    }
    return min;
  }
  public static void init3(){
//		long start = System.currentTimeMillis();
    p3 = new P3();
    p3.dis = Double.MAX_VALUE;
    Dim3 = new D3[n];
    tmp3 = new D3[n];
    for(int i=0;i<n;i++){
//			String in = input.next();
//			int pos1 = in.indexOf(';');
//			int pos2 = in.lastIndexOf(';');
      Dim3[i] = new D3();
      Dim3[i].x = input.nextInt();
      Dim3[i].y = input.nextInt();
      Dim3[i].z = input.nextInt();
    }
    Arrays.sort(Dim3);
    P3 res = solve3(0,n-1);
    if((int)res.a.x>(int)res.b.x){
      System.out.println((int)res.b.x+";"+(int)res.b.y+";"+(int)res.b.z);
      System.out.println((int)res.a.x+";"+(int)res.a.y+";"+(int)res.a.z);
    }else{
      System.out.println((int)res.a.x+";"+(int)res.a.y+";"+(int)res.a.z);
      System.out.println((int)res.b.x+";"+(int)res.b.y+";"+(int)res.b.z);
    }
//		long end = System.currentTimeMillis();
//		System.out.println(end - start);
  }
  public static P3 solve3(int l, int r) {
    if(r==l){
      return p3;
    }
    if(r-l==1){
      return new P3(Dim3[l],Dim3[r]);
    }
    int mid = (l+r)/2;
    P3 lp = solve3(l,mid);
    P3 rp = solve3(mid+1,r);
    P3 min = null;
    cnt3 = 0;
    if(lp.dis<rp.dis){
      min = lp;
    }
    else{
      min = rp;
    }
    for(int i=l;i<=r;i++){
      if((Dim3[mid].y - Dim3[i].y)*(Dim3[mid].y - Dim3[i].y) <= min.dis){
        tmp3[cnt3++] = Dim3[i];
      }
    }
    Arrays.sort(tmp3,0,cnt3,new Comparator<D3>(){
      @Override
      public int compare(D3 d1, D3 d2) {
        return d1.x == d2.x?(int)(d1.z - d2.z):(int)(d1.x - d2.x);
      }

    });
    for(int i=0;i<cnt3;i++){
      for(int j=i+1;j<cnt3&&((tmp3[i].x - tmp3[j].x)*(tmp3[i].x - tmp3[j].x)<min.dis || (tmp3[i].z - tmp3[j].z)*(tmp3[i].z - tmp3[j].z)<min.dis);j++){
        if((tmp3[i].x - tmp3[j].x)*(tmp3[i].x - tmp3[j].x)+(tmp3[i].y - tmp3[j].y)*(tmp3[i].y - tmp3[j].y) +
            (tmp3[i].z - tmp3[j].z)*(tmp3[i].z - tmp3[j].z)<min.dis){
          min = new P3(tmp3[i],tmp3[j]);
        }
      }
    }
    return min;
  }
  static class InputReader {

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
    public int getchar() throws IOException{
      int c = br.read();
      return c;
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
        while (c > 32 && c <= 57) {
          x = x * 10 + c - '0';
          c = br.read();
        }
        return negative ? -x : x;
      } catch (IOException e) {
        return -1;
      }
    }
  }
}