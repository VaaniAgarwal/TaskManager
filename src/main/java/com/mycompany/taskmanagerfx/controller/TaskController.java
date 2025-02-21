/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanagerfx.controller;

import com.mycompany.taskmanagerfx.dao.TaskDAO;
import com.mycompany.taskmanagerfx.pojo.Task;
import java.util.ArrayList;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
/**
 *
 * @author VAANI
 */
public class TaskController {
    private final Label label;
    private final TextField taskId;
    private final TextField taskName;
    private final Button addbtn;
    private final Button delbtn;
    private final ListView<Task> viewTask;
    private final VBox view;
    private TaskDAO taskDAO;
    private ArrayList<Task> taskList;
    
    public TaskController()
    {
        try {
            taskDAO = TaskDAO.getInstance();
        } 
        catch (ClassNotFoundException ex) {
            System.out.println("Database connection failed: "+ex.getMessage());
        }
        label = new Label("Task Manager");
        taskId=new TextField();
        taskId.setPromptText("Enter Task ID");
        taskName=new TextField();
        taskName.setPromptText("Enter Task Name");
        addbtn=new Button("Add a Task");
        delbtn=new Button("Delete a Task");
        viewTask=new ListView<>();
        taskList = new ArrayList<>();
        
        if(taskDAO != null){
            taskList.addAll(taskDAO.viewTasks());
            update();
        }
        
        addbtn.setOnAction(e -> addTask());
        delbtn.setOnAction(e -> delTask());
        
        view=new VBox(10);
        view.getChildren().addAll(label,taskId,taskName,addbtn,delbtn,viewTask);
    }
    
    public VBox getView()
    {
        return view;
    }
    
    private void addTask()
    {
        String id = taskId.getText().trim();
        String name = taskName.getText().trim();
        if(!id.isEmpty() && !name.isEmpty())
        {
            taskDAO.addTask(id,name);
            taskList = taskDAO.viewTasks();
            update();
            taskId.clear();
            taskName.clear();
        }
    }
    
    private void delTask()
    {
        Task selTask = viewTask.getSelectionModel().getSelectedItem();
        if (selTask != null)
        {
            taskDAO.delTask(selTask.getId(),selTask.getName());
            taskList.remove(selTask);
            update();
        }
    }
    
    private void update()
    {
        viewTask.getItems().setAll(taskList);
    }
}
