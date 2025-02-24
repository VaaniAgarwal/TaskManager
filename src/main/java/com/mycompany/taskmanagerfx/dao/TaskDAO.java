/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanagerfx.dao;
import com.mycompany.taskmanagerfx.pojo.Task;
import java.sql.*;
import java.util.*;
/**
 *
 * @author VAANI
 */
public class TaskDAO {
    
    public Connection con = null;
    public Statement stmt = null;
    public ResultSet rs = null;
    public PreparedStatement pstmt = null;
    public static TaskDAO instance = null;
    
    public static synchronized TaskDAO getInstance() throws ClassNotFoundException
    {
        if (instance == null)
            instance = new TaskDAO();
 
        return instance;
    }
    
    private TaskDAO() throws ClassNotFoundException {
        try{            
            Class.forName("org.postgresql.Driver");
            con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","postgres");
            System.out.println("Connection Object--------->"+con);
            stmt=con.createStatement();
        }
        catch(SQLException e){            
        }
    }
    public void addTask(String id,String name)
    {
        try{
            pstmt = con.prepareStatement("Insert into tasks (taskid, taskname) values (?,?)");
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            System.out.println("New Task Added: "+name);
        }
        catch (SQLException ex){    
        }
    }
    
    public void delTask(String id,String name)
    {
        try{
            pstmt = con.prepareStatement("Delete from tasks where taskid = ?");
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            System.out.println("Task Deleted: "+name);
        }
        catch (SQLException ex){   
        }
    }
    
    public void updateTasks(String id, String newname)
    {
        try{
            pstmt=con.prepareStatement("Update tasks set taskname = ? where taskid = ?");
            pstmt.setString(1, newname);
            pstmt.setString(2, id);
            int rowsupdated = pstmt.executeUpdate();
            if(rowsupdated > 0)
            {
                System.out.println("Task Updated: "+id);
            }
        }
        catch(SQLException ex){
            
        }
    }
    public void updateStatus(String id, boolean status)
    {
        try{
            pstmt=con.prepareStatement("Update tasks set status = ? where taskid = ?");
            pstmt.setBoolean(1, status);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
            System.out.println("Task Status Updated: "+id+" -> "+status);
        }
        catch(SQLException e)
        {
            
        }
    }
    
    public ArrayList<Task> viewTasks()
    {
        ArrayList<Task> tasks = new ArrayList<>();
        try{
            rs = stmt.executeQuery("Select * from tasks");
            while(rs.next())
            {
                Task task = new Task(rs.getString("taskid"),rs.getString("taskname"));
                task.setStatus(rs.getBoolean("status"));
                tasks.add(task);
            }
        }
        catch(SQLException ex)
        {
            
        }
        return tasks;
    }
}
