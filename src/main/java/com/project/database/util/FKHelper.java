/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.database.util;

import com.project.database.annotation.FK;
import com.project.database.exception.AnnotationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 *
 * @author mamit
 */
public class FKHelper {
    
    public static boolean isForeignKey(Field field){
        FK fk=field.getAnnotation(FK.class);
        return fk!=null;
    }
    
    public static String getForeignKeyTableReference(Field field) throws AnnotationException{
        if(ColumnHelper.isAnnotedWithColumn(field)){
            FK fk=field.getAnnotation(FK.class);
            if(fk!=null){
                return fk.tableReference();
            }else{
                throw new AnnotationException("The field "+field.getName()+" doesn't have the annotation @FK");
            }
        }
        else{
            throw new AnnotationException("The field "+field.getName()+" doesn't have the annotation @Column");
        }
    }
    
    
    public static Class getForeignKeyTableClassReference(Field field) throws AnnotationException, ClassNotFoundException, IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        if(ColumnHelper.isAnnotedWithColumn(field)){
            FK fk=field.getAnnotation(FK.class);
            if(fk!=null){
                Class[] clazz=Helper.getAllClassEntity();
                for (Class class1 : clazz) {
                    Object object=class1.getDeclaredConstructor().newInstance();
                    if(FKHelper.getForeignKeyTableReference(field).equalsIgnoreCase(TableHelper.getTableName(object))){
                        return class1;
                    }
                }
            }else{
                throw new AnnotationException("The field "+field.getName()+" doesn't have the annotation @FK");
            }
        }
        else{
            throw new AnnotationException("The field "+field.getName()+" doesn't have the annotation @Column");
        }
        return null;
    }
    
    public static String getFieldNameFK(Field field){
        return field.getAnnotation(FK.class).fieldName();
    }
    
    public static Field[] getAllForeignKey(Class objectClass){
        Field[] fields=objectClass.getDeclaredFields();
        ArrayList<Field> list=new ArrayList<>();
        for (Field field : fields) {
            if(FKHelper.isForeignKey(field)){
                list.add(field);
            }
        }
        Field[] result=new Field[list.size()];
        return list.toArray(result);
    }
    
    public static Field getField(Field fkField) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Class objectClass=fkField.getDeclaringClass();
        Object object=objectClass.getDeclaredConstructor().newInstance();
        Field[] fieldFKs=TableHelper.getAllForeignKey(object);
        Field[] fields=objectClass.getDeclaredFields();
        for (Field fieldFK : fieldFKs) {
            for (Field field : fields) {
                if(FKHelper.getFieldNameFK(fieldFK).equalsIgnoreCase(field.getName())){
                    return field;
                }
            }
        }
        return null;
    }
}
