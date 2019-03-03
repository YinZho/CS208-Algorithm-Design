import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

public class test {
  static String s = "ABCDE";
  public static void main(String[] args) throws IOException {
//    FileOutputStream fileWriter=new FileOutputStream("C:\\Users\\1\\Desktop\\input");
//    OutputStreamWriter writer=new OutputStreamWriter(fileWriter, "utf-8");
    Random rand = new Random();
    int k = rand.nextInt(20)+1;
    int a = rand.nextInt(20)+k;
    int b = rand.nextInt(200)+a;
    System.out.print(b+" ");
    System.out.print(a+" ");
    System.out.print(k+" ");
    System.out.print("\n");
    for(int i=0;i<b;i++){
      System.out.print(s.charAt(rand.nextInt(5)));
    }
    System.out.print("\n");
    for(int i=0;i<a;i++){
      System.out.print(s.charAt(rand.nextInt(5)));
    }
//		writer.write(b+" ");
//		writer.write(a+" ");
//		writer.write(k+" ");
//		writer.write("\n");
//		for(int i=0;i<b;i++){
//			writer.write(s.charAt(rand.nextInt(5)));
//		}
//		writer.write("\n");
//		for(int i=0;i<a;i++){
//			writer.write(s.charAt(rand.nextInt(5)));
//		}
  }
}