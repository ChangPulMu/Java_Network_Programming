import java.io.*;

public class MarkResetDo{
	public static void main(String[] args) throws Exception {
		
		InputStream is = null;
		BufferedInputStream bis = null;
		
		try {
			File file = new File("./bin/data/number.txt");
			is = new FileInputStream(file);
			bis = new BufferedInputStream(is);
			
			if(bis.markSupported()) {
				int b, count=0;
				int half = (int)file.length()/2;
				
				System.out.println(file.length()+" "+half);
				System.out.println("read complete file : ");
				System.out.println("-----------------------------");
				
				while((b=bis.read())>0) {
					System.out.print((char)b);
					
					if(++count == half)
						bis.mark(half);
				}
				System.out.println("\n-----------------------------");
				System.out.println("\nmarked : "+(half)+"\n");
				
				bis.reset();
				
				System.out.println("read back from marked point : ");
				System.out.println("-----------------------------");
				
				while((b=bis.read())>0) {
					System.out.print((char)b);
				}
				System.out.println("\n-----------------------------");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(is!=null) is.close();
			if(bis!=null) bis.close();
		}
	}
}