package com.watermark.ui.controller;

import com.watermark.core.service.ImageService;
import com.watermark.ui.util.FileChooserUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;

public class MainController {
    @FXML private ImageView previewImage;
    @FXML private ListView<String> imageList;
    @FXML private TextField watermarkText;
    @FXML private Slider opacitySlider;
    @FXML private ComboBox<String> positionSelect;

    public void initialize() {
        // 初始化UI组件
        positionSelect.getItems().addAll(
            "左上", "中上", "右上",
            "左中", "居中", "右中", 
            "左下", "中下", "右下");
        positionSelect.getSelectionModel().select(4); // 默认居中
    }
    
    private final ImageService imageService = new ImageService();
    private Stage primaryStage;
    private List<File> currentImages;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleImport() {
        List<File> files = FileChooserUtil.showImageOpenDialog(primaryStage);
        if (files != null && !files.isEmpty()) {
            currentImages = files;
            imageList.getItems().clear();
            files.forEach(file -> imageList.getItems().add(file.getName()));
            
            // 预览第一张图片
            previewImage.setImage(new Image(files.get(0).toURI().toString()));
        }
    }
    
    @FXML 
    private void handleApply() {
        // TODO: 应用水印
    }
}