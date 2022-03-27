package Graphics;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;

public class ConfigPanel extends JPanel {
   final  MainFrame frame;
    JLabel label;
    JSpinner spinner1;
    JSpinner spinner2;
    JButton createBtn=new JButton("Create");
    int rows,cols;
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

    //...
    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        //create the label and the spinner1
        label = new JLabel("Grid size:");
        spinner1 = new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));
        spinner2= new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));
        //create spinners for rows and cols, and the button
        
        
 //...TODO
        add(label); //JPanel uses FlowLayout by default
        add(spinner1);
        add(spinner2);
        add(createBtn);
        spinner1.addChangeListener(this::setRows);
        spinner2.addChangeListener(this::setCols);
        createBtn.addActionListener(this::createGame);
    }

    private void setRows(ChangeEvent changeEvent) {
        rows=(Integer)(spinner1.getValue());
    }

    private void setCols(ChangeEvent changeEvent) {
        cols=(Integer)(spinner2.getValue());
    }

    private void createGame(ActionEvent actionEvent) {
        frame.setCols(cols);
        frame.setRows(rows);
    }
    
}