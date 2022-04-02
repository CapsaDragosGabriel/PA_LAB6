package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import static java.lang.Thread.sleep;

public class DrawingPanel extends JPanel {
    private final MainFrame frame;
    int rows, cols;
    int canvasWidth = 600, canvasHeight = 600;
    int boardWidth, boardHeight;
    int cellWidth, cellHeight;
    int padX, padY;
    int stoneSize = 25;
    boolean initiated= Boolean.FALSE;
    List<Integer> sticksHorizontal=new ArrayList<>();
    List<Integer> sticksVertical=new ArrayList<>();
    public DrawingPanel(MainFrame frame) {
        this.frame = frame;

        init(frame.configPanel.getRows(), frame.configPanel.getCols());

    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    final void init(int rows, int cols) {

        this.rows = rows;
        this.cols = cols;
        this.padX = stoneSize + 10;
        this.padY = stoneSize + 10;
        this.cellWidth = (canvasWidth - 2 * padX) / (cols - 1);
        this.cellHeight = (canvasHeight - 2 * padY) / (rows - 1);
        this.boardWidth = (cols - 1) * cellWidth;
        this.boardHeight = (rows - 1) * cellHeight;
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
               drawStone(e.getX(),e.getY());
            };
        });

    }

    void drawStone (int x, int y)
    {

    }
    void resize()
    {

        this.padX = stoneSize + 10;
        this.padY = stoneSize + 10;
        this.cellWidth = (canvasWidth - 2 * padX) / (cols - 1);
        this.cellHeight = (canvasHeight - 2 * padY) / (rows - 1);
        this.boardWidth = (cols - 1) * cellWidth;
        this.boardHeight = (rows - 1) * cellHeight;
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    }
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        paintGrid(g);
       //if (!initiated)
        paintSticks(g);
        paintStones(g);
        repaint();

    }

    private void paintStones(Graphics2D g) {

    }

    private void paintSticks(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(5));
        //horizontal lines
        initiated=true;

        for (int row = 0; row < rows; row++) {
            for (int clmn = 0; clmn < cols-1; clmn++) {
                int x1 = padX+ clmn*cellWidth;
                int y1 = padY + row * cellHeight;
                int x2 = padX + (clmn+1)*cellWidth;
                int y2 = y1;
                if (Math.random() > 0.4) {
                    g.drawLine(x1, y1, x2, y2);

                    sticksHorizontal.add(x2);
                }
            }
        }
        //vertical lines TODO
        for (int clmn = 0; clmn < cols; clmn++) {
            for (int row = 0; row < rows-1; row++) {
                int x1 = padX + clmn * cellWidth;
                int y1 = padY+row*cellHeight;
                int x2 = x1;
                int y2 = padX + (row+1)*cellHeight;

                if (Math.random() > 0.4) {
                    g.drawLine(x1, y1, x2, y2);
                    sticksHorizontal.add(x2);
                }
            }
        }
    }
    private void paintGrid(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        //horizontal lines
        for (int row = 0; row < rows; row++) {
            int x1 = padX;
            int y1 = padY + row * cellHeight;
            int x2 = padX + boardWidth;
            int y2 = y1;
            g.drawLine(x1, y1, x2, y2);
        }
        //vertical lines TODO
        for (int clmn = 0; clmn < cols; clmn++) {
            int x1 = padX + clmn*cellWidth;
            int y1 = padY;
            int x2 = x1;
            int y2 = padX + boardHeight;
            g.drawLine(x1, y1, x2, y2);
        }

        //intersections
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = padX + col * cellWidth;
                int y = padY + row * cellHeight;
                g.setColor(Color.LIGHT_GRAY);
                g.drawOval(x - stoneSize / 2, y - stoneSize / 2, stoneSize, stoneSize);
            }
        }
    }
}