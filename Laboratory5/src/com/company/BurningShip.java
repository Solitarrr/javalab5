package com.company;

import java.awt.geom.Rectangle2D;


//Класс для расчёта фрактала Burning Ship
public class BurningShip extends FractalGenerator
{
    public static final int MAX_ITERATIONS = 2000;
    // Установка начальных значений для формулы расчёта
    // Метод дает возможность генератору определить наиболее "интересную" область комплексной плоскости для конкретноого фрактала. В качестве аргумента передается прямоугольный объект, а также метод должен изменить поля прямоугольника. Конечный диапазон - (-2 - 1.5i) - (1 + 1.5i).
    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
    }
     // Метод, рассчитывающий количество итераций для пикселей
    public int numIterations(double x, double y)
    {
        int iteration = 0; //Начать итерацию с нуля

        ComplexNums cnum = new ComplexNums(x, y);
        while (iteration < MAX_ITERATIONS && ((cnum.Zreal * cnum.Zreal) + (cnum.Zimaginary * cnum.Zimaginary)) < 4) {
            cnum.iterationBurningShip();
            iteration++;
        }
        // Если достигнут максимум итераций (2000), возвращает -1
        if (iteration == MAX_ITERATIONS)
        {
            return -1;
        }
        return iteration;
    }
    // Возвращает имя фрактала "Burning Ship"
    public String toString() { return "Burning Ship"; }
}
