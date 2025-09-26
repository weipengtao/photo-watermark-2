package com.watermark.core.domain;

import java.awt.Color;
import java.awt.Font;

/**
 * 水印参数配置
 */
public class WatermarkParam {
    private String text;         // 水印文本
    private Font font;          // 字体样式
    private Color color;        // 颜色
    private float opacity;      // 透明度 (0.0-1.0)
    private int x;             // x坐标
    private int y;             // y坐标

    // getters & setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}