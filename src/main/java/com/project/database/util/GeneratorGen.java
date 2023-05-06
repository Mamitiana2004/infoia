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

/**
 *
 * @author mamit
 */
public class GeneratorGen{

    public static String insert(Entity entity) {
        try {
            String sql="insert into "+TableHelper.getTableName(entity)+"(";
            Field[] columns=TableHelper.getAllNotAutoComplete(entity);
            for (int i = 0; i < columns.length; i++) {
            	System.out.println(ColumnHelper.getColumnName(columns[i]));
                if (i==columns.length-1) {
                	System.out.println("////////////////");
                    sql+=ColumnHelper.getColumnName(columns[i])+")";
                }
                else{
                	System.out.println("-------------");
                    sql+=ColumnHelper.getColumnName(columns[i])+",";
                }
            }
        	System.out.println("//////--/////");
            sql+=" values (";
        	System.out.println(sql);
            String[] values=ColumnHelper.getValues(entity);
            for (int i = 0; i < values.length; i++) {
            	System.out.println("//////++/////");
            	System.out.println(values[i]);
                if (i==columns.length-1) {
                    sql+="'"+values[i]+"')";
                }
                else{
                    sql+="'"+values[i]+"',";
                }
            }
            System.out.println(sql);
            return sql;
        } catch (AnnotationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public static String delete(Entity entity) {
        try {
            String sql="delete from "+TableHelper.getTableName(entity)+" where ";
            Field fieldId=ColumnHelper.getPrimaryKey(entity);
            String valueId=ColumnHelper.toString(entity, fieldId);
            sql+=fieldId.getName()+"='"+valueId+"'";
            return sql;
        } catch (AnnotationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static String update(Entity entity) {
        try {
            String sql="update "+TableHelper.getTableName(entity)+" set ";
            Field fieldId=ColumnHelper.getPrimaryKey(entity);
            String valueId=ColumnHelper.toString(entity, fieldId);
            Field[] columns=TableHelper.getAllNotAutoComplete(entity);
            String[] values=ColumnHelper.getValues(entity);
            for (int i = 0; i < columns.length && i < values.length; i++) {
                if(!values[i].equalsIgnoreCase("0") && !values[i].equalsIgnoreCase("")){
                    if (i==columns.length-1) {
                        sql+=","+ColumnHelper.getColumnName(columns[i])+"='"+values[i]+"' ";
                    }
                    else if(i==0){
                        sql+=ColumnHelper.getColumnName(columns[i])+"='"+values[i]+"'";
                    }
                    else{
                        sql+=","+ColumnHelper.getColumnName(columns[i])+"='"+values[i]+"'";
                    }
                }
            }
            sql+="where "+fieldId.getName()+"='"+valueId+"'";
            System.out.println(sql);
            return sql;
        } catch (AnnotationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static String count(Entity entity) {
        try {
            return "select count(*) as nbr from "+TableHelper.getTableName(entity);
        } catch (AnnotationException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static String getAll(Entity entity) {
        try {
            return "select * from "+TableHelper.getTableName(entity);
        } catch (AnnotationException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    
}
