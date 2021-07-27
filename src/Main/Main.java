package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception{
        Parent newWindow = FXMLLoader.load(getClass().getResource("MainUI/main.fxml"));

        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(new Scene(newWindow, 1000, 700));
        ResizeHelper.addResizeListener(window);
        window.show();
    }

}
