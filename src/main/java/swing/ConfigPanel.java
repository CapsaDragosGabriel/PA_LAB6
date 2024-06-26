package swing;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;

public class ConfigPanel extends JPanel {
    final MainFrame frame;
    JLabel label;
    JSpinner spinner1;
    JSpinner spinner2;
    JButton createBtn = new JButton("Create");
    int rows, cols;

    public MainFrame getFrame() {
        return frame;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public JSpinner getSpinner() {
        return spinner1;
    }

    public void setSpinner(JSpinner spinner1) {
        this.spinner1 = spinner1;
    }

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        rows = frame.gameGraph.getRows();
        cols = frame.gameGraph.getCols();
        init();
    }

    private void init() {
        //create the label and the spinner1
        label = new JLabel("Grid size (RxC):");
        spinner1 = new JSpinner(new SpinnerNumberModel(frame.gameGraph.getRows()==0?10:frame.gameGraph.getRows(), 2, 100, 1));
        spinner2 = new JSpinner(new SpinnerNumberModel(frame.gameGraph.getRows()==0?10:frame.gameGraph.getCols(), 2, 100, 1));


        add(label); //JPanel uses FlowLayout by default
        add(spinner1);
        add(spinner2);
        add(createBtn);
        spinner1.addChangeListener(this::setRows);
        spinner2.addChangeListener(this::setCols);
        createBtn.addActionListener(this::createGame);
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    private void setRows(ChangeEvent changeEvent) {
        rows = (Integer) (spinner1.getValue());

        frame.setRows(rows);
        frame.gameGraph.setRows(frame.rows);
    }

    private void setCols(ChangeEvent changeEvent) {
        cols = (Integer) (spinner2.getValue());

        frame.setCols(cols);
        frame.gameGraph.setCols(frame.cols);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    private void createGame(ActionEvent actionEvent) {
        /**
         * sterg ce era inainte si pun noile setari
         */
        frame.saveGame = false;
        frame.gameGraph = new GameGraph<>();
        frame.gameGraph.removeAllEdges(frame.gameGraph.getGameEdgeSet());
        frame.gameGraph.removeAllVertices(frame.gameGraph.getGameNodeSet());
        frame.setCols(cols);
        frame.setRows(rows);
        frame.canvas.setCols(cols);
        frame.canvas.setRows(rows);
        frame.canvas.resize();
        frame.setPlayer(1);
        frame.gameGraph.setPrevGameNode(null);
        frame.gameGraph.depopulateNodes();
        frame.gameGraph.setRows(rows);
        frame.gameGraph.setCols(cols);
        frame.canvas.paintComponent(frame.canvas.getGraphics());
        frame.canvas.winningStrategy();

    }

}