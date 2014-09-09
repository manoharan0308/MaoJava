package Downloader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by mchinnaswamy on 9/3/2014.
 */
public class DownloadManager {

    public Thread[] divideAndRule(String Url,
                                  long contentSize,
                                  int noOfThreads, String threadName) {
        int chunkSize = (int) (contentSize / noOfThreads);
//        System.out.println("Chunk size : " + chunkSize);
        Thread[] threadPool = new Thread[noOfThreads];
        for (int i = 0; i < noOfThreads; i++) {
            Thread t = new Thread(new Downloader(threadName + i, Url, i * chunkSize, ((i + 1) * chunkSize) - 1));
            threadPool[i] = t;
            t.start();
        }
        return threadPool;
    }

    public static void main(String args[]) {
        String Url;
        int noOfThreads = 30;
//        Url = "http://download.tamiltunes.me/Kaaviyathalaivan%20%282014%29/Aye%20Mr.%20Minor%21%20-%20TamilTunes.com.mp3";
        Url = "https://r3---sn-a8au-cvne.googlevideo.com/videoplayback?initcwndbps=1170000&key=yt5&id=o-AO1Rw-ghgdwWdEcNPM0PtYZhfZIqDhWzW3kLZ1ioODxp&fexp=910207%2C916608%2C919121%2C927622%2C929305%2C930668%2C931983%2C932404%2C932623%2C934024%2C934030%2C940660%2C945076%2C946013%2C953304%2C953801&mm=31&ipbits=0&ms=au&mv=m&source=youtube&ip=64.138.135.2&signature=8E8B02E4AC92EE4AF6DF99BE8D2796AF77FA028A.5A8F315192E26410778D9763A33C03A51743B523&expire=1410298609&mt=1410276961&itag=5&sver=3&sparams=id%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Cmm%2Cms%2Cmv%2Crequiressl%2Csource%2Cupn%2Cexpire&requiressl=yes&upn=ZUdwRtYWX-o";
        String threadName = "SoodhuKavvum";
        String fileExtension = ".flv";
        String outputFile = "SoodhuKavvum";
        String directory = "C:\\Users\\mchinnaswamy\\Downloads\\";
        long startTime = System.currentTimeMillis();

        try {
            URL page = new URL(Url);
            URLConnection connection =  page.openConnection();
            connection.connect();
/*            boolean isPartialContentAllowed = connection.getResponseCode() == 206;
            System.out.println("Partial Content Allowed : " + isPartialContentAllowed);
            if(!isPartialContentAllowed){
                return;
            }
*/
            long contentLength = connection.getContentLengthLong();
            System.out.println("Content length " + contentLength);
            Thread[] threadPool = new DownloadManager().divideAndRule(Url, contentLength, noOfThreads, threadName);

            for (int j = 0; j < noOfThreads; j++) {
                threadPool[j].join();
            }

            Merger merger = new Merger();
            merger.merge(directory, outputFile, fileExtension, noOfThreads, threadName, contentLength);

            long endTime = System.currentTimeMillis();
            System.out.println("Time taken in secs : "+ (endTime-startTime)/1000.0);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
