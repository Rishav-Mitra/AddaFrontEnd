package addaui.addafrontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private TextField Text;

    @FXML
    private ListView<String> chatbox;

    @FXML
    private ImageView profilepic;

    @FXML
    void Sender(MouseEvent event) {  String message = Text.getText().trim();
        if (!message.isEmpty()) {
            chatbox.getItems().add(message);
            Text.clear();
            chatbox.scrollTo(chatbox.getItems().size() - 1);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Text.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String message = Text.getText().trim();
                if (!message.isEmpty()) {
                    chatbox.getItems().add(message);
                    Text.clear();
                    chatbox.scrollTo(chatbox.getItems().size() - 1);
                }
            }
        });


        Text.setOnAction(event -> {
            String message = Text.getText().trim();
            if (!message.isEmpty()) {
                chatbox.getItems().add(message);
                Text.clear();
                chatbox.scrollTo(chatbox.getItems().size() - 1);
            }
        });
        chatbox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);

                } else {
                    Label label= new Label(item);
                    label.setStyle("-fx-background-color: transparent; " +
                            "-fx-text-fill: black; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-family: Calibri; " +
                            "-fx-padding: 8 12 8 12;" );
                    label.setMaxWidth(300);
                    label.setWrapText(true);
                    HBox container= new HBox(label);
                    container.setPrefWidth(200);
                    container.setAlignment(Pos.CENTER_RIGHT);
                    setGraphic(container);
                }
            }
        });
    }
   @FXML
    void LogOut(MouseEvent event) throws IOException{
        Parent root= FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene scene= new Scene(root);
        Stage stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
   }
}
