import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by mchinnaswamy on 9/2/2014.
 */
public class UrlConnectionDemo {


    public void connect(String Url) {
        try {
            URL page = new URL(Url);
            URLConnection connection = page.openConnection();

            long contentLength = connection.getContentLengthLong();
            String contentType = connection.getContentType();
            System.out.println("Content type : "+ contentType);
            System.out.println("Content length "+ contentLength);
            InputStream is = connection.getInputStream();
            File file = new File("C:\\Users\\mchinnaswamy\\Downloads\\MrMinor.mp3");
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream os = new FileOutputStream(file.getAbsoluteFile());
            byte[] buf = new byte[1024];
            int bytesRead = 0;
            int totalRead = 0;

            while ((bytesRead = is.read(buf)) != -1) {
                totalRead += bytesRead;
                os.write(buf, 0, bytesRead);
            }
            assert totalRead == contentLength;
            os.close();
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {

        final String Url ="http://download.tamiltunes.me/Kaaviyathalaivan%20%282014%29/Aye%20Mr.%20Minor%21%20-%20TamilTunes.com.mp3";
                //"http://www.sriramanamaharshi.org/downloadbooks/whoami_all_languages/Who_Am_I_English.pdf";

        UrlConnectionDemo u1 = new UrlConnectionDemo();
        u1.connect(Url);
    }
}
