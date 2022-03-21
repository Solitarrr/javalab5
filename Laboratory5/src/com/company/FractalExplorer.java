package com.company;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import javax.swing.JFileChooser.*;
import javax.swing.filechooser.*;
import javax.imageio.ImageIO.*;
import java.awt.image.*;


// Класс, позволяющий исследовать разные области фрактала благодаря его создания, отображения через графический интерфейс и обработки событий, которые вызваны взаимодействием приложения с пользователем
public class FractalExplorer
{
    // Ширина и высота отображения - это целочисленная, поэтому int
    private int displaySize;
    // JImageDisplay для обновления отображения изображения в разных методах в процессе вычисления фрактала
    private JImageDisplay display;
    // Объект, использующий ссылку на базовый класс для отображения других типов фракталов (будет на будущее)
    private FractalGenerator fractal;
    // Объект, который определяет размер текущего диапазона просмотра, то есть то, что показывает в настоящий момент
    private Rectangle2D.Double range;
    // Конструктор, принимающий размер дисплея и сохраняющий его, после чего инициализирующий объекты диапазона и генератора фрактала
    public FractalExplorer(int size) {
        // Сохраняет размер дисплея
        displaySize = size;
        // Инициализирует фрактальный генератор и объекты диапазона
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);

    }
    // Этот метод инициализирует графический интерфейс Swing с помощью JFrame, содержащий JImageDisplay объект и кнопку для сброса дисплея.
    public void createAndShowGUI()
    {
        display.setLayout(new BorderLayout());
        JFrame Frame = new JFrame("Fractal Explorer");
        // Добавляет и центрует объект изображения
        Frame.add(display, BorderLayout.CENTER);
        // Создание объекта кнопки сброса
        JButton resetButton = new JButton("Reset");
        Frame.add(resetButton, BorderLayout.SOUTH);
        ButtonHandler resetHandler = new ButtonHandler();
        // Обработка события нажатия на кнопку
        resetButton.addActionListener(resetHandler);
        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);
        // Создание объекта combo-box
        JComboBox myComboBox = new JComboBox();
        // Добавляем элементы в combo-box
        FractalGenerator mandelbrotFractal = new Mandelbrot();
        myComboBox.addItem(mandelbrotFractal);
        FractalGenerator tricornFractal = new Tricorn();
        myComboBox.addItem(tricornFractal);
        FractalGenerator burningShipFractal = new BurningShip();
        myComboBox.addItem(burningShipFractal);
        // Обрабатывать нажатия будет ButtonHandler
        ButtonHandler fractalChooser = new ButtonHandler();
        myComboBox.addActionListener(fractalChooser);
        // Создание панели и добавление combo-box-а. Добавление также текста пояснения "Fractial:"
        // Пропысывание расположения этой панели наверху
        JPanel myPanel = new JPanel();
        JLabel myLabel = new JLabel("Fractal:");
        myPanel.add(myLabel);
        myPanel.add(myComboBox);
        Frame.add(myPanel, BorderLayout.NORTH);
        // Создается кнопка сохранения и добавляется на созданную панель внизу
        JButton saveButton = new JButton("Save");
        JPanel myBottomPanel = new JPanel();
        myBottomPanel.add(saveButton);
        myBottomPanel.add(resetButton);
        Frame.add(myBottomPanel, BorderLayout.SOUTH);
        // Обрабатывать события будет ButtonHandler
        ButtonHandler saveHandler = new ButtonHandler();
        saveButton.addActionListener(saveHandler);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.pack();
        Frame.setVisible(true);
        Frame.setResizable(false);

    }
    // Данный метод является приватным, а также вспомогательным и служит для отображения фрактала. Его цикл заключается в том, что он проходит через каждый пиксель в отображении и вычисляет кол-во итераций для соответствующих координат в области отображения фракталов. Если кол-во итераций = -1, он ставит черный цвет пикселя, а иначе выбирает значение в зависимости от кол-ва итераций. По готовности обновляется дисплей
    private void drawFractal()
    {
        // Перебор каждого пикселя на имеющемся дисплее
        for (int x=0; x<displaySize; x++){
            for (int y=0; y<displaySize; y++){
                // Тут находятся координаты xCoord и yCoord в той области, где отображается фрактал
                double xCoord = fractal.getCoord(range.x,
                        range.x + range.width, displaySize, x);
                double yCoord = fractal.getCoord(range.y,
                        range.y + range.height, displaySize, y);
                // Расчет кол-ва итераций для координат в той области, где отображается фрактал
                int i = fractal.numIterations(xCoord, yCoord);
                // Если кол-во итераций = -1, поставить черный пиксель
                if (i == -1){
                    display.drawPixel(x, y, 0);
                }
                else{
                    // Взять в противном случае значение оттенка. В основу брать числа итераций
                    float hue = 0.7f + (float) i / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    // Обновление отображения цвета у каждого пикселя
                    display.drawPixel(x, y, rgbColor);
                }
            }
        }
        // Когда пиксели будут отрисованы, то перекрасить JImageDisplay в соответствии с текущим изображением
        display.repaint();
    }
    // Внутренний класс для обработки событий ActionListener от кнопки сброса
    private class ButtonHandler implements ActionListener
    {   // Обработчик сбросит диапазон к начальному диапазону, который задан генератором, а затем перерисует фрактал
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();
            // Если нажать на combo-box, то возьмется выбранный фрактал и идет его ввывод на дисплей
            if (e.getSource() instanceof JComboBox) {
                JComboBox mySource = (JComboBox) e.getSource();
                fractal = (FractalGenerator) mySource.getSelectedItem();
                fractal.getInitialRange(range);
                drawFractal();
            }
            // Если нажать на кнопку сброса, то сработает приближение
            else if (command.equals("Reset")) {
                fractal.getInitialRange(range);
                drawFractal();
            }
            // Если нажать на кнопку сохранения, то сохраняется текущее отображение фрактала
            else if (command.equals("Save")) {
                JFileChooser myFileChooser = new JFileChooser();
                // Сохранение только PNG-изображения
                FileFilter extensionFilter = new FileNameExtensionFilter("PNG Images", "png");
                myFileChooser.setFileFilter(extensionFilter);
                // Удостоверяется, что filechooser не разрешит что-то, помимо *.png
                myFileChooser.setAcceptAllFileFilterUsed(false);
                // Открывает окно с возможностью выбора директории сохранения
                int userSelection = myFileChooser.showSaveDialog(display);
                // Если пользователь решает сохранить файл, операция сохранения продолжается
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    // Получение файла и имя файла
                    java.io.File file = myFileChooser.getSelectedFile();
                    String file_name = file.toString();
                    // Попытка сохранить изображение на диск
                    try {
                        BufferedImage showImage = display.getImage();
                        javax.imageio.ImageIO.write(showImage, "png", file);
                    }
                    // Ловит все исключения и пишет сообщение об исключении
                    catch (Exception exception) {
                        JOptionPane.showMessageDialog(display, exception.getMessage(), "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                }
                 // В случае, если пользователь передумал сохранять файл, то return
                else return;
            }
        }
    }
    //Внутренний класс для обработки событий MouseAdapter с дисплея
    private class MouseHandler extends MouseAdapter
    {
        // Когда идет нажатие мышкой, то происходит перемещение на указанные щелчком координаты. Приближение вполовину от нынешнего
        @Override //переопределяем метод базового класса
        public void mouseClicked(MouseEvent e)
        {
            // Принимает x координату нажатия мышью
            int x = e.getX();
            double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, x);
            // Принимает y координату нажатия мышью
            int y = e.getY();
            double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, y);
            // Вызыватеся метод генератора recentAndZoomRange() с координатами, по которым идет нажатие, и с масштабом 0,5
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            // Перерисовывает фрактал после приближения отображения
            drawFractal();
        }
    }
    //Статический метод main () для запуска FractalExplorer. Инициализирует новый экземпляр FractalExplorer с размером дисплея 800, вызывает createAndShowGUI () в объекте проводника, а затем вызывает drawFractal () в проводнике, чтобы увидеть исходный вид.
    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(800);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}