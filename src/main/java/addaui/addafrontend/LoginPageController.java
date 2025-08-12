package addaui.addafrontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.input.MouseEvent;

public class LoginPageController {
    @FXML
    private void goto_chats(MouseEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene= new Scene(root);
        Stage stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
