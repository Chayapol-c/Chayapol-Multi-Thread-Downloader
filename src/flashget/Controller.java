package flashget;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Controller control DownloadTask. It contain UI components. set their an action.
 *
 * @author Chayapol 6210545947
 */
public class Controller {
    /**
     * threadProgress1-5 are ProgressBar of DownloadTask.
     */
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

    /**
     * Input Field.
     */
    @FXML
    private TextField textField;

    @FXML
    private Button downloadButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button resumeButton;

    @FXML
    private Button pauseButton;

    /**
     * Display name of download file
     */
    @FXML
    private Label fileNameLabel;

    /**
     * Display current downloaded of file
     */
    @FXML
    private Label percentProgress;

    /**
     * Display the remaining time
     */
    @FXML
    private Label downloadTimeLabel;

    /**
     * Display String "Thread: "
     */
    @FXML
    private Label threadTextLabel;

    /**
     * ProgressBar to show the progress of downloading
     */
    @FXML
    private ProgressBar downloadBar;

    /**
     * Application's pane
     */
    @FXML
    private AnchorPane browseScene;
    private ProgressBar[] threadProgresses;
    private DownloadManipulator controller;
    private File out ;
    private String text;

    @FXML
    public void initialize() {
        downloadButton.setOnAction(this::startWorker);
        clearButton.setOnAction(this::clear);
        downloadBar.setProgress(0.0);
        cancelButton.setOnAction(this::stopWorker);
        threadProgresses = new ProgressBar[]{threadProgress1, threadProgress2, threadProgress3, threadProgress4, threadProgress5};
        downloadButton.setVisible(true);
        cancelButton.setVisible(true);
        pauseButton.setVisible(false);
        resumeButton.setVisible(false);
        VisibleOfUI.threadAreaControl(false, threadProgresses, threadTextLabel);
    }

    /**
     * Call this method when Download button is pressed
     */
    public void startWorker(ActionEvent event) {
        text = textField.getText().trim();
        if (text.isEmpty()) return;
        //Create FileHandle for Out
        FileHandle fileHandle = new FileHandle();
        UrlHandle urlHandle = new UrlHandle();
        urlHandle.connectURL(textField);
        long fileLength = urlHandle.getFileLength();
        String fileName = urlHandle.getFileName();
        out = null;
        if (fileLength == 0) return;
        try {
            out = fileHandle.browseFile(browseScene, fileName);
        } catch (NullPointerException npe) {
            return;
        }
        downloadButton.setVisible(false);
        pauseButton.setVisible(true);
        cancelButton.setDisable(false);
        fileNameLabel.setText(fileName);
        // set threadNumber
        final int threadNumber = 5;
        controller = new DownloadManipulator(fileLength, threadProgresses, percentProgress, downloadTimeLabel, downloadBar, downloadButton);
        long size = controller.sizeDivider(fileLength, threadNumber);
        controller.startDownload(text, out, size, threadNumber);
        VisibleOfUI.threadAreaControl(true, threadProgresses, threadTextLabel);
    }

    /**
     * Call this method when Cancel button is pressed
     */
    public void stopWorker(ActionEvent event) {
        controller.stopDownload();
        pauseButton.setVisible(false);
        resumeButton.setVisible(false);
        downloadButton.setVisible(true);
        VisibleOfUI.threadAreaControl(false, threadProgresses, threadTextLabel);
    }

    /**
     * Call this method when Clear button is pressed
     */
    public void clear(ActionEvent event) {
        textField.clear();
    }
}

