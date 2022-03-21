package com.company;
import java.awt.geom.Rectangle2D;

/**
 * Класс для расчёта фрактала Tricorn
 */
public class Tricorn extends FractalGenerator
{
    public static final int MAX_ITERATIONS = 2000;
    /**
     * Установка начальных значений для формулы расчёта
     */
    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -2;
        range.width = 4;
        range.height = 4;
    }
    /**
     * Метод, рассчитывающий количество итераций для пикселей
     */
    public int numIterations(double x, double y)
    {
        int iteration = 0;
        ComplexNums cnum = new ComplexNums(x, y);
        while (iteration < MAX_ITERATIONS && ((cnum.Zreal * cnum.Zreal) + (cnum.Zimaginary * cnum.Zimaginary)) < 4) {
            cnum.iterationTricorn();
            iteration++;
        }
        // Если достигнут максимум итераций (2000), возвращает -1
        if (iteration == MAX_ITERATIONS)
        {
            return -1;
        }

        return iteration;
    }
    /**
     * Возвращает имя фрактала "Tricorn"
     */
    public String toString() {
        return "Tricorn";
    }
}