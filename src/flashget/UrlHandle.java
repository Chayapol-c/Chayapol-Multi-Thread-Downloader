package flashget;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlHandle {

    private long fileLength;
    private String fileName;

    public void connectURL(TextField textField) {
        String text = textField.getText().trim();
        try {
            URL url = new URL(text);
            URLConnection connection = url.openConnection();
            fileLength = connection.getContentLengthLong();
            fileName = text.substring(text.lastIndexOf('/') + 1);
        } catch (MalformedURLException ignored) {
            System.out.println("connect url");
            //Alert

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getFileLength(){
        return fileLength;
    }

    public String getFileName(){
        return fileName;
    }

}
