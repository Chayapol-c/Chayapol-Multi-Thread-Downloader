package flashget;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Chayapol 6210545947
 */
public class DownloadManipulator {


    private DownloadTask[] tasks;
    private Label percentProgress;
    private Label downloadTimeLabel;
    private ProgressBar[] threadProgresses;
    private ProgressBar downloadBar;
    private Button downloadButton;

    private OutputFormat formatter;
    private long downloaded;
    private long startDownloadTime;
    private long fileLength;

    public DownloadManipulator(long fileLength, ProgressBar[] threadProgresses, Label percentProgress, Label downloadTimeLabel, ProgressBar downloadBar, Button downloadButton) {
        this.fileLength = fileLength;
        this.threadProgresses = threadProgresses;
        this.percentProgress = percentProgress;
        this.downloadTimeLabel = downloadTimeLabel;
        this.downloadBar = downloadBar;
        this.downloadButton = downloadButton;
    }

    /**
     * Create and run Threads
     *
     * @param threadNumber
     * @param out
     * @param urlName
     */
    public void startDownload(int threadNumber, File out, String urlName) {
        formatter = new OutputFormat();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        long KILOBYTE = 4096 * 4;
        long chunk = (fileLength / (KILOBYTE)) + 1;
        long size = ((chunk / threadNumber)) * (KILOBYTE);
        // Start counting estimate time
        startDownloadTime = System.nanoTime();
        tasks = new DownloadTask[threadNumber];
        // Create and addListener to DownloadTask

        for (int i = 0; i < threadNumber; i++) {
            tasks[i] = new DownloadTask(urlName, out, size * i, size);
            if (i == threadNumber - 1) {
                tasks[i] = new DownloadTask(urlName, out, size * i, fileLength - (size * i));
            }
            //bind each threadBar with DownloadTask
            threadProgresses[i].progressProperty().bind(tasks[i].progressProperty());
            tasks[i].valueProperty().addListener(this::valueChange);
            executor.execute(tasks[i]);
        }

        //bind downloadBar with each DownloadTask
        downloadBar.progressProperty().bind(tasks[0].progressProperty().multiply(0.2).add(tasks[1].progressProperty().multiply(0.2)).add(
                tasks[2].progressProperty().multiply(0.2).add(tasks[3].progressProperty().multiply(0.2).add(tasks[4].progressProperty().multiply(0.2)))));
        executor.shutdown();
    }

    /**
     * Stop downloading
     */
    public void stopDownload() {
        for (DownloadTask task : tasks) {
            task.cancel();
        }
    }


    private void valueChange(ObservableValue observableValue, Object oldValue, Object newValue) {
        // change null to 0 for old value
        if (oldValue == null) oldValue = 0L;
        //sum value from all threads
        downloaded += ((long) newValue - (long) oldValue);

        long elapsedTime = System.nanoTime() - startDownloadTime;
        long allTimeForDownload = 0;

        String byteProgressText = formatter.byteConvert(downloaded, fileLength);
        if (downloaded >= 1) allTimeForDownload = (elapsedTime * fileLength) / downloaded;
        double remainingTime = (allTimeForDownload - elapsedTime) * 1.0E-9;

        // reset download bar area
        if ((long) newValue == 0) {
            remainingTime = 0;
            byteProgressText = "";
            downloaded = 0;
        }
        // show Notification when download is complete.
        if (downloaded == fileLength) {
            downloadButton.setVisible(true);
            Notification.showDialog("download complete");
        }
        String remainTime = formatter.updateRemainingTime(remainingTime);
        //set message to components
        downloadTimeLabel.setText(remainTime);
        percentProgress.setText(byteProgressText);
    }

    /**
     * Get the download task array.
     *
     * @return download task array.
     */
    public DownloadTask[] getTasks() {
        return tasks;
    }
}
