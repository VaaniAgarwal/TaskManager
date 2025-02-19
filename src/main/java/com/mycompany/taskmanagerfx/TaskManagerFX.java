/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.taskmanagerfx;
import com.mycompany.taskmanagerfx.controller.TaskController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
  
/**
 *
 * @author VAANI
 */
public class TaskManagerFX extends Application {
    @Override
    public void start(Stage stage) 
    {
        TaskController controller=new TaskController();
        VBox view=controller.getView();
        Scene scene=new Scene(view,400,300);
        stage.setTitle("Task Manager");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
