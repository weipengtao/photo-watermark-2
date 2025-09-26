package com.watermark.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/watermark/ui/view/main.fxml"));
        Parent root = loader.load();
        
        // 传递primaryStage给控制器
        MainController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("图片水印工具");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}