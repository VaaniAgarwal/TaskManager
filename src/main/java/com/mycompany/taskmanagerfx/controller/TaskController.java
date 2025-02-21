/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanagerfx.controller;

import com.mycompany.taskmanagerfx.dao.TaskDAO;
import com.mycompany.taskmanagerfx.pojo.Task;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
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
    private final Button editbtn;
    private final Button updatebtn;
    private final ListView<Task> viewTask;
    private final VBox view;
    private final HBox buttonview;
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
        addbtn=new Button("Add Task");
        delbtn=new Button("Delete Task");
        editbtn=new Button("Edit Task");
        updatebtn = new Button("Update Task"); 
        updatebtn.setDisable(true);
        viewTask=new ListView<>();
        taskList = new ArrayList<>();
        
        if(taskDAO != null){
            taskList.addAll(taskDAO.viewTasks());
            update();
        }
        
        addbtn.setOnAction(e -> addTask());
        delbtn.setOnAction(e -> delTask());
        editbtn.setOnAction(e -> editTask());
        updatebtn.setOnAction(e -> updateTask());
        
        view=new VBox(10);
        view.setAlignment(Pos.CENTER);
        view.getChildren().addAll(label,taskId,taskName,viewTask);
        
        buttonview=new HBox(10);
        buttonview.getChildren().addAll(addbtn,delbtn,editbtn,updatebtn);
        buttonview.setAlignment(Pos.CENTER);
        
        view.getChildren().add(buttonview);  
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
    
    private void editTask()
    {
        Task selTask = viewTask.getSelectionModel().getSelectedItem();
        if (selTask != null)
        {
            taskName.setText(selTask.getName());
            taskId.setVisible(false);
            addbtn.setDisable(true);
            delbtn.setDisable(true);
            editbtn.setDisable(true);
            updatebtn.setDisable(false);
        }
    }
    
    public void updateTask()
    {
        Task selTask = viewTask.getSelectionModel().getSelectedItem();
        if (selTask != null)
        {
            String newname = taskName.getText().trim();
            if(!newname.isEmpty())
            {
                taskDAO.updateTasks(selTask.getId(),newname);
                selTask.setName(newname);
                update();
                taskName.clear();
                taskId.setVisible(true);
                addbtn.setDisable(false);
                delbtn.setDisable(false);
                editbtn.setDisable(false);
                updatebtn.setDisable(true);
            }
        }
    }
    
    private void update()
    {
        taskList.clear();
        taskList.addAll(taskDAO.viewTasks());
        viewTask.getItems().setAll(taskList);
    }
}
