package addaui.addafrontend;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;


public class GroupChatsController implements Initializable {
    @FXML
    private TextField text;
    @FXML
    private ListView<String> chatbox;

    @FXML
    void sender(MouseEvent event){
        String message= text.getText().trim();
        if (!message.isEmpty()){
            chatbox.getItems().add(message);
            text.clear();
            chatbox.scrollTo(chatbox.getItems().size()-1);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text.setOnKeyPressed(event->{
            if (event.getCode()== KeyCode.ENTER){
                String message= text.getText().trim();
                if(!message.isEmpty()){
                    chatbox.getItems().add(message);
                    text.clear();
                    chatbox.scrollTo(chatbox.getItems().size()-1);
                }
            }
        });
       chatbox.setCellFactory( lv-> new ListCell<>(){
           @Override
           protected void updateItem(String item, boolean empty){
               super.updateItem(item, empty);
               if (item==null || empty){
                   setGraphic(null);
               }
               else {
                   Label label= new Label(item);
                   label.setStyle("-fx-font-family: Calibri;"+
                           "-fx-font-size: 16px;"+
                           "-fx-padding: 8 12 8 12;"+
                           "-fx-text-fill: Black;"+
                           "-fx-background-color: rgb(117,235,170);"+
                           "-fx-background-radius: 8;"
                   );
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
}
