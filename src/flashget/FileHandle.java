package flashget;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FileHandle {
    private String fileName;
    private long fileLength;

    public File browseFile(AnchorPane browseScene, Label fileNameLabel) {
        final FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Directory");
        chooser.setInitialFileName(fileName);
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*" + fileName));
        Stage stage = (Stage) browseScene.getScene().getWindow();
        File out = null;
        try {
            out = chooser.showSaveDialog(stage);
            chooser.setInitialDirectory(out.getParentFile());
        } catch (NullPointerException npe) {
            fileNameLabel.setText("Please Input URL");
        }
        return out;
    }

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

    public String getFileName() {
        return fileName;
    }

    public long getFileLength() {
        return fileLength;
    }

}
