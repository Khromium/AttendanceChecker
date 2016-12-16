package khrom;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

public class Controller {
    @FXML
    private ToggleButton fullscreen;
    private Stage stage;
    @FXML
    private ToolBar toolbar;
    @FXML
    private TextField gakuseki;
    @FXML
    private TabPane tabpane;
    @FXML
    private Label gakusekiNumber;

    public void toggleHandler(ActionEvent event) {
        switch (((ToggleButton) event.getSource()).getId()) {
            case "fullscreen":
                stage.setFullScreen(!stage.isFullScreen());
                break;
        }

    }

    /**
     * データ追加の部分
     */
    public void onInsertData() {

        System.out.println();
        if (isGakuseki(gakuseki.getText())) {
            addDB("tst.db", gakuseki.getText());
            gakusekiNumber.setText(gakuseki.getText() + "を追加しました");
            gakuseki.setText("");
        } else {
            gakusekiNumber.setText("データが正しくないです");
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

//        ReadOnlyBooleanProperty fullScreenProperty = this.stage.fullScreenProperty();
        gakuseki.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)
                setFieldFocus();

        });
//        fullScreenProperty.addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isFullScreen) {
//                if (isFullScreen) {
//                    root.getChildren().remove(menu);
//                } else {
//                    vBox.getChildren().add(0, menu);
//                }
//            }
//        });


    }

    /**
     * なんか動かん
     */
//    public void setTabListener() {
//        tabpane.getSelectionModel().selectedItemProperty().addListener((obs, old, newV) -> {
//            if (newV.getTabPane().getSelectionModel().getSelectedIndex() == 0)
//                gakuseki.requestFocus();
//        });
//    }
    public void setFieldFocus() {
        tabpane.setFocusTraversable(false);
        gakuseki.requestFocus();
    }


    public synchronized void addDB(String originDB, String number) {//マルチスレッド用に synchronizedをつけている
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
            connOrigin = DriverManager.getConnection(dbHead + originDB, prop);
            connOrigin.setAutoCommit(false);


            stmt = connOrigin.createStatement();
            //ツイートID，緯度，経度，場所，ユーザID，スクリーン名，リプライ先ID，ツイート日，ツイートテキスト，ハッシュタグ，URL，ユーザ詳細,プロフィール内の位置情報
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS tenko ( date_time INTEGER, number TEXT)");

            pstmtOrigin = connOrigin.prepareStatement("INSERT INTO tenko VALUES (?, ?)");
            String date = String.valueOf(calendar.get(Calendar.YEAR)) +
                    String.valueOf(String.format("%02d", calendar.get(Calendar.MONTH))) +
                    String.valueOf(String.format("%02d", calendar.get(Calendar.DATE))) +
                    String.valueOf(String.format("%02d", calendar.get(Calendar.HOUR))) +
                    String.valueOf(String.format("%02d", calendar.get(Calendar.MINUTE))) +
                    String.valueOf(String.format("%02d", calendar.get(Calendar.SECOND))) +
                    String.valueOf(calendar.get(Calendar.MILLISECOND));
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

}
