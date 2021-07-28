package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) throws SQLException {

        //region Database Connection
        DatabaseManager.openConnection();
        ArrayList<DatabaseClasses.Guide> guides = DatabaseManager.getGuides();

        //endregion

        launch(args);
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
    }

}
