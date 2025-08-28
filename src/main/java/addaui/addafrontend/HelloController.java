package addaui.addafrontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController{

    @FXML
    private StackPane contents;
    @FXML
    private void initialize(){
        loadview("PersonalChats.fxml");
    }
    @FXML
    private void personal(){
        loadview("PersonalChats.fxml");
    }
    @FXML
    private void global(){
        loadview("GlobalChats.fxml");
    }
    @FXML
    private void group(){
        loadview("GroupChats.fxml");
    }

    private void loadview(String fxml){
        try{
            Pane view= FXMLLoader.load(getClass().getResource(fxml));
            view.prefWidthProperty().bind(contents.widthProperty());
            view.prefHeightProperty().bind(contents.heightProperty());
            contents.getChildren().setAll(view);
        }
        catch(IOException e){
            e.printStackTrace();
        }
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
