/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanagerfx.pojo;

/**
 *
 * @author VAANI
 */
public class Task {
    String id,name;
    boolean status;
    public Task(String id,String name)
    {
        this.id=id;
        this.name=name;
        this.status=false;
    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id=id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public boolean isCompleted()
    {
        return status;
    }
    public void setStatus(boolean status)
    {
        this.status=status;
    }
    @Override
    public String toString()
    {
        return id+"-> "+name;
    }
}
