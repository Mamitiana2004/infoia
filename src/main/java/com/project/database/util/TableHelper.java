/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.database.util;

import java.util.Locale;
import com.project.database.annotation.Table;
import com.project.database.exception.AnnotationException;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 *
 * @author mamit
 */
public class TableHelper {
    
    public static String getTableName(Object object) throws AnnotationException{
        Table table=object.getClass().getAnnotation(Table.class);
        if(table!=null){
            if(table.value().equalsIgnoreCase("")){
                return object.getClass().getSimpleName().toLowerCase(Locale.ENGLISH);
            }
            else{
                return table.value().toLowerCase(Locale.ENGLISH);
            }
        }
        else{
            throw new AnnotationException("The class "+object.getClass().getSimpleName()+" doesn't have the annotation @Table");
        }
    }
    
    public static boolean isAnnotedWithTable(Object object){
        Table table=object.getClass().getAnnotation(Table.class);
        return table!=null;
    }
    
    public static Field[] getAllAnnotedFieldWithColumn(Object object){
        if(TableHelper.isAnnotedWithTable(object)){
            Field[] fields=object.getClass().getDeclaredFields();
            ArrayList<Field> listField=new ArrayList<>();
            for (Field field : fields) {
                if(ColumnHelper.isAnnotedWithColumn(field)){
                    listField.add(field);
                }
            }
            Field[] result=new Field[listField.size()];
            return listField.toArray(result);
        }
        return new Field[0];
    }
    
    public static Field[] getAllNotAutoComplete(Object object){
        Field[] fields=TableHelper.getAllAnnotedFieldWithColumn(object);
        ArrayList<Field> list=new ArrayList<>();
        for (Field field : fields) {
            if(!ColumnHelper.isAutoComplete(field)){
                list.add(field);
            }
        }
        Field[] result=new Field[list.size()];
        return list.toArray(result);
    }
    
    public static Field[] getAllNotPrimaryKey(Object object) throws AnnotationException{
        Field[] fields=object.getClass().getDeclaredFields();
        ArrayList<Field> list=new ArrayList<>();
        for (Field field : fields) {
            if(!ColumnHelper.isPrimaryKey(field)){
                list.add(field);
            }
        }
        Field[] result=new Field[list.size()];
        return list.toArray(result);
    }
    
    public static Field[] getAllForeignKey(Object object){
        Field[] fields=object.getClass().getDeclaredFields();
        ArrayList<Field> list=new ArrayList<>();
        for (Field field : fields) {
            if(FKHelper.isForeignKey(field)){
                list.add(field);
            }
        }
        Field[] result=new Field[list.size()];
        return list.toArray(result);
    }
    
    public static Field[] getAllAutoComplete(Object object){
        Field[] fields=object.getClass().getDeclaredFields();
        ArrayList<Field> list=new ArrayList<>();
        for (Field field : fields) {
            if(ColumnHelper.isAutoComplete(field)){
                list.add(field);
            }
        }
        Field[] result=new Field[list.size()];
        return list.toArray(result);
    }
    
    public static Field[] getAllNotAutoCompleteBy(Object object,String... columnNames){
        Field[] fields=object.getClass().getDeclaredFields();
        ArrayList<Field> list=new ArrayList<>();
        for (Field field : fields) {
            if(!ColumnHelper.isAutoComplete(field)){
                boolean same=false;
                for (String columnName : columnNames) {
                    if(field.getName().equalsIgnoreCase(columnName)){
                        same=true;
                    }
                }
                if(!same){
                    list.add(field);
                }
            }
        }
        Field[] result=new Field[list.size()];
        return list.toArray(result);
    }
    
    public static Field[] getAllAnnotedFieldWithColumnWithOutFK(Object object){
        if(TableHelper.isAnnotedWithTable(object)){
            Field[] fields=object.getClass().getDeclaredFields();
            ArrayList<Field> listField=new ArrayList<>();
            for (Field field : fields) {
                if(ColumnHelper.isAnnotedWithColumn(field) && !FKHelper.isForeignKey(field)){
                    listField.add(field);
                }
            }
            Field[] result=new Field[listField.size()];
            return listField.toArray(result);
        }
        return new Field[0];
    }
}
