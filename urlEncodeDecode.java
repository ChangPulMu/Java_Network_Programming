
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class urlEncodeDecode {

       public static void main(String args[]) {
    	   try {
    		   String url = "https://s.pstatic.net/static/www/mobile/edit/2016/0705/mobile_212852414260.png";
    		   
    		   System.out.println("Original URL "+url+"\n");
    		   
    		   String encodedUrl = URLEncoder.encode(url, "UTF-8");
    		   System.out.println("Encoded URL "+encodedUrl);
    		   
    		   String decodedUrl = URLDecoder.decode(url, "UTF-8");
    		   System.out.println("Dncoded URL "+decodedUrl);
    	   } catch (UnsupportedEncodingException uee) {
    		   System.err.println(uee);
    	   }
       }
}