package com.watermark.core.service;

import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import com.watermark.core.domain.WatermarkParam;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.AlphaComposite;
import java.awt.FontMetrics;
import java.io.File;
import java.io.IOException;

public class ImageService {
    
    /**
     * 读取图片文件
     */
    public BufferedImage readImage(File inputFile) throws IOException, ImageReadException {
        return Imaging.getBufferedImage(inputFile);
    }

    /**
     * 保存图片文件
     */
    public void saveImage(BufferedImage image, File outputFile, ImageFormats format) throws IOException, ImageWriteException {
        Imaging.writeImage(image, outputFile, format);
    }

    /**
     * 添加文本水印
     */
    public BufferedImage addTextWatermark(BufferedImage original, WatermarkParam param) {
        BufferedImage watermarked = new BufferedImage(
            original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = (Graphics2D) watermarked.getGraphics();
        g2d.drawImage(original, 0, 0, null);
        
        // 设置字体渲染质量
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // 设置透明度
        AlphaComposite alpha = AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, param.getOpacity());
        g2d.setComposite(alpha);
        
        // 设置字体和颜色
        g2d.setFont(param.getFont());
        g2d.setColor(param.getColor());
        
        // 计算文本位置
        FontMetrics metrics = g2d.getFontMetrics();
        int textWidth = metrics.stringWidth(param.getText());
        int textHeight = metrics.getHeight();
        
        int x = calculateXPosition(original.getWidth(), textWidth, param.getX());
        int y = calculateYPosition(original.getHeight(), textHeight, param.getY());
        
        // 绘制文本
        g2d.drawString(param.getText(), x, y);
        g2d.dispose();
        
        return watermarked;
    }

    /**
     * 计算X轴位置 (0=左, 1=中, 2=右)
     */
    private int calculateXPosition(int imgWidth, int textWidth, int position) {
        switch (position % 3) {
            case 1: return (imgWidth - textWidth) / 2; // 居中
            case 2: return imgWidth - textWidth - 10;  // 右边(留10px边距)
            default: return 10;                        // 左边(留10px边距)
        }
    }

    /**
     * 计算Y轴位置 (0=上, 1=中, 2=下)
     */
    private int calculateYPosition(int imgHeight, int textHeight, int position) {
        switch (position / 3) {
            case 1: return (imgHeight + textHeight) / 2; // 垂直居中
            case 2: return imgHeight - 10;              // 底部(留10px边距)
            default: return textHeight + 10;            // 顶部(留10px边距)
        }
    }
}