/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.database.Interface;

import com.project.database.object.Entity;
import java.util.ArrayList;

/**
 *
 * @author mamit
 */
public interface InterfaceDAO {
    public void insert(Entity entity)throws Exception;
    public void update(Entity entity,String... columnNames)throws Exception;
    public void delete(Entity entity,String... columnNames)throws Exception;
    public int count(Entity entity)throws Exception;
    public ArrayList get(Entity entity,String... columnNames)throws Exception;
    public ArrayList get(Entity entity,String columnName,int operation)throws Exception;
}
