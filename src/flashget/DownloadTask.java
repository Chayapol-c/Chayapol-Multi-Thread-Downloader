package flashget;

import javafx.concurrent.Task;

import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Download and Write file from start point
 *
 * @author Chayapol 6210545947
 */
public class DownloadTask extends Task<Long> {

    private URL url;
    private File file;
    private long start;
    private long size;

    /**
     * Initialize the DownloadTask
     *
     * @param url   is a url in String
     * @param file  is destination file
     * @param start is start byte for this Task.
     * @param size  is byte size for this Task.
     */
    public DownloadTask(String url, File file, long start, long size) {
        try {
            this.url = new URL(url);
            this.file = file;
            this.size = size;
            this.start = start;
        } catch (MalformedURLException me) {
            Notification.showDialog("Cannot find file " + file);
        }
    }

    @Override
    public Long call() {
        final int BUFFERSIZE = 1024;
        byte[] buffer = new byte[BUFFERSIZE];
        try {
            URLConnection connection = url.openConnection();
            String range;
            if (size > 0) {
                range = String.format("bytes=%d-%d", start, start + size - 1);
            } else {
                // size not given, so read from start byte to end of file
                range = String.format("bytes=%d-", start);
            }
            connection.setRequestProperty("Range", range);
            long fileLength = connection.getContentLengthLong();
            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            RandomAccessFile out = new RandomAccessFile(file, "rwd");
            out.seek(start);
            int read;
            long download = 0;
            while (download < size) {
                read = in.read(buffer, 0, 1024);
                out.write(buffer, 0, read);
                download += read;
                updateProgress(download, fileLength);
                updateValue(download);
                if (isCancelled()) {
                    updateProgress(0, 0);
                    updateValue((long) 0);
                    Notification.showDialog("cancel download");
                    break;
                }

                //Stop loop if it's end of a file.
                if (read < 0) break;
            }
            in.close();
            out.close();
            return download;
        } catch (IOException ioe) {
            Notification.showDialog("Cannot find file " + file);
        }
        return null;
    }


}

