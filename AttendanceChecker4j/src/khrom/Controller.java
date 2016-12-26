package khrom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class Controller implements EventHandler<ActionEvent> {
    public static String DB_NAME = "gakuseki.db";
    private Stage stage;
    @FXML
    private TextField gakuseki;
    @FXML
    private TabPane tabpane;
    @FXML
    private Label gakusekiNumber;
    @FXML
    private ListView listView;
    @FXML
    private ChoiceBox fromHour, fromMinute, fromSecond, toHour, toMinute, toSecond;
    @FXML
    private DatePicker fromDate, toDate;
    @FXML
    private Label history;
    @FXML
    private ToggleButton fullscreen;
    @FXML
    private Button reg;
    @FXML
    private Button binbutton, txtbutton;


    /**
     * 選択肢の追加やアクションのセットをここでまとめて行う
     *
     * @param stage
     */
    public void init(Stage stage) {
        setStage(stage);
        fullscreen.setOnAction(this);
        gakuseki.setOnAction(this);
        reg.setOnAction(this);
        binbutton.setOnAction(this);
        txtbutton.setOnAction(this);
        setChoice();
    }


    /**
     * 選択肢の追加
     */
    public void setChoice() {
        fromDate.setValue(LocalDate.now());
        toDate.setValue(LocalDate.now());
        ObservableList<String> hour = FXCollections.observableArrayList();
        ObservableList<String> minute_second = FXCollections.observableArrayList();
        for (int i = 0; i <= 23; i++) hour.add(String.valueOf(i));
        fromHour.setItems(hour);
        toHour.setItems(hour);
        for (int i = 0; i <= 59; i++) minute_second.add(String.valueOf(i));
        fromMinute.setItems(minute_second);
        toMinute.setItems(minute_second);
        fromSecond.setItems(minute_second);
        toSecond.setItems(minute_second);

        fromHour.getSelectionModel().select(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        toHour.getSelectionModel().select(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        fromMinute.getSelectionModel().select(Calendar.getInstance().get(Calendar.MINUTE));
        toMinute.getSelectionModel().select(Calendar.getInstance().get(Calendar.MINUTE));
        fromSecond.getSelectionModel().select(0);
        toSecond.getSelectionModel().select(59);
    }

    /**
     * データ追加の部分
     */
    public void insertData() {

        if (isGakuseki(gakuseki.getText())) {
            String numberString = gakuseki.getText().length() == 10 ? new StringBuilder(gakuseki.getText()).substring(5) : gakuseki.getText();
            DataBase.addDB(DB_NAME, numberString);
            gakusekiNumber.setText(numberString + "を追加しました");
            history.setText(String.valueOf(Integer.parseInt(history.getText()) + 1));
            logger("add:" + numberString, false);
            gakuseki.setText("");
        } else {
            gakusekiNumber.setText("データが正しくないです");
            logger("入力エラー", true);
            gakuseki.setText("");
        }
    }

    /**
     * 入力されたデータが学籍番号であるかどうかを識別します
     *
     * @param gakuseki 学籍番号読み取りデータ
     * @return 正しければTrue、そうでなければFalse
     */
    public boolean isGakuseki(String gakuseki) {
        String regex = "^[0-9]+$";
        if (gakuseki.length() != 5 && gakuseki.length() != 10) return false;
        if (Pattern.compile(regex).matcher(gakuseki).find() == false) return false;
        return true;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        gakuseki.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)
                setFieldFocus();

        });
    }



    public void setFieldFocus() {
        tabpane.setFocusTraversable(false);
        gakuseki.requestFocus();
    }


    public void saveDB2txt() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存ファイル選択");
        fileChooser.setInitialFileName(DateUtils.getDefaultFileName() + ".txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file", "*.txt"));
        fileChooser.setInitialDirectory(new File("./"));
        File saveFile = fileChooser.showSaveDialog(stage);
        if (saveFile == null) {
            logger("ファイルが選択されていません", false);
            return;
        }
        List<String> list = new ArrayList<>();
        list.addAll(
                DataBase.readDB(DB_NAME,
                        fromDate.getValue().toString().replaceAll("-", "") +
                                String.format("%02d", Integer.parseInt((String) fromHour.getValue())) +
                                String.format("%02d", Integer.parseInt((String) fromMinute.getValue())) +
                                String.format("%02d", Integer.parseInt((String) fromSecond.getValue())),
                        toDate.getValue().toString().replaceAll("-", "") +
                                String.format("%02d", Integer.parseInt((String) toHour.getValue())) +
                                String.format("%02d", Integer.parseInt((String) toMinute.getValue())) +
                                String.format("%02d", Integer.parseInt((String) toSecond.getValue()))));

        save2txt(list, saveFile.getPath(), true);

    }


    /**
     * ログをコンソールとテキストで出力します。
     * 問題が発生したとき用
     *
     * @param text
     * @param isError
     */
    public void logger(String text, boolean isError) {
        Label label = new Label(text);
        if (isError) label.setStyle("-fx-text-fill: red;");
        listView.getItems().add(0, label);
        try {
            save2txt(Arrays.asList(text), "activity.log", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * テキストのデータ保存
     *
     * @param data     テキストデータ
     * @param fileName 出力ファイル名
     * @param add      ファイルに追記するかどうか
     * @throws IOException
     */
    private void save2txt(List<String> data, String fileName, boolean add) throws IOException {
        BufferedWriter wr;
        try {
            wr = new BufferedWriter(new FileWriter(fileName, add));
            for (String datum : data) {
                wr.write(datum + "\n");
            }
            wr.flush();
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(ActionEvent event) {
        Object src = event.getSource();
        if (src.equals(fullscreen)) {
            stage.setFullScreen(!stage.isFullScreen());
        } else if (src.equals(gakuseki) || src.equals(reg)) {
            insertData();
        } else if (src.equals(binbutton)) {
            try {
                OldVersion.writeOldBinary(stage, fromDate.getValue().toString().replaceAll("-", "") +
                                String.format("%02d", Integer.parseInt((String) fromHour.getValue())) +
                                String.format("%02d", Integer.parseInt((String) fromMinute.getValue())) +
                                String.format("%02d", Integer.parseInt((String) fromSecond.getValue())),
                        toDate.getValue().toString().replaceAll("-", "") +
                                String.format("%02d", Integer.parseInt((String) toHour.getValue())) +
                                String.format("%02d", Integer.parseInt((String) toMinute.getValue())) +
                                String.format("%02d", Integer.parseInt((String) toSecond.getValue())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (src.equals(txtbutton)) {
            try {
                saveDB2txt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
