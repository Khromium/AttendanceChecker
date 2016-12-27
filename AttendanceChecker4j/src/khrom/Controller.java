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

public class Controller {
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
    @FXML
    private Button filepicker, output, clear;
    @FXML
    private ListView filelist;


    /**
     * 選択肢の追加やアクションのセットをここでまとめて行う
     *
     * @param stage
     */
    public void init(Stage stage) {
        setStage(stage);
        fullscreen.setOnAction(event -> stage.setFullScreen(!stage.isFullScreen()));
        gakuseki.setOnAction(event -> insertData());
        reg.setOnAction(event -> insertData());
        binbutton.setOnAction(event -> OldVersions.writeOldBinary(stage, getFromDateString(), getToDateString()));
        txtbutton.setOnAction(event -> saveDB2txt());
        filepicker.setOnAction(event -> addDraftFiles());
        output.setOnAction(event -> outputData(getAllfileData(), false));
        clear.setOnAction(event -> clearList(filelist));
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

    /**
     * ステージのセット
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        gakuseki.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)
                setFieldFocus();

        });
    }

    /**
     * 入力欄にフォーカスが当たるようにする
     */
    public void setFieldFocus() {
        tabpane.setFocusTraversable(false);
        gakuseki.requestFocus();
    }

    /**
     * ファイルを選択してリストに加える
     */
    public void addDraftFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("学籍ファイル選択");
        fileChooser.setInitialFileName(DateUtils.getDefaultFileName() + ".txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("numbers file", "*.txt", "*.bin", "*.db"));
        fileChooser.setInitialDirectory(new File("./"));
        List<File> files = fileChooser.showOpenMultipleDialog(stage);
        files.forEach(f -> filelist.getItems().add(0, new Label(f.getPath())));
    }

    /**
     * リスト削除
     * @param list
     */
    public void clearList(ListView list) {
        list.getItems().clear();
    }

    /**
     * 全データを取得
     * @return
     */
    public List<String> getAllfileData() {
        Set<String> numbers = new HashSet<>();
        for (Object path : filelist.getItems()) {
            File file = new File(((Label) path).getText());
            String[] tmp = file.getName().split("\\.");
            switch (tmp[tmp.length - 1]) {
                case "txt":
                    numbers.addAll(readFile(file.getPath()));
                    break;
                case "bin":
                    numbers.addAll(OldVersions.read(file));
                    break;
                case "db":
                    numbers.addAll(DataBase.readAllData(DB_NAME));
                    break;
            }
        }
        List<String> res = new ArrayList<>();
        res.addAll(numbers);

        return res;
    }

    /**
     * データベースの内容をテキストに保存する
     */
    public void saveDB2txt() {
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
        list.addAll(DataBase.readDB(DB_NAME, getFromDateString(), getToDateString()));

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
        save2txt(Arrays.asList(text), "activity.log", true);
    }

    public void outputData(List<String> data, boolean isAppend) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存ファイル選択");
        fileChooser.setInitialFileName(DateUtils.getDefaultFileName() + ".txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file", "*.txt"));
        fileChooser.setInitialDirectory(new File("./"));
        File saveFile = fileChooser.showSaveDialog(stage);
        if (saveFile == null)
            return;
        save2txt(data, saveFile.getPath(), false);

    }

    /**
     * テキストのデータ保存
     *
     * @param data     テキストデータ
     * @param fileName 出力ファイル名
     * @param isAppend ファイルに追記するかどうか
     * @throws IOException
     */
    private void save2txt(List<String> data, String fileName, boolean isAppend) {
        BufferedWriter wr;
        try {
            wr = new BufferedWriter(new FileWriter(fileName, isAppend));
            for (String datum : data) {
                wr.write(datum + "\n");
            }
            wr.flush();
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * テキストファイルの読み込み
     * @param fName
     * @return
     */
    public Set<String> readFile(String fName) {
        Set<String> res = new HashSet<>();
        String str;
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fName), "UTF-8"));
            while ((str = br.readLine()) != null) {
                res.add(str);
            }
        } catch (Exception e) {//面倒なのでまとめる
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 始まりの日付取得
     * @return
     */
    public String getFromDateString() {
        return fromDate.getValue().toString().replaceAll("-", "") +
                String.format("%02d", Integer.parseInt((String) fromHour.getValue())) +
                String.format("%02d", Integer.parseInt((String) fromMinute.getValue())) +
                String.format("%02d", Integer.parseInt((String) fromSecond.getValue()));
    }

    /**
     * 終わりの日付取得
     * @return
     */
    public String getToDateString() {
        return toDate.getValue().toString().replaceAll("-", "") +
                String.format("%02d", Integer.parseInt((String) toHour.getValue())) +
                String.format("%02d", Integer.parseInt((String) toMinute.getValue())) +
                String.format("%02d", Integer.parseInt((String) toSecond.getValue()));
    }
}
