package Main.Guide;

import Main.Functions;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

public class GuideViewerController implements Initializable {

    @FXML
    private TitledPane lblGuideTitle;
    @FXML
    private TextField lblTitle;
    @FXML
    private TextArea lblDescription;
    @FXML
    private Label lblPath;
    private Guide selectedGuide;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setSelectedGuide(Guide guide) {
        this.selectedGuide = guide;
        lblGuideTitle.setText(selectedGuide.getTitle());
        lblTitle.setText(selectedGuide.getTitle());
        lblDescription.setText(selectedGuide.getDescription());
        lblPath.setText(selectedGuide.getPath());
    }

    public void browse() {
        FileChooser fileChooser = new FileChooser();
        File result = fileChooser.showOpenDialog(null);

        if (result != null) {
            if (selectedGuide == null) {
                lblPath.setText(result.getAbsolutePath());
            } else {
                selectedGuide.setPath(result.getAbsolutePath());
            }
        }
    }

    public void save() throws SQLException
    {
        if (!lblTitle.getText().equals("") && !lblDescription.getText().equals("") && !lblPath.getText().equals("")) {
            String title = lblTitle.getText();
            String description = lblDescription.getText();
            String path = lblPath.getText();

            if (selectedGuide == null) {
                Guide guide = new Guide(title, description, path);
                Guide.addNewGuide(guide);
                System.out.println("Saved guide");
            } else {
                selectedGuide.setTitle(title);
                selectedGuide.setDescription(description);
                selectedGuide.setPath(path);
                System.out.println("edited guide");
            }
        }
    }

    public void cancel() {
        Functions.close(lblGuideTitle);
        Functions.mainPane.setEffect(null);
    }

}
