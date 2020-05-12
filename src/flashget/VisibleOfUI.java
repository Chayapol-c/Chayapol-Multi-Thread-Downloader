package flashget;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.util.Map;

/**
 *
 *
 * @author Chayapol 6210545947
 */
public class VisibleOfUI {
    private Map<Button,Boolean> buttons;

    public static void threadAreaControl(boolean status,ProgressBar[] threadProgresses, Label threadTextLabel) {
        for (ProgressBar bar : threadProgresses) {
            bar.setVisible(status);
        }
        threadTextLabel.setVisible(status);
    }

    public void setButtons(Map<Button, Boolean> buttons) {
        this.buttons = buttons;
    }

//    public static void buttonControl(Button[] button , boolean[] command){
//       for(int i = 0 ; )
//    }
}


