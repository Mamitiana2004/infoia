/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.database.util;

import com.project.database.exception.AnnotationException;
import com.project.database.object.Entity;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mamit
 */
public class Generator{

    public static String getByPK(Entity entity) {
        try {
            String sql="select * from "+TableHelper.getTableName(entity);
            Field fieldId=ColumnHelper.getPrimaryKey(entity);
            String valueId=ColumnHelper.toString(entity, fieldId);
            sql+=" where "+fieldId.getName()+"='"+valueId+"'";
            return sql;
        } catch (AnnotationException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public static String getByPKValue(Entity entity,Object value) {
        try {
            String sql="select * from "+TableHelper.getTableName(entity);
            Field fieldId=ColumnHelper.getPrimaryKey(entity);
            sql+=" where "+fieldId.getName()+"='"+value+"'";
            return sql;
        } catch (AnnotationException ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String deleteBy(Entity entity, String... columnNames) {
        try {
            String sql="delete from "+TableHelper.getTableName(entity)+" where ";
            for (int i = 0; i < columnNames.length; i++) {
                String columnName = columnNames[i];
                Field fieldContent=ColumnHelper.getByColumnName(entity, columnName);
                if(i==columnNames.length-1){
                    sql+=fieldContent.getName()+"='"+ColumnHelper.toString(entity, fieldContent)+"'";
                }
                else{
                    sql+=fieldContent.getName()+"='"+ColumnHelper.toString(entity, fieldContent)+"' and ";
                }
            }
            return sql;
        } catch (AnnotationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public static String getBy(Entity entity, String... columnNames) {
        try {
            String sql="select * from "+TableHelper.getTableName(entity)+" where ";
            for (int i = 0; i < columnNames.length; i++) {
                String columnName = columnNames[i];
                Field fieldContent=ColumnHelper.getByColumnName(entity, columnName);
                if(i==columnNames.length-1){
                    sql+=fieldContent.getName()+"='"+ColumnHelper.toString(entity, fieldContent)+"'";
                }
                else{
                    sql+=fieldContent.getName()+"='"+ColumnHelper.toString(entity, fieldContent)+"' and ";
                }
            }
            return sql;
        } catch (AnnotationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public static String getBy(Entity entity,String columnName,int operation) {
        try {
            String sql="select * from "+TableHelper.getTableName(entity)+" where ";
            Field fieldContent=ColumnHelper.getByColumnName(entity, columnName);
            sql+=fieldContent.getName()+Helper.getOperation(operation)+"'"+ColumnHelper.toString(entity, fieldContent)+"'";
            return sql;
        } catch (AnnotationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public static String updateBy(Entity entity, String... columnNames) {
        try {
            String sql="update "+TableHelper.getTableName(entity)+" set ";
            Field[] columns=TableHelper.getAllNotAutoCompleteBy(entity,columnNames);
            String[] values=ColumnHelper.getValuesBy(entity,columnNames);
            for (int i = 0; i < columns.length && i < values.length; i++) {
                if(!values[i].equalsIgnoreCase("0") && !values[i].equalsIgnoreCase("")){
                    if (i==columns.length-1) {
                        sql+=ColumnHelper.getColumnName(columns[i])+"='"+values[i]+"' ";
                    }
                    else if(i==0){
                        sql+=ColumnHelper.getColumnName(columns[i])+"='"+values[i]+"'";
                    }
                    else{
                        sql+=","+ColumnHelper.getColumnName(columns[i])+"='"+values[i]+"'";
                    }
                }
            }
            sql+="where ";
            for (int i = 0; i < columnNames.length; i++) {
                String columnName = columnNames[i];
                Field fieldContent=ColumnHelper.getByColumnName(entity, columnName);
                if(i==columnNames.length-1){
                    sql+=fieldContent.getName()+"='"+ColumnHelper.toString(entity, fieldContent)+"'";
                }
                else{
                    sql+=fieldContent.getName()+"='"+ColumnHelper.toString(entity, fieldContent)+"' and ";
                }
            }
            return sql;
        } catch (AnnotationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public static String insertReturn(Entity entity, String objReturn) {
        try {
            String sql="insert into "+TableHelper.getTableName(entity)+"(";
            Field[] columns=TableHelper.getAllNotAutoComplete(entity);
            for (int i = 0; i < columns.length; i++) {
                if (i==columns.length-1) {
                    sql+=ColumnHelper.getColumnName(columns[i])+")";
                }
                else{
                    sql+=ColumnHelper.getColumnName(columns[i])+",";
                }
            }
            sql+=" values (";
            String[] values=ColumnHelper    .getValues(entity);
            for (int i = 0; i < values.length; i++) {
                if (i==columns.length-1) {
                    sql+="'"+values[i]+"')";
                }
                else{
                    sql+="'"+values[i]+"',";
                }
            }
            sql+=" return "+objReturn;
            return sql;
        } catch (AnnotationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
}
