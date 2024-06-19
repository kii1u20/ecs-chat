package uk.ac.soton.comp1206.ui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.URL;
import java.util.ResourceBundle;

import uk.ac.soton.comp1206.App;
import uk.ac.soton.comp1206.utility.Utility;

public class Options {
    App app;
    private Stage stage;
    Scene scene = null;
    Parent root = null;
    private static final Logger logger = LogManager.getLogger(LoginWindow.class);
    @FXML Button closeButton;
    @FXML CheckBox muteChechBox;

    public Options(App app) {
        this.app = app;
        this.stage = new Stage();

        try {
            //Instead of building this GUI programmatically, we are going to use FXML
            var loader = new FXMLLoader(getClass().getResource("/settings.fxml"));

            //TODO: Fill in login.fxml

            //Link the GUI in the FXML to this class
            loader.setController(this);
            root = loader.load();
        } catch (Exception e) {
            //Handle any exceptions with loading the FXML
            logger.error("Unable to read file: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        scene = new Scene(root);

        muteChechBox.selectedProperty().bindBidirectional(Utility.audioEnabledProperty());
        closeButton.setOnAction(e-> {stage.hide();});
    }

	public Scene getScene() {
		return this.scene;
	}

    public Boolean isShowing() {
        return stage.isShowing();
    }

	public void show() {
        stage.setScene(scene);

        stage.show();
        stage.centerOnScreen();
	}
}
