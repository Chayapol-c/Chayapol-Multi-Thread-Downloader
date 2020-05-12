package flashget;

import javafx.fxml.FXML;
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

/**
 * @author Chayapol 6210545947
 */

public class FileHandle {
    private String fileName;
    private long fileLength;

    /**
     * @param browseScene
     * @return destination file
     */
    public File browseFile(AnchorPane browseScene) {

        final FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Directory");
        chooser.setInitialFileName(fileName);
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*" + fileName));
        Stage stage = (Stage) browseScene.getScene().getWindow();
        File out = null;
        out = chooser.showSaveDialog(stage);
        chooser.setInitialDirectory(out.getParentFile());

        return out;
    }

    /**
     * @param textField
     */
    public void connectURL(TextField textField) {
        String text = textField.getText().trim();
        fileName = text.substring(text.lastIndexOf('/') + 1);
        try {
            URL url = new URL(text);
            URLConnection connection = url.openConnection();
            fileLength = connection.getContentLengthLong();
        } catch (MalformedURLException ignored) {
            Notification.showDialog("Please input url ");
        } catch (IOException e) {
            Notification.showDialog("Cannot find file " + fileName);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileLength() {
        return fileLength;
    }

}
