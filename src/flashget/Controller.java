package flashget;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Contain UI component
 *
 * @author Chayapol 6210545947
 */
public class Controller {

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem openFolder;

    @FXML
    private MenuItem selectFolder;

    @FXML
    private MenuItem quit;

    @FXML
    private Menu file;

    @FXML
    private Menu help;

    @FXML
    private MenuItem about;

    @FXML
    private MenuItem contactMe;

    @FXML
    private ProgressBar threadProgress1;

    @FXML
    private ProgressBar threadProgress2;

    @FXML
    private ProgressBar threadProgress3;

    @FXML
    private ProgressBar threadProgress4;

    @FXML
    private ProgressBar threadProgress5;

    @FXML
    private TextField textField;

    @FXML
    private Button downloadButton;

    @FXML
    private Button clearButton;

    @FXML
    public Label fileNameLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private ProgressBar downloadBar;

    @FXML
    private Label percentProgress;

    @FXML
    private Button resumeButton;

    @FXML
    private Button pauseButton;

    @FXML
    public AnchorPane browseScene;

    @FXML
    private Label downloadTimeLabel;

    @FXML
    private Label threadTextLabel;

    private ProgressBar[] threadProgresses;
    private DownloadManipulator controller;
//    private VisibleOfUI visibleControl ;

    @FXML
    public void initialize() {

        downloadButton.setOnAction(this::startWorker);
        clearButton.setOnAction(this::clear);
        downloadBar.setProgress(0.0);
        cancelButton.setOnAction(this::stopWorker);
        pauseButton.setOnAction(this::pause);
        resumeButton.setOnAction(this::resume);
        quit.setOnAction(this::quitProgram);
        openFolder.setOnAction(this::getDownloadFolder);
        selectFolder.setOnAction(this::setDownloadFolder);
        about.setOnAction(this::getUsage);
        contactMe.setOnAction(this::getContact);

        threadProgresses = new ProgressBar[]{threadProgress1, threadProgress2, threadProgress3, threadProgress4, threadProgress5};

        downloadButton.setVisible(true);
        cancelButton.setVisible(true);
        pauseButton.setVisible(false);
        resumeButton.setVisible(false);

//        Map buttons = new HashMap<Button, Boolean>();
//        buttons.put(downloadButton,true);
//        buttons.put(cancelButton,true);
//        buttons.put(pauseButton,false);
//        buttons.put(resumeButton,false);
//        visibleControl = new VisibleOfUI();
//        visibleControl.setButtons(buttons);
        VisibleOfUI.threadAreaControl(false, threadProgresses, threadTextLabel);

    }

    private void getContact(ActionEvent event) {
    }

    private void getUsage(ActionEvent event) {
    }


    //TODO refactor logical method to another class
    public void startWorker(ActionEvent event) {

        String text = textField.getText().trim();
        if (text.isEmpty()) return;
        //Create FileHandle for Out
        FileHandle fileHandle = new FileHandle();
        fileHandle.connectURL(textField);
        long fileLength = fileHandle.getFileLength();
        String fileName = fileHandle.getFileName();
        File out =null;
        if(fileLength == 0) return;
        try {
            out = fileHandle.browseFile(browseScene);
        }catch (NullPointerException npe){
            return;
        }

        downloadButton.setVisible(false);
        pauseButton.setVisible(true);
        cancelButton.setDisable(false);
        fileNameLabel.setText(fileName);
        int threadNumber = 5;

        controller = new DownloadManipulator(fileLength,threadProgresses,percentProgress,downloadTimeLabel,downloadBar,downloadButton);
        controller.startDownload(threadNumber, out, text);
        VisibleOfUI.threadAreaControl(true, threadProgresses, threadTextLabel);
    }

    //TODO cancel Task
    public void stopWorker(ActionEvent event) {
        controller.stopDownload();
        pauseButton.setVisible(false);
        resumeButton.setVisible(false);
        downloadButton.setVisible(true);
        VisibleOfUI.threadAreaControl(false, threadProgresses, threadTextLabel);
    }

    public void clear(ActionEvent event) {
        textField.clear();
    }

    public void resume(ActionEvent event) {
        pauseButton.setVisible(true);
        resumeButton.setVisible(false);
    }

    public void pause(ActionEvent event) {
        resumeButton.setVisible(true);
        pauseButton.setVisible(false);
    }

    private void setDownloadFolder(ActionEvent event) {
    }

    private void getDownloadFolder(ActionEvent event) {
    }

    private void quitProgram(ActionEvent event) {
        System.exit(0);
    }

}

