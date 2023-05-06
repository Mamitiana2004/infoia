/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.database.object;

import com.project.database.exception.AnnotationException;
import com.project.database.util.ColumnHelper;
import com.project.database.util.FKHelper;
import com.project.database.util.Generic;
import com.project.database.util.OtherGeneric;
import com.project.database.util.TableHelper;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mamit
 */
public class Entity {
    public void save() throws Exception{
        Generic dao=new Generic();
        dao.insert(this);
    }
    
    public void update(String... columnNames) throws Exception{
        Generic dao=new Generic();
        dao.update(this,columnNames);
    }
    
    public void delete(String... columnNames) throws Exception{
        Generic dao=new Generic();
        dao.delete(this,columnNames);
    }
    
    public int count() throws Exception{
        Generic dao=new Generic();
        return dao.count(this);
    }
    
    public ArrayList getAll() throws Exception{
        Generic dao=new Generic();
        return dao.get(this);
    }
    
    public ArrayList get(String... columnNames) throws Exception{
        Generic dao=new Generic();
        return dao.get(this,columnNames);
    }
    
    public ArrayList get(String columnNames,int operation) throws Exception{
        Generic dao=new Generic();
        return dao.get(this,columnNames,operation);
    }
    
    @Override
    public String toString() {
        try {
            Class classTemp=this.getClass();
            String val=TableHelper.getTableName(this)+"{ ";
            Field[] fields=TableHelper.getAllAnnotedFieldWithColumnWithOutFK(this);
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if(i==fields.length-1){
                    val+=ColumnHelper.getColumnName(field)+"= "+ColumnHelper.toString(this, field);
                }
                else{
                    val+=ColumnHelper.getColumnName(field)+"= "+ColumnHelper.toString(this, field)+",";
                }
            }
            Field[] fieldFKs=TableHelper.getAllForeignKey(this);
            for (int i = 0; i < fieldFKs.length; i++) {
                Field fieldFK = fieldFKs[i];
                val+=","+fieldFK.getName()+"(FK)= ";
                Object obj=FKHelper.getForeignKeyTableClassReference(fieldFK).newInstance();
                obj=OtherGeneric.getById((Entity) obj, ColumnHelper.toString(this, fieldFK));
                val+=obj;
            }
            val+=" }";
            return val;
        } catch (AnnotationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            System.err.println(e.getMessage());
        } catch (InstantiationException ex) {
            Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error";
    }
}
