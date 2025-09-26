package com.watermark.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

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
    
    @FXML
    private void handleImport() {
        // TODO: 实现图片导入
    }
    
    @FXML 
    private void handleApply() {
        // TODO: 应用水印
    }
}