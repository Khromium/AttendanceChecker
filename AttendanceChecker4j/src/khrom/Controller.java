package khrom;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
        gakusekiNumber.setText(gakuseki.getText()+"を追加しました");
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
}
