package flashget;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * UrlHandle can connect url by String and get file length.
 *
 * @author Chayapol 6210545947
 */
public class UrlHandle {

    private long fileLength;
    private String fileName;

    /**
     * Connect url, both get file length and set file name.
     *
     * @param textField is url input text field.
     */
    public void connectURL(TextField textField) {
        String text = textField.getText().trim();
        try {
            URL url = new URL(text);
            URLConnection connection = url.openConnection();
            fileLength = connection.getContentLengthLong();
            fileName = text.substring(text.lastIndexOf('/') + 1);
        } catch (MalformedURLException ignored) {
            Notification.showDialog("Please input url ");
        } catch (IOException e) {
            Notification.showDialog("Cannot find file " + fileName);
        }
    }

    /**
     * Get file's length.
     *
     * @return file's length
     */
    public long getFileLength() {
        return fileLength;
    }

    /**
     * Get name of download file.
     *
     * @return name of download file
     */
    public String getFileName() {
        return fileName;
    }

}
