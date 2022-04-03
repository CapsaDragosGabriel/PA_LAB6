package swing;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jgrapht.alg.matching.DenseEdmondsMaximumCardinalityMatching;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static java.lang.Thread.sleep;

public class DrawingPanel extends JPanel {
    public static final int STONE_SIZE = 25;
    private final MainFrame frame;
    int rows, cols;
    int canvasWidth = 600, canvasHeight = 600;
    int boardWidth, boardHeight;
    int cellWidth, cellHeight;
    int padX, padY;
    public int counter = 0;
    boolean initiated = Boolean.FALSE;
    List<Integer> sticksHorizontal = new ArrayList<>();
    List<Integer> sticksVertical = new ArrayList<>();

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;

        init(frame.configPanel.getRows(), frame.configPanel.getCols());

    }
    void resize() {

        this.padX = STONE_SIZE + 10;
        this.padY = STONE_SIZE + 10;
        this.cellWidth = (canvasWidth - 2 * padX) / (cols - 1);
        this.cellHeight = (canvasHeight - 2 * padY) / (rows - 1);
        this.boardWidth = (cols - 1) * cellWidth;
        this.boardHeight = (rows - 1) * cellHeight;
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    }
    /**
     * Daca exista un maximum matching -> nu exista drum de augumentare -> ultimul nod
     * va fi de culoarea celui de al doilea jucator-> primul jucator pierde
     * In caz contrar, ultimul nod va avea culoarea primului jucator -> al doilea jucator pierde
     */
    public void winningStrategy() {
        DenseEdmondsMaximumCardinalityMatching denseEdmondsMaximumCardinalityMatching = new DenseEdmondsMaximumCardinalityMatching(frame.gameGraph);
        System.out.println(denseEdmondsMaximumCardinalityMatching.isMaximumMatching
                (denseEdmondsMaximumCardinalityMatching.getMatching()));

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
        initiated = false;
        this.rows = rows;
        this.cols = cols;
        this.padX = STONE_SIZE + 10;
        this.padY = STONE_SIZE + 10;
        this.cellWidth = (canvasWidth - 2 * padX) / (cols - 1);
        this.cellHeight = (canvasHeight - 2 * padY) / (rows - 1);
        this.boardWidth = (cols - 1) * cellWidth;
        this.boardHeight = (rows - 1) * cellHeight;
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        frame.setResizable(false);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                drawStone(e.getX(), e.getY());
            }

            ;
        });
      //  this.paintComponent(this.getGraphics());
    }

    public void loadGame() {
        frame.saveGame = true;
        GameGraph loadGraph = new GameGraph();
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("E:\\AN2\\ProiectePA\\PA_LAB6\\graph.JSON"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            loadGraph = mapper.readValue(inputStream, GameGraph.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.gameGraph = loadGraph;
        //frame.canvas.
        int oldPlayer = frame.gameGraph.getPrevGameNode()!=null? frame.gameGraph.getPrevGameNode().getPlayer(): 2;
        int nextPlayer = (oldPlayer == 0) ? 0 : (oldPlayer == 1 ? 2 : 1);
        frame.player = nextPlayer;
        frame.canvas.repaint();
        frame.canvas.paintGrid((Graphics2D) frame.canvas.getGraphics());
        //winningStrategy();
        //  frame.saveGame=false;
    }

    void saveGame() {
        BufferedImage im = new BufferedImage(frame.canvas.getWidth(), frame.canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
        GameGraph saveGraph = new GameGraph();
        this.paint(im.getGraphics());
        try {
            saveGraph = frame.gameGraph;
            ObjectMapper mapper = new ObjectMapper();
            File locationPNG = new File("E:\\AN2\\ProiectePA\\PA_LAB6\\shot.png");
            File locationJSON = new File("E:\\AN2\\ProiectePA\\PA_LAB6\\graph.JSON");
            ImageIO.write(im, "PNG", locationPNG);
            mapper.writeValue(locationJSON, saveGraph);
            System.out.println("Saved image at "+locationPNG);
        } catch (StreamWriteException e) {

            System.out.println("Problem saving the file.");
        } catch (DatabindException e) {

            System.out.println("Binding failed.");
        } catch (IOException e) {
            System.out.println("Could not write image");
            e.printStackTrace();
        }

        frame.saveGame = false;
    }

    void drawStone(int x, int y) {
        GameNode node = frame.gameGraph.getGameNodeAtCoords(x, y, frame.player);
        if (node == null) return;
        Graphics graphics = this.getGraphics();
        Graphics2D g = (Graphics2D) graphics;
        if (frame.player == 1) g.setColor(Color.RED);
        else g.setColor(Color.BLUE);
        g.fillOval((int) node.getCoordX() - STONE_SIZE / 2, (int) node.getCoordY() - STONE_SIZE / 2, STONE_SIZE, STONE_SIZE);
        frame.swapPlayer();

    }


    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        paintGrid(g);
        if (!frame.saveGame) generateSticks(g);
        paintSticks(g);
        paintStones(g);
        // repaint();

    }

    private void paintStones(Graphics2D g) {
        for (GameNode node : frame.gameGraph.getGameNodeSet()) {
            g.setColor(Color.BLACK);
            if (node.getPlayer() == 1) g.setColor(Color.RED);
            else if (node.getPlayer() == 2) g.setColor(Color.BLUE);
            g.fillOval((int) node.getCoordX() - STONE_SIZE / 2, (int) node.getCoordY() - STONE_SIZE / 2, STONE_SIZE, STONE_SIZE);

        }
    }

    private void paintSticks(Graphics2D g) {
        for (GameEdge edge : frame.gameGraph.getGameEdgeSet()) {
            int x1 = edge.getGameNode1().getCoordX();
            int y1 = edge.getGameNode1().getCoordY();
            int x2 = edge.getGameNode2().getCoordX();
            int y2 = edge.getGameNode2().getCoordY();
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(5));
            g.drawLine(x1, y1, x2, y2);
        }
    }

    private void generateSticks(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(5));
        //horizontal lines
        frame.saveGame = true;

        for (int row = 0; row < rows; row++) {
            for (int clmn = 0; clmn < cols - 1; clmn++) {
                int x1 = padX + clmn * cellWidth;
                int y1 = padY + row * cellHeight;
                int x2 = padX + (clmn + 1) * cellWidth;
                int y2 = y1;
                if (Math.random() > 0.4) {
                    g.drawLine(x1, y1, x2, y2);
                    GameNode gameNode1 = new GameNode(x1, y1);
                    GameNode gameNode2 = new GameNode(x2, y2);
                    frame.gameGraph.addEdge(gameNode1, gameNode2);
                    frame.gameGraph.addVertex(gameNode1);
                    frame.gameGraph.addVertex(gameNode2);
                    // sticksHorizontal.add(x2);
                }
            }

        }
        //vertical lines TODO
        for (int clmn = 0; clmn < cols; clmn++) {
            for (int row = 0; row < rows - 1; row++) {
                int x1 = padX + clmn * cellWidth;
                int y1 = padY + row * cellHeight;
                int x2 = x1;
                int y2 = padX + (row + 1) * cellHeight;

                if (Math.random() > 0.4) {
                    g.drawLine(x1, y1, x2, y2);
                    GameNode gameNode1 = new GameNode(x1, y1);
                    GameNode gameNode2 = new GameNode(x2, y2);
                    frame.gameGraph.addEdge(gameNode1, gameNode2);
                    frame.gameGraph.addVertex(gameNode1);
                    frame.gameGraph.addVertex(gameNode2);
                    //  sticksHorizontal.add(x2);
                }
            }
        }
        initiated = true;
    }

    private void paintGrid(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.setStroke(new BasicStroke(1));
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
            int x1 = padX + clmn * cellWidth;
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
                g.drawOval(x - STONE_SIZE / 2, y - STONE_SIZE / 2, STONE_SIZE, STONE_SIZE);
            }
        }
    }


}