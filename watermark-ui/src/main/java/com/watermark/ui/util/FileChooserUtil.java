package com.watermark.ui.util;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import java.util.List;

public class FileChooserUtil {
    
    public static List<File> showImageOpenDialog(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择图片");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("图片文件", 
                "*.jpg", "*.jpeg", "*.png", "*.bmp", "*.tiff"),
            new FileChooser.ExtensionFilter("所有文件", "*.*")
        );
        return fileChooser.showOpenMultipleDialog(window);
    }
    
    public static File showSaveDialog(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存图片");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JPEG", "*.jpg", "*.jpeg"),
            new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        return fileChooser.showSaveDialog(window);
    }
}