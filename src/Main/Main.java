package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class Main extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) throws SQLException, IOException {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static FXMLLoader getFXML(String path) {
        try {
            return new FXMLLoader(Main.class.getResource(path));
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void start(Stage window) throws Exception {
        Parent newWindow = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainUI/main.fxml")));
        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(new Scene(newWindow, 1000, 700));

        ResizeHelper.addResizeListener(window);
        window.setMinWidth(1000);
        window.setMinHeight(700);
        window.show();
        primaryStage = window;
    }

}
