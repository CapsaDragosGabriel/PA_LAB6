package Graphics;

import javax.swing.*;

import java.awt.*;

public class MainFrame extends JFrame {
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;
    int rows,cols;

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
        this.add(canvas);
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