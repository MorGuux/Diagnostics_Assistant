package Main.MainUI;

import Main.DatabaseManager;
import Main.Functions;
import Main.Guide.GuideViewerController;
import Main.Main;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Main.Guide.Guide;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable {

    @FXML
    private AnchorPane window;
    @FXML
    private AnchorPane diagnostics;
    @FXML
    private AnchorPane programming;
    @FXML
    private AnchorPane connections;
    @FXML
    private AnchorPane settings;
    @FXML
    private Label version;
    @FXML
    private Label connected;
    @FXML
    private ImageView diagnosticsIcon;
    @FXML
    private ImageView programmingIcon;
    @FXML
    private ImageView connectionsIcon;
    @FXML
    private ImageView settingsIcon;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView minimizedLogo;
    @FXML
    private ImageView maximiseIcon;
    @FXML
    private ImageView minimizeIcon;
    @FXML
    private ImageView closeIcon;
    @FXML
    public JFXButton edit;
    @FXML
    public JFXButton delete;
    @FXML
    public TableView<Guide> diagGuides;
    @FXML
    private TableColumn<Guide, String> titleCol;
    @FXML
    private TableColumn<Guide, String> descriptionCol;

    public Guide getSelectedGuide() {
        return this.diagGuides.getSelectionModel().getSelectedItem();
    }

    private ObservableList<Guide> guides;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Functions.mainPane = window;
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

        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));

        try {
            DatabaseManager.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        edit.disableProperty().bind(
                Bindings.isNull(diagGuides.getSelectionModel().selectedItemProperty())
        );
        delete.disableProperty().bind(
                Bindings.isNull(diagGuides.getSelectionModel().selectedItemProperty())
        );

        guides = FXCollections.observableArrayList(Guide.getGuides());
        diagGuides.setItems(guides);
        diagGuides.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        diagGuides.setOnMouseClicked(MouseEvent -> {
            if (MouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                final ContextMenu contextMenu = new ContextMenu();
                final AnchorPane pane = new AnchorPane();
                MenuItem open = new MenuItem("Open guide");
                MenuItem edit = new MenuItem("Edit guide");
                MenuItem delete = new MenuItem("Delete guide");

                diagGuides.setContextMenu(contextMenu);
                contextMenu.getItems().addAll(open, edit, delete);
                contextMenu.show(pane, MouseEvent.getScreenX(), MouseEvent.getScreenY());

                open.disableProperty().bind(
                        Bindings.isNull(diagGuides.getSelectionModel().selectedItemProperty())
                );
                edit.disableProperty().bind(
                        Bindings.isNull(diagGuides.getSelectionModel().selectedItemProperty())
                );
                delete.disableProperty().bind(
                        Bindings.isNull(diagGuides.getSelectionModel().selectedItemProperty())
                );
                open.setOnAction(e -> {

                    try {
                        diagGuides.getSelectionModel().getSelectedItem().openInDefaultProgram();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                edit.setOnAction(e -> loadGuide());
                delete.setOnAction(e -> delete());
            } else if (MouseEvent.getButton().equals(MouseButton.PRIMARY) && MouseEvent.getClickCount() == 2) {
                try {
                    diagGuides.getSelectionModel().getSelectedItem().openInDefaultProgram();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addGuide() {
        diagGuides.getSelectionModel().clearSelection();
        loadGuide();
    }

    public void loadGuide() {
        FXMLLoader loader = Main.getFXML("Guide/guideViewer.fxml");

        Parent guideWindow = null;
        try {
            assert loader != null;
            guideWindow = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.getPrimaryStage());
        assert guideWindow != null;
        dialog.setScene(new Scene(guideWindow));
        dialog.initStyle(StageStyle.UNDECORATED);

        if (getSelectedGuide() != null) {
            dialog.setTitle(getSelectedGuide().getTitle());
            GuideViewerController guideViewerController = loader.getController();
            guideViewerController.setSelectedGuide(getSelectedGuide());
        }
        dialog.show();
        blur();
    }

    private void blur() {
        ColorAdjust adj = new ColorAdjust(0, 0, 0, 0);
        GaussianBlur blur = new GaussianBlur(3);
        adj.setInput(blur);
        window.setEffect(adj);
    }

    public void delete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this guide?", ButtonType.YES, ButtonType.NO);
        blur();
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            if (getSelectedGuide() != null) {
                if (Guide.deleteGuide(getSelectedGuide())) {
                    guides.remove(getSelectedGuide());
                }
            }
        }
        window.setEffect(null);
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
