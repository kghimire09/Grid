//Grid: Create a Swing program that consists of a JFrame that holds one JPanel (which I will call the drawing panel).
//        Divide the drawing panel into a grid (the size of which should be controlled by command line arguments and
//        defaulting to 50 by 50) of cells. Paint each cell of the a random color. These colors should stay stable as
//        the frame is resized, hidden, exposed, and otherwise manipulated. Draw thin grey lines between each cell of
//        the grid. Note that the size of the cells will stay fixed, so rows and columns will have to be "added" or "removed"
//        as the frame is resized. While there are ways to solve this problem with layout managers, this time just use your
//        JPanel's paintComponent method to draw the requested pattern.

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Grid extends JFrame {
    private static double rectHeight = 50;
    private static double rectWidth = 50;

    public static class DrawingPanel extends JPanel {

        public Random rand = new Random();

        public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        public double width = screenSize.getWidth();
        public double height = screenSize.getHeight();
        private int ROWS = (int) (height / rectHeight);
        private int COLUMNS = (int) (width / rectWidth);

        private Color[][] colors;
        public DrawingPanel() {
            setBackground(Color.BLUE);
            colors = new Color[ROWS + 1][COLUMNS + 1];
            for (int i = 0; i <= ROWS; i++) {
                for (int j = 0; j <= COLUMNS; j++) {
                    colors[i][j] = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
                }
            }
        }
        public void squaresDrawing(Graphics graphic) {
            Graphics2D brick = (Graphics2D) graphic.create();
            double x = 0;
            double y = 0;
            for (int i = 0; i <= ROWS; i++) {
                for (int j = 0; j <= COLUMNS; j++) {
                    brick.setColor(colors[i][j]);
                    Rectangle2D.Double rect = new Rectangle2D.Double(x, y, rectWidth, rectHeight);
                    brick.fill(rect);
                    brick.setColor(Color.gray);
                    brick.draw(rect);
                    x += rectWidth;
                }
                x = 0;
                y += rectHeight;
            }
        }
        @Override
        protected void paintComponent(Graphics graphic) {
            super.paintComponent(graphic);
            squaresDrawing(graphic);
        }
    }
    public Grid() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        add(new DrawingPanel());
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                rectHeight = Integer.parseInt(args[0]);
                rectWidth = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.print("The value you entered is not a number");
                System.exit(1);
            }
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Grid().setVisible(true);
            }
        });
    }
}
