package modle;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.awt.*;

/**
 * @author RM.LasanthaRanga@gmail.com
 */
public class Allert {

    public static void notificationGood(String title, String mg) {
        Toolkit.getDefaultToolkit().beep();
        Image image = new Image("/grafics/Good_100px.png");
        Notifications.create()
                .title(title)
                .text(mg)
                .hideAfter(Duration.seconds(3))
                .position(Pos.TOP_RIGHT)
                .graphic(new ImageView(image))
                .darkStyle()
                .show();
    }


    public static void notificationError(String title, String mg) {
        Toolkit.getDefaultToolkit().beep();
        Image image = new Image("/grafics/Boring_100px.png");
        Notifications.create()
                .title(title)
                .text(mg)
                .hideAfter(Duration.seconds(3))
                .position(Pos.TOP_RIGHT)
                .graphic(new ImageView(image))
                .darkStyle()
                .show();
    }

    public static void notificationInfo(String title, String mg) {
        Toolkit.getDefaultToolkit().beep();
        Image image = new Image("/grafics/Fingers_100px.png");
        Notifications.create()
                .title(title)
                .text(mg)
                .hideAfter(Duration.seconds(3))
                .position(Pos.TOP_RIGHT)
                .graphic(new ImageView(image))
                .darkStyle()
                .show();
    }

    public static void notificationWorning(String title, String mg) {
        Toolkit.getDefaultToolkit().beep();
        Image image = new Image("/grafics/info.png");
        Notifications.create()
                .title(title)
                .text(mg)
                .hideAfter(Duration.seconds(3))
                .position(Pos.TOP_RIGHT)
                .graphic(new ImageView(image))
                .darkStyle()
                .show();
    }

    public static void notificationConnection(String title, String mg) {
        Toolkit.getDefaultToolkit().beep();
        Image image = new Image("/grafics/disconnect.png");
        Notifications.create()
                .title(title)
                .text(mg)
                .hideAfter(Duration.seconds(3))
                .position(Pos.TOP_RIGHT)
                .graphic(new ImageView(image))
                .darkStyle()
                .show();
    }

}
