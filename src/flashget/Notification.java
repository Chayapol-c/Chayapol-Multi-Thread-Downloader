package flashget;

import javafx.scene.control.Alert;

/**
 * Notification when somethings occur.
 *
 * @author Chayapol 6210545947
 */

public class Notification {

//    private Alert aleart ;
//    public Notification(){
//        this.aleart = new Alert(Alert.AlertType.INFORMATION);
//    }

    /**
     * Show an alert window .
     *
     * @param message is an alert's detail.
     */
    public static void showDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
