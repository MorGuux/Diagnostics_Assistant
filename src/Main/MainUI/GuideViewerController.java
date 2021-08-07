package Main.MainUI;

import Main.DatabaseManager;
import Main.Functions;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import Main.Guide;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class GuideViewerController  implements Initializable {

    @FXML private TitledPane lblGuideTitle;
    @FXML private TextField lblTitle;
    @FXML private TextArea lblDescription;
    @FXML private Label lblPath;
    private Guide selectedGuide;

    public void setSelectedGuide(Guide guide) {
        this.selectedGuide = guide;
        lblGuideTitle.setText(selectedGuide.getTitle());
        lblTitle.setText(selectedGuide.getTitle());
        lblDescription.setText(selectedGuide.getDescription());
        lblPath.setText(selectedGuide.getPath());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }
}
