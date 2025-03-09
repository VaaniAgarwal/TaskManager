/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanagerfx.controller;

import com.mycompany.taskmanagerfx.dao.TaskDAO;
import com.mycompany.taskmanagerfx.pojo.Task;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private final TextField search;
    private final ListView<Task> viewTask;
    private final VBox view;
    private final HBox buttonview;
    private TaskDAO taskDAO;
    private ArrayList<Task> taskList;
    private final DatePicker dueDate;
    
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
        dueDate = new DatePicker();
        dueDate.setPromptText("Set Due Date");
        addbtn=new Button("Add Task");
        delbtn=new Button("Delete Task");
        editbtn=new Button("Edit Task");
        updatebtn = new Button("Update Task"); 
        updatebtn.setDisable(true);
        search = new TextField();
        search.setPromptText("Search Tasks");
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
        search.textProperty().addListener((obs, oldValue, newValue) -> { 
            searchTasks(newValue);
        });
        
        view=new VBox(10);
        view.setAlignment(Pos.CENTER);
        view.getChildren().addAll(label,search, taskId,taskName,dueDate,viewTask);
        
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
        LocalDate due = dueDate.getValue();
        if(!id.isEmpty() && !name.isEmpty() && due != null )
        {
            taskDAO.addTask(id,name,due);
            taskList = taskDAO.viewTasks();
            update();
            taskId.clear();
            taskName.clear();
            dueDate.setValue(null);
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
            dueDate.setValue(selTask.getDueDate());
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
            LocalDate newDueDate = dueDate.getValue();
            if(!newname.isEmpty())
            {
                taskDAO.updateTasks(selTask.getId(),newname,newDueDate);
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
    
    public void searchTasks(String s)
    {
        if(s.isEmpty())
        {
            viewTask.getItems().setAll(taskList);
        }
        else
        {
            List<Task> searchedTasks = taskList.stream()
                    .filter(task -> task.getName().toLowerCase().contains(s.toLowerCase()) || 
                            task.getId().toLowerCase().contains(s.toLowerCase())).toList();
            viewTask.getItems().setAll(searchedTasks);
        }
    }
    
    private void update()
    {
        viewTask.getItems().clear();
        taskList.clear();
        taskList.addAll(taskDAO.viewTasks());
        viewTask.setCellFactory(param -> new ListCell<Task>() {
            private final CheckBox ch = new CheckBox();
            
            @Override
            protected void updateItem(Task t, boolean empty)
            {
                super.updateItem(t, empty);
                if(empty || t == null)
                {
                    setGraphic(null);
                }
                else
                {
                    ch.setText(t.getName()+" (Due: "+(t.getDueDate() != null ? t.getDueDate():"No Date") + ")");
                    ch.setSelected(t.isCompleted());
                    ch.setOnAction(e -> {
                        t.setStatus(ch.isSelected());
                        taskDAO.updateStatus(t.getId(), t.isCompleted());
                    });
                    setGraphic(ch);
                }
            }
        });
        viewTask.getItems().setAll(taskList);
    }
}
