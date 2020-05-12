package flashget;

import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * FileHandle can set where downloaded file will go and also can rename it.
 *
 * @author Chayapol 6210545947
 */

public class FileHandle {

    /**
     * Open new window for choosing destination.
     *
     * @param browseScene is current scene.
     * @param fileName    is a file name String.
     * @return destination file
     */
    public File browseFile(AnchorPane browseScene, String fileName) {

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

}
