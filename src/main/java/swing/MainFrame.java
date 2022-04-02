package swing;

import lombok.Data;

import javax.swing.*;

import java.awt.*;

@Data
public class MainFrame extends JFrame {
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;
    GameGraph<GameNode, GameEdge> gameGraph = new GameGraph<>();
    int player = 1;
    int rows, cols;
    boolean saveGame=false;
    public MainFrame() {
        super("Laboratorul 6 CDG");
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //create the components
        controlPanel = new ControlPanel(this);
        configPanel = new ConfigPanel(this);
        canvas = new DrawingPanel(this);
        //...TODO
        //arrange the components in the container (frame)
        //JFrame uses a BorderLayout by default

        this.add(configPanel, BorderLayout.NORTH);

        this.add(controlPanel, BorderLayout.SOUTH);
        this.add(canvas, BorderLayout.CENTER);


        gameGraph.removeAllEdges(gameGraph.getGameEdgeSet());
        gameGraph.removeAllVertices(gameGraph.getGameNodeSet());
        setCols(cols);
        setRows(rows);
        canvas.setCols(cols);
        canvas.setRows(rows);
        canvas.resize();
        setPlayer(1);
        gameGraph.setPrevGameNode(null);
        gameGraph.depopulateNodes();

        //...TODO

        //invoke the layout manager
        pack();
    }

    public int gameFinished() {
        for (GameNode node : gameGraph.getGameNodeSet()) {
            if (gameGraph.isValidNode(node))
                return 0;
        }
        if (player == 1) {
            System.out.println("Player 2 won!");
            return 2;
        }
        System.out.println("Player 1 won!");
        return 1;
    }

    public void swapPlayer() {
        if (player == 1) player = 2;
        else if (player == 2) player = 1;
        gameFinished();
    }
}