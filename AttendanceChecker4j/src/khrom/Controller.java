package khrom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class Controller {
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
    private ChoiceBox fromHour;
    @FXML
    private ChoiceBox fromMinute;
    @FXML
    private ChoiceBox fromSecond;
    @FXML
    private DatePicker fromDate;
    @FXML
    private ChoiceBox toHour;
    @FXML
    private ChoiceBox toMinute;
    @FXML
    private ChoiceBox toSecond;
    @FXML
    private DatePicker toDate;
//    @FXML
//    private Button excelbutton;

    public static String DB_NAME = "gakuseki.db";

    public void toggleHandler(ActionEvent event) {
        switch (((ToggleButton) event.getSource()).getId()) {
            case "fullscreen":
                stage.setFullScreen(!stage.isFullScreen());
                break;
        }

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
    public void onInsertData() {
        System.out.println();
        if (isGakuseki(gakuseki.getText())) {
            addDB(gakuseki.getText());
            gakusekiNumber.setText((gakuseki.getText().length() == 10 ? new StringBuilder(gakuseki.getText()).substring(5) : gakuseki.getText())
                    + "を追加しました");
            logger("add:" + (gakuseki.getText().length() == 10 ? new StringBuilder(gakuseki.getText()).substring(5) : gakuseki.getText()), false);
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


    public synchronized void addDB(String number) {//マルチスレッド用に synchronizedをつけている
        Connection connOrigin = null;
        Statement stmt;
        Properties prop = new Properties();
        prop.put("journal_mode", "MEMORY");//必要はないが高速化のため
        prop.put("sync_mode", "OFF");//高速化のため
        String dbHead = "jdbc:sqlite:./";
        PreparedStatement pstmtOrigin;

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
            //準備(Cドライブにあるsqlite_sampleフォルダに作成します)
            Class.forName("org.sqlite.JDBC");
            connOrigin = DriverManager.getConnection(dbHead + DB_NAME, prop);
            connOrigin.setAutoCommit(false);
            stmt = connOrigin.createStatement();
            //ツイートID，緯度，経度，場所，ユーザID，スクリーン名，リプライ先ID，ツイート日，ツイートテキスト，ハッシュタグ，URL，ユーザ詳細,プロフィール内の位置情報
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS tenko ( date_time INTEGER, number TEXT)");

            pstmtOrigin = connOrigin.prepareStatement("INSERT INTO tenko VALUES (?, ?)");
            String date = String.valueOf(calendar.get(Calendar.YEAR)) +
                    String.valueOf(String.format("%02d", calendar.get(Calendar.MONTH) + 1)) +
                    String.valueOf(String.format("%02d", calendar.get(Calendar.DATE))) +
                    String.valueOf(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY))) +
                    String.valueOf(String.format("%02d", calendar.get(Calendar.MINUTE))) +
                    String.valueOf(String.format("%02d", calendar.get(Calendar.SECOND)));
            pstmtOrigin.setDouble(1, Double.parseDouble(date));
            pstmtOrigin.setString(2, number);
            pstmtOrigin.addBatch();
            pstmtOrigin.executeBatch();
            connOrigin.commit();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connOrigin != null) {
                try {
                    //接続を閉じる
                    connOrigin.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized Set<String> readDB(String from, String to) {//マルチスレッド用に synchronizedをつけている
        Connection connOrigin = null;
        Set<String> gakuseki = new HashSet<>();
        Properties prop = new Properties();
        prop.put("journal_mode", "MEMORY");//必要はないが高速化のため
        prop.put("sync_mode", "OFF");//高速化のため
        String dbHead = "jdbc:sqlite:./";
        PreparedStatement pstmtOrigin;

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
            //準備(Cドライブにあるsqlite_sampleフォルダに作成します)
            Class.forName("org.sqlite.JDBC");
            connOrigin = DriverManager.getConnection(dbHead + DB_NAME, prop);
            connOrigin.setAutoCommit(false);
//            stmt = connOrigin.createStatement();
//            //ツイートID，緯度，経度，場所，ユーザID，スクリーン名，リプライ先ID，ツイート日，ツイートテキスト，ハッシュタグ，URL，ユーザ詳細,プロフィール内の位置情報
//            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS tenko ( date_time INTEGER, number TEXT)");
            // WHERE カラム >= 値1 AND カラム <= 値2;
//            System.out.println("SELECT * FROM tenko WHERE date_time >= " + from + " AND date_time <=" + to);

            pstmtOrigin = connOrigin.prepareStatement("SELECT * FROM tenko WHERE date_time >= " + from + " AND date_time <=" + to);
            ResultSet rs = pstmtOrigin.executeQuery();
            while (rs.next()) {
                gakuseki.add(rs.getString(2));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connOrigin != null) {
                try {
                    //接続を閉じる
                    connOrigin.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return gakuseki;
    }

    public void saveDB2txt() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存ファイル選択");
        fileChooser.setInitialFileName(getDefaultFileName() + ".txt");
        fileChooser.setInitialDirectory(new File("./"));
        File saveFile = fileChooser.showSaveDialog(stage);
        if (saveFile == null) {
            logger("ファイルが選択されていません", false);
            return;
        }
        List<String> list = new ArrayList<>();
        list.addAll(
                readDB(
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

    public void saveDB2excel() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存ファイル選択");

        fileChooser.setInitialFileName(getDefaultFileName() + ".xlsx");
        fileChooser.setInitialDirectory(new File("./"));
        File saveFile = fileChooser.showSaveDialog(stage);
        if (saveFile == null) {
            logger("ファイルが選択されていません", false);
            return;
        }

    }



    public String getDefaultFileName() {
        String amPM = null;
        amPM = String.valueOf(Calendar.getInstance().get(Calendar.DATE));
        if (Calendar.getInstance().get(Calendar.AM_PM) == Calendar.PM) {
            amPM += "pm";
        } else {
            amPM += "am";
        }
        return amPM;
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

}
