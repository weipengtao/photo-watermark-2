package com.watermark.ui.controller;

import com.watermark.core.domain.WatermarkParam;
import com.watermark.core.service.ImageService;
import com.watermark.ui.util.FileChooserUtil;
import org.apache.commons.imaging.ImageFormats;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {
    @FXML private ImageView previewImage;
    @FXML private ListView<String> imageList;
    @FXML private TextField watermarkText;
    @FXML private Slider opacitySlider;
    @FXML private ComboBox<String> positionSelect;
    @FXML private ComboBox<String> colorSelect;
    @FXML private Slider fontSizeSlider;
    @FXML private Label fontSizeLabel;
    
    // 颜色映射表
    private final Map<String, java.awt.Color> colorMap = new HashMap<>();

    public void initialize() {
        // 初始化UI组件
        positionSelect.getItems().addAll(
            "左上", "中上", "右上",
            "左中", "居中", "右中", 
            "左下", "中下", "右下");
        positionSelect.getSelectionModel().select(4); // 默认居中
        
        // 初始化颜色选择器
        initColorMap();
        colorSelect.getItems().addAll(colorMap.keySet());
        colorSelect.getSelectionModel().select("黑色"); // 默认黑色
        
        // 初始化字体大小滑块
        fontSizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int fontSize = newVal.intValue();
            fontSizeLabel.setText(String.valueOf(fontSize));
        });
        
        // 图片列表选择监听
        imageList.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
                int index = imageList.getSelectionModel().getSelectedIndex();
                if (index >= 0 && currentImages != null) {
                    previewImage.setImage(new Image(currentImages.get(index).toURI().toString()));
                }
            });
    }
    
    /**
     * 初始化颜色映射表
     */
    private void initColorMap() {
        colorMap.put("黑色", java.awt.Color.BLACK);
        colorMap.put("白色", java.awt.Color.WHITE);
        colorMap.put("红色", java.awt.Color.RED);
        colorMap.put("绿色", java.awt.Color.GREEN);
        colorMap.put("蓝色", java.awt.Color.BLUE);
        colorMap.put("黄色", java.awt.Color.YELLOW);
        colorMap.put("青色", java.awt.Color.CYAN);
        colorMap.put("粉色", java.awt.Color.PINK);
        colorMap.put("橙色", java.awt.Color.ORANGE);
        colorMap.put("灰色", java.awt.Color.GRAY);
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
            
            // 预览第一张图片并选中
            previewImage.setImage(new Image(files.get(0).toURI().toString()));
            imageList.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void handleExport() {
        if (currentImages == null || currentImages.isEmpty()) {
            showAlert("请先导入并处理图片");
            return;
        }
        
        File outputDir = FileChooserUtil.showSaveDialog(primaryStage);
        if (outputDir != null) {
            try {
                int index = imageList.getSelectionModel().getSelectedIndex();
                File originalFile = currentImages.get(index);
                String fileName = originalFile.getName();
                
                // 保存处理后的图片
                Image outputImage = previewImage.getImage();
                BufferedImage buffered = SwingFXUtils.fromFXImage(outputImage, null);
                imageService.saveImage(buffered, 
                    new File(outputDir, "wm_" + fileName), 
                    fileName.toLowerCase().endsWith(".png") ? 
                        ImageFormats.PNG : ImageFormats.JPEG);
                
                showAlert("导出成功: " + outputDir.getAbsolutePath());
            } catch (Exception e) {
                showAlert("导出失败: " + e.getMessage());
            }
        }
    }
    
    @FXML 
    private void handleApply() {
        if (currentImages == null || currentImages.isEmpty()) {
            showAlert("请先导入图片");
            return;
        }
        
        if (watermarkText.getText().isEmpty()) {
            showAlert("请输入水印文字");
            return;
        }
        
        try {
            File selectedFile = currentImages.get(imageList.getSelectionModel().getSelectedIndex());
            BufferedImage original = imageService.readImage(selectedFile);
            
            WatermarkParam param = new WatermarkParam();
            param.setText(watermarkText.getText());
            param.setOpacity((float) opacitySlider.getValue());
            param.setX(positionSelect.getSelectionModel().getSelectedIndex() % 3);
            param.setY(positionSelect.getSelectionModel().getSelectedIndex() / 3);
            
            // 设置水印颜色
            String selectedColor = colorSelect.getValue();
            if (selectedColor != null && colorMap.containsKey(selectedColor)) {
                param.setColor(colorMap.get(selectedColor));
            } else {
                param.setColor(java.awt.Color.BLACK); // 默认黑色
            }
            
            // 设置水印字体和大小
            int fontSize = (int) fontSizeSlider.getValue();
            param.setFont(new java.awt.Font("宋体", java.awt.Font.BOLD, fontSize));
            
            BufferedImage watermarked = imageService.addTextWatermark(original, param);
            previewImage.setImage(convertToFxImage(watermarked));
            
        } catch (Exception e) {
            showAlert("应用水印失败: " + e.getMessage());
        }
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private Image convertToFxImage(BufferedImage image) {
        return SwingFXUtils.toFXImage(image, null);
    }
}