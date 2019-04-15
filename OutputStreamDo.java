import java.io.*;

public class OutputStreamDo{
	public static void main(String[] args) {
		
		/* OutputStream : byte stream, OutputStreamWriter char stream */
		
		//OutputStream out = (System.out);
		OutputStreamWriter out = new OutputStreamWriter(System.out);
		
		char out1 = 'A';
		char out2 = '가';
		
		try {
			out.write(out1);
			out.write(out2);
			
			out.flush();
			out.close();
		}
		catch(IOException ie)
		{
			System.out.println("예외냐? 이것때문 : "+ie);
		}
	}
}