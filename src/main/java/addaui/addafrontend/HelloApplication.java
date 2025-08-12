package addaui.addafrontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException{
        Parent root= FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Image icon= new Image("C:\\Users\\mrish\\AddaFrontEnd\\src\\main\\resources\\addaui\\addafrontend\\logo.png");
        stage.getIcons().add(icon);
        stage.setTitle("Hello!");
        Scene scene= new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}