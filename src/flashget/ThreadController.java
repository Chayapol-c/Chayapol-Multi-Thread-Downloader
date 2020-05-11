package flashget;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadController {

    private long fileLength;

    private DownloadTask[] tasks;

    public ThreadController(long fileLength, ProgressBar[] threadProgresses, Label percentProgress, Label downloadTimeLabel, ProgressBar downloadBar) {
        this.fileLength = fileLength;
        this.threadProgresses = threadProgresses;
        this.percentProgress = percentProgress;
        this.downloadTimeLabel = downloadTimeLabel;
        this.downloadBar = downloadBar;

    }

    private ProgressBar[] threadProgresses;
    private ProgressBar downloadBar;

    public void manipulate(int threadNumber, File out, String urlName) {
        ExecutorService excecutor = Executors.newFixedThreadPool(5);
         long KILOBYTE = 4096 * 4;
         long chunk = (fileLength / (KILOBYTE)) + 1;
        long size = ((chunk / threadNumber)) * (KILOBYTE);
        startDownloadTime = System.nanoTime();

                tasks = new DownloadTask[threadNumber];

                       // Create and addListener to DownloadTask
                        for (int i = 0; i < threadNumber; i++) {
            tasks[i] = new DownloadTask(urlName, out, size * i, size);
            if (i == threadNumber - 1) {
                tasks[i] = new DownloadTask(urlName, out, size * i, fileLength - (size * i));

            }
            threadProgresses[i].progressProperty().bind(tasks[i].progressProperty());
            tasks[i].valueProperty().addListener(this::valueChange);
            excecutor.execute(tasks[i]);

        }
        downloadBar.progressProperty().bind(tasks[0].progressProperty().multiply(0.2).add(tasks[1].progressProperty().multiply(0.2)).add(
                tasks[2].progressProperty().multiply(0.2).add(tasks[3].progressProperty().multiply(0.2).add
                        (
                                tasks[4].progressProperty().multiply(0.2)))));
        excecutor.shutdown();

    }


    private long downloaded;

    private long startDownloadTime;

    private Label percentProgress;

    private Label downloadTimeLabel;


    private void valueChange(ObservableValue observableValue, Object oldValue, Object newValue) {
                // change null to 0 for old value
                 if (oldValue == null) oldValue = 0L;
                //sum value from all threads
                downloaded += ((long) newValue - (long) oldValue);

                 long elapsedTime = System.nanoTime() - startDownloadTime;
         long allTimeForDownload = 0;

                String byteProgressText = String.format("%s / %s", OutputFormat.convertByte(downloaded), OutputFormat.convertByte(fileLength));
        if (downloaded >= 1) allTimeForDownload = (elapsedTime * fileLength) / downloaded;
         double remainingTime = (allTimeForDownload - elapsedTime) * 1.0E-9;

                       // reset download bar area
                         if ((long) newValue == 0) {
            remainingTime = 0;
            byteProgressText = "";
            downloaded = 0;
        }

                String remainTime = OutputFormat.updateRemainingTime(remainingTime);
                //set message to components
                downloadTimeLabel.setText(remainTime);
        percentProgress.setText(byteProgressText);

    }



    public DownloadTask[] getTasks() {
         return tasks;

    }


}
