package com.company;
import javax.swing.*;
import java.awt.image.*;
import java.awt.*;

//Класс изображения, позволяющий выводить фрактал на экран
class JImageDisplay extends JComponent
{
    // Экземпляр буферизованного изображения. Управляет изображением, которое мы можем отрисовывать
    private BufferedImage showImage;
    // Конструктор, который принимает ширину и высоту изображения, а затем инициализирует объект с такими шириной и высотой
    public JImageDisplay(int width, int height) {
        // Создание объекта изображения
        this.showImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // Обращение к setPreferredSize и установка ширины и высоты
        super.setPreferredSize(new Dimension(width, height));
    }
    // Метод для получения изображения
    public BufferedImage getImage() {
        return showImage;
    }
    // Переопредлённый метод, позволяющий выводить изображение на экран
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(showImage, 0, 0, showImage.getWidth(), showImage.getHeight(), null);
    }
    // Данный метод ставит всем пикселям изображения чёрный цвет
    public void clearImage()
    {
        int[] blankArray = new int[getWidth() * getHeight()];
        showImage.setRGB(0, 0, getWidth(), getHeight(), blankArray, 0, 1);
    }
    // Метод, который устанавливает цвет пикселя соответственно переданному значению цвета
    public void drawPixel(int x, int y, int rgbColor)
    {
        showImage.setRGB(x, y, rgbColor);
    }
}