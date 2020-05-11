package flashget;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class VisibleOfUI {
    public static void threadAreaControl(boolean status,ProgressBar[] threadProgresses, Label threadTextLabel) {
        for (ProgressBar bar : threadProgresses) {
            bar.setVisible(status);
        }
        threadTextLabel.setVisible(status);
    }
}


