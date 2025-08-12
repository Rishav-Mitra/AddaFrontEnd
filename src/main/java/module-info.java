module addaui.addafrontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;


    opens addaui.addafrontend to javafx.fxml;
    exports addaui.addafrontend;
}