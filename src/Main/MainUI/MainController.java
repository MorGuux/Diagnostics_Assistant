package Main.MainUI;

import Main.DatabaseManager;
import Main.Functions;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Main.Guide;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

public class MainController  implements Initializable {

    @FXML private AnchorPane window;
    @FXML private AnchorPane diagnostics;
    @FXML private AnchorPane programming;
    @FXML private AnchorPane connections;
    @FXML private AnchorPane settings;
    @FXML private Label version;
    @FXML private Label connected;
    @FXML private ImageView diagnosticsIcon;
    @FXML private ImageView programmingIcon;
    @FXML private ImageView connectionsIcon;
    @FXML private ImageView settingsIcon;
    @FXML private ImageView logo;
    @FXML private ImageView minimizedLogo;
    @FXML private ImageView maximiseIcon;
    @FXML private ImageView minimizeIcon;
    @FXML private ImageView closeIcon;
    @FXML private TableView<Guide> diagGuides;
    @FXML private TableColumn<Guide, String> titleCol;
    @FXML private TableColumn<Guide, String> descriptionCol;
    @FXML private TableColumn<Guide, String> pathCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        version.setText("v1.0.0");
        connected.setText("Connected");

        logo.setImage(new Image(new File("images/logo_white.png").toURI().toString()));
        minimizedLogo.setImage(new Image(new File("images/logo.png").toURI().toString()));
        diagnosticsIcon.setImage(new Image(new File("images/diagnostics.png").toURI().toString()));
        programmingIcon.setImage(new Image(new File("images/programming.png").toURI().toString()));
        connectionsIcon.setImage(new Image(new File("images/connections.png").toURI().toString()));
        settingsIcon.setImage(new Image(new File("images/settings.png").toURI().toString()));
        minimizeIcon.setImage(new Image(new File("images/minimize.png").toURI().toString()));
        maximiseIcon.setImage(new Image(new File("images/maximise.png").toURI().toString()));
        closeIcon.setImage(new Image(new File("images/close.png").toURI().toString()));

        titleCol.setCellValueFactory(new PropertyValueFactory<Guide, String>("Title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Guide, String>("Description"));
        pathCol.setCellValueFactory(new PropertyValueFactory<Guide, String>("Path"));

        try
        {
            DatabaseManager.openConnection();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        ObservableList<Guide> guides = FXCollections.observableArrayList(Guide.getGuides());

        diagGuides.setItems(guides);

        diagGuides.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        diagGuides.setOnMouseClicked(MouseEvent -> {
            if (MouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                final ContextMenu contextMenu = new ContextMenu();
                final AnchorPane pane = new AnchorPane();
                MenuItem open = new MenuItem("Open guide");

                diagGuides.setContextMenu(contextMenu);
                contextMenu.getItems().addAll(open);
                contextMenu.show(pane, MouseEvent.getScreenX(), MouseEvent.getScreenY());

                open.setOnAction(actionEvent -> {
                    try {
                        diagGuides.getSelectionModel().getSelectedItem().openInDefaultProgram();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    private void openPanel(AnchorPane panel) {
        diagnostics.setVisible(false);
        programming.setVisible(false);
        connections.setVisible(false);
        settings.setVisible(false);
        panel.setVisible(true);
    }

    public void openDiagnostics() {
        openPanel(diagnostics);
    }

    public void openProgramming() {
        openPanel(programming);
    }

    public void openConnections() {
        openPanel(connections);
    }

    public void openSettings() {
        openPanel(settings);
    }

    public void maximise() {
        Functions.maximise(window);
    }

    public void minimize() {
        Functions.minimize(window);
    }

    public void exit() {
        System.exit(1);
    }

}
