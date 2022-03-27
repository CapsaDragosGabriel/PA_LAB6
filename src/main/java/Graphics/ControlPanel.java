package Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ControlPanel extends JPanel {
    final MainFrame frame;
    JButton exitBtn = new JButton("Exit");
    JButton loadBtn = new JButton("Load");
    JButton saveBtn = new JButton("Save");
    //create all buttons (Load, Exit, etc.)
// ...TODO
    public ControlPanel(MainFrame frame) {
        this.frame = frame; init();
    }
    private void init() {
        //change the default layout manager (just for fun)
        setLayout(new GridLayout(1, 4));
        //add all buttons ...TODO
        this.add(saveBtn,new GridLayout(1,4) );
        this.add(loadBtn,new GridLayout(2,3));
        this.add(exitBtn,new GridLayout(3,2));

        //configure listeners for all buttons
        exitBtn.addActionListener(this::exitGame);
       saveBtn.addActionListener(this::saveGame);
        loadBtn.addActionListener(this::loadGame);
 //...TODO
    }

    private void loadGame(ActionEvent actionEvent) {
    }

    private void saveGame(ActionEvent actionEvent) {

    }

    private void exitGame(ActionEvent e) {
        frame.dispose();
    }
 //...TODO
}