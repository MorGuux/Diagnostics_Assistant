package Main;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Functions {

    public static void drag(AnchorPane window) {
        Stage thisWindow = (Stage) window.getScene().getWindow();
        window.setOnMousePressed(pressEvent -> window.setOnMouseDragged(dragEvent -> {
            thisWindow.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            thisWindow.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
    }

    public static void open(String fxml, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Functions.class.getClassLoader().
                getResource(fxml));
        Parent window = fxmlLoader.load();
        Stage newWindow = new Stage();

        newWindow.setAlwaysOnTop(true);
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.setTitle(title);
        newWindow.setScene(new Scene(window));
        newWindow.show();
    }

    public static void maximise(AnchorPane window) {
        Stage thisWindow = (Stage) window.getScene().getWindow();
        thisWindow.setMaximized(!thisWindow.isMaximized());
    }

    public static void minimize(AnchorPane window) {
        Stage thisWindow = (Stage) window.getScene().getWindow();
        thisWindow.setIconified(true);
    }

    public static void close(AnchorPane window) {
        Stage thisWindow = (Stage) window.getScene().getWindow();
        thisWindow.close();
    }

}
