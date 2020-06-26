package server.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import server.Server;

public class ServerController {

    @FXML private TextArea textArea;
    @FXML private Label stateLabel;

    private final Server server = new Server();

    @FXML
    private void initialize(){
        server.start();
        stateLabel.setText("Запущен");
    }
}
