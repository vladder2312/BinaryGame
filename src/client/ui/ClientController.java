package client.ui;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

public class ClientController {

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
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getPlayerList();
            }
        },0, 1000);
        generateNumber();
        seven.setOnAction(actionEvent ->{ model.switchSelected(0); checkNumber(0); });
        six.setOnAction(actionEvent ->  { model.switchSelected(1); checkNumber(1); });
        five.setOnAction(actionEvent -> { model.switchSelected(2); checkNumber(2); });
        four.setOnAction(actionEvent -> { model.switchSelected(3); checkNumber(3); });
        three.setOnAction(actionEvent ->{ model.switchSelected(4); checkNumber(4); });
        two.setOnAction(actionEvent ->  { model.switchSelected(5); checkNumber(5); });
        one.setOnAction(actionEvent ->  { model.switchSelected(6); checkNumber(6); });
        zero.setOnAction(actionEvent -> { model.switchSelected(7); checkNumber(7); });
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
    private void checkNumber(int position){
        ToggleButton tb;
        switch (position){
            case 0 -> tb = seven;
            case 1 -> tb = six;
            case 2 -> tb = five;
            case 3 -> tb = four;
            case 4 -> tb = three;
            case 5 -> tb = two;
            case 6 -> tb = one;
            case 7 -> tb = zero;
            default -> { return; }
        }
        if (tb.getText().equals("0")) {
            tb.setText("1");
        } else {
            tb.setText("0");
        }

        if(model.checkRight()){
            client.upScore();
            generateNumber();
            disableToggles();
        }
    }

    /**
     * Получить данные о игроках с сервера
     */
    private void getPlayerList(){
        playerList.setText("");
        for(String line : client.getStringList()){
            playerList.setText(playerList.getText()+"\n"+line);
        }
    }

    /**
     * Выключить все кнопки
     */
    private void disableToggles(){
        seven.setSelected(false);
        seven.setText("0");
        six.setSelected(false);
        six.setText("0");
        five.setSelected(false);
        five.setText("0");
        four.setSelected(false);
        four.setText("0");
        three.setSelected(false);
        three.setText("0");
        two.setSelected(false);
        two.setText("0");
        one.setSelected(false);
        one.setText("0");
        zero.setSelected(false);
        zero.setText("0");
        model.clearSelected();
    }
}
