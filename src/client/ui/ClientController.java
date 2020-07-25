package client.ui;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.Timer;
import java.util.TimerTask;

public class ClientController {

    @FXML private AnchorPane anchorPane;
    @FXML private TextArea playerList;
    @FXML private Label number;
    @FXML private ToggleButton seven;
    @FXML private ToggleButton six;
    @FXML private ToggleButton five;
    @FXML private ToggleButton four;
    @FXML private ToggleButton three;
    @FXML private ToggleButton two;
    @FXML private ToggleButton one;
    @FXML private ToggleButton zero;

    private final ClientModel model = new ClientModel();
    private final Client client = new Client();
    private final Timer timer = new Timer();

    @FXML
    void initialize(){
        anchorPane.setBackground(new Background(new BackgroundImage(
                new Image("client/image/background.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT)));
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                playerList.setText(client.getPlayerList());
            }
        },0, 1000);
        generateNumber();
        seven.setOnAction(actionEvent ->{ model.switchSelected(0); checkNumber(seven); });
        six.setOnAction(actionEvent ->  { model.switchSelected(1); checkNumber(six); });
        five.setOnAction(actionEvent -> { model.switchSelected(2); checkNumber(five); });
        four.setOnAction(actionEvent -> { model.switchSelected(3); checkNumber(four); });
        three.setOnAction(actionEvent ->{ model.switchSelected(4); checkNumber(three); });
        two.setOnAction(actionEvent ->  { model.switchSelected(5); checkNumber(two); });
        one.setOnAction(actionEvent ->  { model.switchSelected(6); checkNumber(one); });
        zero.setOnAction(actionEvent -> { model.switchSelected(7); checkNumber(zero); });
        client.start();
    }

    /**
     * Сгенерировать новое число
     */
    private void generateNumber(){
        int num = (int)(Math.random()*255+1);
        model.setRight(Integer.toBinaryString(num));
        number.setText(String.valueOf(num));
    }

    /**
     * Изменить текст 0 на 1 и проверить решение
     */
    private void checkNumber(ToggleButton button){
        if (button.getText().equals("0")) {
            button.setText("1");
        } else {
            button.setText("0");
        }

        if(model.checkRight()){
            client.upScore();
            generateNumber();
            disableToggles();
        }
    }

    /**
     * Выключить все кнопки
     */
    private void disableToggles(){
        for(Node node: anchorPane.getChildren()){
            if(node.getClass().getName().equals("javafx.scene.control.ToggleButton")){
                ((ToggleButton)node).setSelected(false);
                ((ToggleButton)node).setText("0");
            }
        }
    }
}
