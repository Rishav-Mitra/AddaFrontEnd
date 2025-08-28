package addaui.addafrontend;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.*;

public class PersonalChatsController implements Initializable {

    @FXML
    private FontIcon recordButton;
    private boolean isRecording = false;
    private AudioRecorder recorder = new AudioRecorder();

    @FXML
    private FontIcon emojipanel;

    @FXML
    private TextField Text;

    @FXML
    private ListView<Object> chatbox;

    @FXML
    private ImageView profilepic;

    private void toggleRecording() {
        if (!isRecording) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startRecording() {
        isRecording = true;
        recordButton.setIconLiteral("fas-stop");
        recordButton.setIconSize(20);
        System.out.println("Recording started...");
        String filename = "voice_" + System.currentTimeMillis() + ".wav";
        recorder.startRecording(filename);
    }

    private void stopRecording() {
        isRecording = false;
        recordButton.setIconLiteral("fas-microphone");
        recordButton.setIconSize(20);
        System.out.println("Recording stopped...");
        recorder.stopRecording();
        File audioFile = recorder.getAudioFile();

        if (audioFile != null && audioFile.exists()) {
            addAudioMessage(audioFile);
        }
    }

    private void addAudioMessage(File audioFile) {
        Media media = new Media(audioFile.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
       FontIcon playButton= new FontIcon("fas-play");
       playButton.setIconSize(18);
       playButton.setAccessibleRole(AccessibleRole.BUTTON);
        playButton.setOnMouseClicked(e -> {
            MediaPlayer.Status status= player.getStatus();
            if (status== MediaPlayer.Status.PLAYING){
                player.pause();
                playButton.setIconLiteral("fas-play");
            }
            else {
                player.play();
                playButton.setIconLiteral("fas-pause");
            }
        });
        Slider progress= new Slider();
        progress.setPrefWidth(150);
        Label timestamp = new Label("00:00 / 00:00");

        FontIcon speedIcon= new FontIcon("fas-forward");
        speedIcon.setIconSize(16);
        speedIcon.setAccessibleRole(AccessibleRole.BUTTON);

        speedIcon.setOnMouseClicked(e ->{
            if (player.getRate()==1.0){
                player.setRate(1.5);
                timestamp.setTextFill(Color.DARKBLUE);
                Tooltip.install(speedIcon, new Tooltip("1.5x speed"));
            } else if (player.getRate()== 1.5) {
                player.setRate(2.0);
                timestamp.setTextFill(Color.DARKRED);
                Tooltip.install(speedIcon, new Tooltip("2x speed"));
            }
            else{
                player.setRate(1.0);
                timestamp.setTextFill(Color.BLACK);
                Tooltip.install(speedIcon, new Tooltip("Normal speed"));
            }
        });

        HBox container= new HBox(5, playButton, progress, timestamp, speedIcon);
        container.setAlignment(Pos.CENTER_RIGHT);

        player.setOnReady(()->{
            double total= media.getDuration().toSeconds();
            progress.setMax(total);
            timestamp.setText("00:00 / "+formatTime(total));
        });

        player.setOnEndOfMedia(() -> {
            player.stop(); // ensures media is back at start
            progress.setValue(0);
            playButton.setIconLiteral("fas-play");
            timestamp.setText("00:00 / " + formatTime(media.getDuration().toSeconds()));
        });

        player.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!progress.isValueChanging()) {
                progress.setValue(newTime.toSeconds());
            }
            double current = newTime.toSeconds();
            double total = media.getDuration().toSeconds();
            timestamp.setText(formatTime(current) + " / " + formatTime(total));
        });
        progress.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
            if (!isChanging) {
                player.seek(javafx.util.Duration.seconds(progress.getValue()));
            }
        });

        progress.setOnMouseReleased(e -> {
            player.seek(javafx.util.Duration.seconds(progress.getValue()));
        });

        chatbox.getItems().add(container);
        chatbox.scrollTo(chatbox.getItems().size() - 1);

    }

    private String formatTime(double seconds){
        int min= (int) seconds / 60;
        int secs= (int) seconds % 60;
        return String.format("%02d:%02d", min, secs);
    }


    @FXML
    void Sender(MouseEvent event) {
        String message = Text.getText().trim();
        if (!message.isEmpty()) {
            chatbox.getItems().add(message);
            Text.clear();
            chatbox.scrollTo(chatbox.getItems().size() - 1);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Record button toggle
        recordButton.setIconLiteral("fa-microphone");
        recordButton.setOnMouseClicked(e -> toggleRecording());
        recordButton.setStyle("-fx-cursor: hand;");
        
        ContextMenu emojiMenu = new ContextMenu();
        MenuItem smile = new MenuItem("🙂");
        MenuItem hotface = new MenuItem("🥵");
        MenuItem laughingface = new MenuItem("😂");
        MenuItem rolling = new MenuItem("🤣");

        smile.setOnAction(e -> Text.appendText("🙂"));
        hotface.setOnAction(e -> Text.appendText("🥵"));
        laughingface.setOnAction(e -> Text.appendText("😂"));
        rolling.setOnAction(e -> Text.appendText("🤣"));

        emojiMenu.getItems().addAll(smile, hotface, laughingface, rolling);
        emojipanel.setOnMouseClicked(e -> {
            emojiMenu.show(emojipanel, e.getScreenX(), e.getScreenY());
        });

        // Enter to send
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

        chatbox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else if (item instanceof String) {
                    Label label = new Label((String) item);
                    label.setStyle("-fx-font-family: Calibri;" +
                            "-fx-font-size: 16px;" +
                            "-fx-padding: 8 12 8 12;" +
                            "-fx-text-fill: Black;" +
                            "-fx-background-color: rgb(117,235,170);" +
                            "-fx-background-radius: 8;");
                    label.setMaxWidth(300);
                    label.setWrapText(true);
                    HBox container = new HBox(label);
                    container.setAlignment(Pos.CENTER_RIGHT);
                    setGraphic(container);
                } else if (item instanceof HBox) { // Audio bubble
                    setGraphic((HBox) item);
                }
            }
        });
    }
}
