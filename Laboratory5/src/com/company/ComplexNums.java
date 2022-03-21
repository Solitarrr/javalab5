package com.company;

// Класс, который вычисляет итерации преобразования комплексных чисел
public class ComplexNums {
    private double x;
    private double y;
    public double Zreal;
    public double Zimaginary;
    // Конструктор, который устанавливает значения по умолчанию
    public ComplexNums(double x, double y){
        this.x = x;
        this.y = y;
        this.Zreal = 0;
        this.Zimaginary = 0;
    }
    // Методы, который осуществляет подсчёт
    public void IterationMandelbrot(){
        double ZrealUpdated = (Zreal * Zreal) - (Zimaginary * Zimaginary) + x;
        double ZimaginaryUpdated = 2 * Zreal * Zimaginary + y;
        Zreal = ZrealUpdated;
        Zimaginary = ZimaginaryUpdated;
    }
    public void iterationTricorn(){
        double ZrealUpdated = (Zreal * Zreal) - (Zimaginary * Zimaginary) + x;
        double ZimaginaryUpdated = -2 * Zreal * Zimaginary + y;
        Zreal = ZrealUpdated;
        Zimaginary = ZimaginaryUpdated;
    }
    public void iterationBurningShip(){
        double ZrealUpdated = Zreal * Zreal - Zimaginary * Zimaginary + x;
        double ZimaginaryUpdated = 2 * Math.abs(Zreal) * Math.abs(Zimaginary) + y;
        Zreal = ZrealUpdated;
        Zimaginary = ZimaginaryUpdated;
    }
}