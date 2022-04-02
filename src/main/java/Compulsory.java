// Java Program to create a
// blank label and add text to it.

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import swing.MainFrame;

class Compulsory{

    Button createBtn;

    public static void main(String[] args) {
       //launch(args);
         new MainFrame().setVisible(true);

    }
/*
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Laboratorul 6");
        createBtn = new Button("Create");

        StackPane layout = new StackPane();
        layout.getChildren().add( createBtn);

        Scene scene= new Scene(layout,300,250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }*/
}