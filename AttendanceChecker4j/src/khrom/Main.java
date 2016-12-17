package khrom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("出欠とるよ！");
        primaryStage.setScene(new Scene(root, 620, 400));
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(620);
        Controller controller = fxmlLoader.getController();
        controller.setStage(primaryStage);
        controller.setChoice();
        primaryStage.show();
        controller.setFieldFocus();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
