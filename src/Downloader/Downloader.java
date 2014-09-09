package Downloader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by mchinnaswamy on 9/3/2014.
 */
public class Downloader implements Runnable {

    String threadName, Url;
    int startOffset, endOffset;

    public Downloader(String threadName, String url, int startOffset, int endOffset) {
        this.threadName = threadName;
        this.Url = url;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }


    @Override
    public void run() {
        try {

            URL page = new URL(this.Url);
            URLConnection connection = page.openConnection();
//            System.out.println("startOffset : " + this.startOffset + " " + "endOffset : " + this.endOffset);
            connection.setRequestProperty("Range", "bytes=" + this.startOffset + "-" + this.endOffset);
            long contentLength = connection.getContentLengthLong();
            InputStream is = connection.getInputStream();
            File file = new File("C:\\Users\\mchinnaswamy\\Downloads\\" + this.threadName + ".flv");
            if (!file.exists()) {
                file.createNewFile();
            }
            file.deleteOnExit();
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
}
