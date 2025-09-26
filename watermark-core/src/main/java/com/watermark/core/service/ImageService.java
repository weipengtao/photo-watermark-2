package com.watermark.core.service;

import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.Imaging;
import com.watermark.core.domain.WatermarkParam;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageService {
    
    /**
     * 读取图片文件
     */
    public BufferedImage readImage(File inputFile) throws IOException {
        return Imaging.getBufferedImage(inputFile);
    }

    /**
     * 保存图片文件
     */
    public void saveImage(BufferedImage image, File outputFile, ImageFormats format) throws IOException {
        Imaging.writeImage(image, outputFile, format);
    }

    /**
     * 添加文本水印（基础实现）
     */
    public BufferedImage addTextWatermark(BufferedImage original, WatermarkParam param) {
        // TODO: 实现文本水印
        return original;
    }
}