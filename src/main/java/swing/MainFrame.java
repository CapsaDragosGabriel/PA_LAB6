package swing;

import lombok.Data;

import javax.swing.*;

import java.awt.*;
@Data
public class MainFrame extends JFrame {
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;
    GameGraph<GameNode,GameEdge> gameGraph;
    int player=1;
    int rows,cols;

    public MainFrame() {
        super("Laboratorul 6 CDG");
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //create the components
        controlPanel=new ControlPanel(this);
        configPanel=new ConfigPanel(this);
        canvas = new DrawingPanel(this);
 //...TODO
        //arrange the components in the container (frame)
        //JFrame uses a BorderLayout by default

        this.add(configPanel,BorderLayout.NORTH);

        this.add(controlPanel,BorderLayout.SOUTH);
       this.add(canvas, BorderLayout.CENTER);

 //...TODO

        //invoke the layout manager
        pack();
    }


}