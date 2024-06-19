package uk.ac.soton.comp1206.ui;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.ac.soton.comp1206.App;
import uk.ac.soton.comp1206.network.Communicator;
import uk.ac.soton.comp1206.utility.Utility;

import java.security.Key;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.EventListener;
import java.util.concurrent.Flow;

/**
 * Chat window which will display chat messages and a way to send new messages
 */
public class ChatWindow {

    private static final Logger logger = LogManager.getLogger(ChatWindow.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    private final App app;
    private final Scene scene;
    private final Communicator communicator;

    private TextFlow textFlow;
    private ScrollPane sPane;
    private TextField textBox;
    private Button sendButton;
    private Button optionsButton;
    private HBox horizontalPane;
    private UserList userList;

    /**
     * Create a new Chat Window, linked to the main App and the Communicator
     * 
     * @param app          the main app
     * @param communicator the communicator
     */
    public ChatWindow(App app, Communicator communicator) {
        this.app = app;
        this.communicator = communicator;

        // Setup scene with a border pane
        var pane = new BorderPane();
        this.scene = new Scene(pane, 640, 480);
        textFlow = new TextFlow();
        textBox = new TextField();
        userList = new UserList(200);
        sendButton = new Button();
        sendButton.setText("Send");
        optionsButton = new Button();
        optionsButton.setText("Options");
        pane.setTop(optionsButton);
        sPane = new ScrollPane();
        horizontalPane = new HBox();
        sPane.setContent(textFlow);
        sPane.setFitToWidth(true);
        sPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        pane.setCenter(sPane);
        pane.setRight(userList);
        textBox.setPrefWidth(599);
        horizontalPane.getChildren().add(textBox);
        horizontalPane.getChildren().add(sendButton);
        pane.setBottom(horizontalPane);
        HBox.setHgrow(textBox, Priority.ALWAYS);
        HBox.setHgrow(sendButton, Priority.NEVER);

        sendButton.setOnAction(e -> {
            sendCurrentMessage(textBox.getText());
            textBox.clear();
        });

        optionsButton.setOnAction(e-> {app.openOptions();});

        textBox.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                sendCurrentMessage(textBox.getText());
                textBox.clear();
            }
        });

        // Link the communicator to this window
        communicator.setWindow(this);

        communicator.addListener((message) -> {
            Platform.runLater(() -> this.receiveMessage(message));
        });

        // TODO: Set up the GUI to be more useful than this...

        // Set the stylesheet for this window
        String css = this.getClass().getResource("/chat.css").toExternalForm();
        scene.getStylesheets().add(css);
    }

    /**
     * Handle an incoming message from the Communicator
     * 
     * @param message The message that has been received, in the form User:Message
     */
    public void receiveMessage(String message) {
        // TODO: Handle incoming messages
        String[] msg = message.split(":");
        Text userName = new Text(msg[0]);
        Text text = new Text(": " + msg[1] + "\n");
        text.getStyleClass().add("messages");
        // userList.addUser(userName.getText());
        if (userName.getText().equals(app.getUsername() + ":")) {
            userName.getStyleClass().add("my_nickname");
        } else {
            userName.getStyleClass().add("nickname");
        }
        int[] currentTime = new int[2];
        currentTime[0] = LocalTime.now().getHour();
        currentTime[1] = LocalTime.now().getMinute();
        Text timestamp = new Text(String.valueOf("[" + currentTime[0]) + ":" + String.valueOf(currentTime[1]) + "]");
        timestamp.getStyleClass().add("timestamp");
        textFlow.getChildren().add(timestamp);
        textFlow.getChildren().add(userName);
        textFlow.getChildren().add(text);
        // textFlow.applyCss();
        textFlow.layout();
        sPane.applyCss();
        sPane.layout();
        sPane.setVvalue(1.0f);
        Utility.playAudio("incoming.mp3");
    }

    /**
     * Send an outgoing message from the Chatwindow
     * @param text The text of the message to send to the Communicator
     */
    private void sendCurrentMessage(String text) {
        if (text != "") {
            communicator.send(app.getUsername() + ": " + text);
        }
    }

    /**
     * Get the scene contained inside the Chat Window
     * @return
     */
    public Scene getScene() {
        return scene;
    }
}
