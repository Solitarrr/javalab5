package com.company;
import java.awt.geom.Rectangle2D;


//Класс для расчёта фрактала Мандельброта
// Этот класс является подклассом FractalGenerator
public class Mandelbrot extends FractalGenerator
{
    public static final int MAX_ITERATIONS = 2000;
    // Кратко: Установка начальных значений для формулы расчёта
    // Полно: Метод, позволяющий генератору фракталов определить область комплексной плоскости для определенного фрактала. В качестве аргумента передается прямоуг. объект, а также метод должен изменить поля прямоуг. для отображения начального диапазона для фрактала. Начальный диапазон в (-2 - 1.5i) - (1 + 1.5i).
    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    // Метод, который рассчитывает количество итераций для пикселей. Метод учитывает то, что модуль комплексного числа должен не превышать 2
    public int numIterations(double x, double y)
    {
        int iteration = 0; //Начать итерацию с нуля
        ComplexNums cxnum = new ComplexNums(x, y);
        while (iteration < MAX_ITERATIONS && ((cxnum.Zreal * cxnum.Zreal) + (cxnum.Zimaginary * cxnum.Zimaginary)) < 4)
        {
            cxnum.IterationMandelbrot();
            iteration++;
        }
        // В случае если достигнут максимум итераций (2000), то возвращается -1
        if (iteration == MAX_ITERATIONS){ return -1; }
        else { return iteration; }
    }
    // Возвращение имя фрактала "Mandelbrot"
    public String toString() {
        return "Mandelbrot";
    }
}