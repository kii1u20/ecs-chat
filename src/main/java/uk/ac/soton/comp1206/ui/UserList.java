package uk.ac.soton.comp1206.ui;

import java.util.ArrayList;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class UserList extends VBox {
    private TextFlow userList = new TextFlow();
    private ArrayList<String> usersArray = new ArrayList<String>();

    public UserList(int width) {
        this.setPrefWidth(width);
        this.setSpacing(20);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setAlignment(Pos.TOP_CENTER);
        this.getStyleClass().add("userList");

        String imagePath = this.getClass().getResource("/ECS.png").toExternalForm();
        ImageView image = new ImageView(imagePath);
        image.setFitWidth(100);
        image.setFitHeight(this.getHeight());
        image.setPreserveRatio(true);

        this.getChildren().add(image);
        this.getChildren().add(userList);
    }

    public void addUser(String username) {
        Text user = new Text(username + "\n");

        if (usersArray.contains(user.getText())) return;
        usersArray.add(user.getText());
        userList.getChildren().add(user);
    }
}
