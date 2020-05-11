package flashget;

import javafx.concurrent.Task;

import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadTask extends Task<Long> {

    private URL url;
    private File file;
    private long start;
    private long size;

    public DownloadTask(String url, File file, long start, long size) {
        try {
            this.url = new URL(url);
            this.file = file;
            this.size = size;
            this.start = start;
        } catch (MalformedURLException me) {
            System.out.println("downloadtask");
        }
    }

    @Override
    public Long call() throws Exception {
        final int BUFFERSIZE = 1024;
        byte[] buffer = new byte[BUFFERSIZE];
        long startTime = System.nanoTime();
        try {
            URLConnection connection = url.openConnection();
            String range = null;
            if (size > 0) {
                range = String.format("bytes=%d-%d", start, start + size - 1);
            } else {
                // size not given, so read from start byte to end of file
                range = String.format("bytes=%d-", start);
            }
            connection.setRequestProperty("Range", range);
            long fileLength = connection.getContentLengthLong();
            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            //            FileOutputStream fos = new FileOutputStream(file);
            //            BufferedOutputStream out = new BufferedOutputStream(fos , 1024);
            RandomAccessFile out = new RandomAccessFile(file, "rwd");
            out.seek(start);
            int read = 0;
            long download = 0;
            double percentDownloaded = 0.00;
            //            while ((read = in.read(buffer , 0 , 1024)) >= 0) {
            while (download < size) {
                read = in.read(buffer, 0, 1024);
                out.write(buffer, 0, read);
                download += read;
                updateProgress(download, fileLength);
                updateValue(download);
                if (isCancelled()) {
                    updateProgress(0, 0);
                    updateValue((long) 0);
                    break;
                }
                //Stop loop if it's end of a file.
                if (read < 0) break;
            }
            in.close();
            out.close();
            double elapsed = 1.0E-9 * (System.nanoTime() - startTime);
            System.out.printf("Elapsed %.6f sec\n", elapsed);
            return download;
        } catch (IOException ioe) {
        }
        return null;
    }


}

