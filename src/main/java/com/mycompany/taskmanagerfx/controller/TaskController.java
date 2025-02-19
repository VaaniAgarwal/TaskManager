/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanagerfx.controller;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
/**
 *
 * @author VAANI
 */
public class TaskController {
    private final Label label;
    private final TextField task;
    private final Button addtask;
    private final Button deletetask;
    private final ListView<String> Viewtask;
    private final VBox view;
    public TaskController()
    {
        label = new Label("Task Manager");
        task=new TextField();
        task.setPromptText("Enter task name");
        addtask=new Button("Add New Task");
        deletetask=new Button("Delete a Task");
        Viewtask=new ListView<>();
        view=new VBox(10);
        view.getChildren().addAll(label,task,addtask,deletetask,Viewtask);
    }
    public VBox getView()
    {
        return view;
    }
}
