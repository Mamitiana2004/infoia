/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.database.util;

import com.project.database.annotation.Column;
import com.project.database.exception.AnnotationException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author mamit
 */
public class ColumnHelper {
    
    public static String getColumnName(Field field) throws AnnotationException{
        Column column=field.getAnnotation(Column.class);
        if(column!=null){
            if(column.value().equalsIgnoreCase("")){
                return field.getName().toLowerCase(Locale.ENGLISH);
            }
            else{
                return column.value().toLowerCase(Locale.ENGLISH);
            }
        }
        else{
            throw new AnnotationException("The field "+field.getName()+" doesn't have the annotation @Column");
        }
    }
    
    public static boolean isAnnotedWithColumn(Field field){
        Column column=field.getAnnotation(Column.class);
        return column!=null;
    }
    
    public static boolean isPrimaryKey(Field field) throws AnnotationException{
        return field.getAnnotation(Column.class).pk();
    }
    
    public static Field getPrimaryKey(Object object) throws AnnotationException{
        int nbrPk=0;
        Field value=null;
        Field[] fields=TableHelper.getAllAnnotedFieldWithColumn(object);
        for (Field field : fields) {
            if(ColumnHelper.isPrimaryKey(field)){
                value=field;
                nbrPk++;
            }
        }
        if(nbrPk==1){
            return value;
        }
        else if(nbrPk>1){
            throw  new AnnotationException("A database object must not have more than 1 pk :You have "+nbrPk+" primary key");
        }
        else{
            throw new AnnotationException("This class "+object.getClass().getSimpleName() +" doesn't have a primary key");
        }
    }
    
    public static boolean isAutoComplete(Field field){
        Column column=field.getAnnotation(Column.class);
        return column.autoComplete();
    }
    
    public static Object get(Object object,String column) throws IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        Class clazz=object.getClass();
        String function=column.substring(0,1).toUpperCase(Locale.FRENCH)+column.substring(1);
        Method method=clazz.getMethod("get"+function);
        Object value=method.invoke(object,  null);
        Method[] methods=clazz.getMethods();
        for (Method mth : methods) {
            if (mth.getName().toLowerCase(Locale.ENGLISH).equalsIgnoreCase(function.toLowerCase(Locale.ENGLISH))) {
                value=mth.invoke(object, null);
                break;
            }
        }
        return value;
    }
    
    public static String toString(Object object,Field field) throws IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
    	if(ColumnHelper.get(object, field.getName()).getClass()==Date.class){
        	System.out.println("Date");
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date=(Date)get(object, field.getName());
            return format.format(date);
        }
    	else if(ColumnHelper.get(object, field.getName()).getClass()==Time.class){
            Time date=(Time)get(object, field.getName());
            return date.toString();
        }
        field.setAccessible(true);
        if(field.get(object)!=null){
            return field.get(object).toString();
        }
        else{
            return "";
        }
    }
    
    public static String[] getValues(Object object)throws IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        Class clazz=object.getClass();
        ArrayList<String> list=new ArrayList<>();
        if (TableHelper.isAnnotedWithTable(object)) {
            Field[] listField=TableHelper.getAllNotAutoComplete(object);
            for (Field field : listField) {
                list.add(ColumnHelper.toString(object, field));
            }
        }
        String[] values=new String[list.size()];
        return list.toArray(values);
    }
    
    public static Field getByColumnName(Object object,String ColumnName) throws AnnotationException{
        Field[] fields=TableHelper.getAllAnnotedFieldWithColumn(object);
        for (Field field : fields) {
            String columnNameField=ColumnHelper.getColumnName(field);
            if(columnNameField.compareToIgnoreCase(ColumnName)==0){
                return field;
            }
        }
        throw  new AnnotationException("This column name "+ColumnName+" doesn't exist in this class "+object.getClass().getSimpleName());
    }
    
    public static String[] getValuesBy(Object object,String... columnNames)throws IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        Class clazz=object.getClass();
        ArrayList<String> list=new ArrayList<>();
        if (TableHelper.isAnnotedWithTable(object)) {
            Field[] listField=TableHelper.getAllNotAutoCompleteBy(object,columnNames);
            for (Field field : listField) {
                list.add(ColumnHelper.toString(object, field));
            }
        }
        String[] values=new String[list.size()];
        return list.toArray(values);
    }
    
    
    public Field getFieldByColumnName(Object object,String columnName) throws AnnotationException{
        Field[] fields=TableHelper.getAllAnnotedFieldWithColumn(object);
        for (Field field : fields) {
            if(ColumnHelper.getColumnName(field).equalsIgnoreCase(columnName)){
                return field;
            }
        }
        return null;
    }
}
