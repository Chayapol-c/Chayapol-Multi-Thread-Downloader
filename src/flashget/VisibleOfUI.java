package flashget;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * Controller UI. Firstly ,I use this class for setVisible components which have same visibility or
 * in same area.
 *
 * @author Chayapol 6210545947
 */
public class VisibleOfUI {

    /**
     * Control visible of thread area.
     *
     * @param status           is boolean variable that you want to set.
     * @param threadProgresses is array of ProgressBar in thread area.
     * @param threadTextLabel  is Label which has text "Thread: ".
     */
    public static void threadAreaControl(boolean status, ProgressBar[] threadProgresses, Label threadTextLabel) {
        for (ProgressBar bar : threadProgresses) {
            bar.setVisible(status);
        }
        threadTextLabel.setVisible(status);
    }

}


