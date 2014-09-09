package Downloader;

import java.io.*;

/**
 * Created by mchinnaswamy on 9/4/2014.
 */
public class Merger {

    public void merge(String directory, String outputFile, String fileExtension, int noOfthreads, String threadName, long contentLength) throws IOException {
        File outFile = new File(directory + outputFile + fileExtension);
        OutputStream os = new FileOutputStream(outFile);
        for (int t = 0; t < noOfthreads; t++) {
            File file = new File(directory + threadName + t + fileExtension);
            FileInputStream is = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int bytesRead = 0;
            int totalRead = 0;

            while ((bytesRead = is.read(buf)) != -1) {
                totalRead += bytesRead;
                os.write(buf, 0, bytesRead);
            }
            is.close();

            assert totalRead == contentLength;
        }
        os.close();

    }
}
