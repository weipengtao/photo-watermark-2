package com.watermark.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.watermark.ui.controller.MainController;
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
        
        // 更彻底的窗口管理设置
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen(); // 使用centerOnScreen替代setX/setY
        
        // 添加额外的MacOS特定设置
        System.setProperty("glass.verbose", "true"); // 启用Glass日志
        System.setProperty("prism.verbose", "true"); // 启用Prism日志
        
        // 更安全的窗口显示方式
        javafx.application.Platform.runLater(() -> {
            try {
                primaryStage.show();
            } catch (Exception e) {
                System.err.println("窗口显示异常: " + e.getMessage());
                javafx.application.Platform.exit();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}