/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.database.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import com.project.database.annotation.Table;
import com.project.database.object.Operator;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mamit
 */
public class Helper {
    public static Class<?>[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[0]);
    }

    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                if (packageName.length() == 0) {
                    classes.addAll(findClasses(file, file.getName()));
                } else {
                    classes.addAll(findClasses(file, packageName + "." + file.getName()));
                }
            } else if (file.getName().endsWith(".class")) {
                if (packageName.length() == 0) {
                    classes.add(Class.forName(file.getName().substring(0, file.getName().length() - 6)));
                } else {
                    classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
                }
            }
        }
        return classes;
    }

    public static Table getTableAnnotation(Class clazz){
        return (Table) clazz.getAnnotation(Table.class);
    }
    
    public static Class[] getAllClassEntity() throws ClassNotFoundException, IOException{
        Class[] classes=Helper.getClasses("");
        ArrayList<Class> list=new ArrayList<>();
        for (Class classe : classes) {
            if(Helper.getTableAnnotation(classe)!=null){
                list.add(classe);
            }
        }
        Class[] result=new Class[list.size()];
        return list.toArray(result);
    }
    
    public static String getOperation(int operation){
        switch (operation) {
            case Operator.EGAL:
                return "=";
            case Operator.INF:
                return "<";
            case Operator.INF_EGL:
                return "<=";
            case Operator.SUP:
                return ">";
            case Operator.SUP_EGL:
                return ">=";
            default:
                break;
        }
        return null;
    }
    
    public static String UCFirst(String name) {
        char[] charLi = name.toCharArray();
        String res = "" + charLi[0];
        res = res.toUpperCase(Locale.FRENCH);
        for (int i = 1; i < charLi.length; i++) {
            res += charLi[i];
        }
        return res;
    }
    
    public static Method setter(Field field){
        Class clTemp=field.getDeclaringClass();
        Class fieldClass=field.getType();
        try {
            return clTemp.getMethod("set"+UCFirst(field.getName()), fieldClass);
        } catch (NoSuchMethodException ex) {
            System.err.println("No such method set"+UCFirst(field.getName())+"("+fieldClass.getSimpleName()+") in the class "+clTemp.getSimpleName());
        } catch (SecurityException ex) {
            Logger.getLogger(Generic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Method getter(Field field){
        Class clTemp=field.getDeclaringClass();
        Class fieldClass=field.getType();
        try {
            return clTemp.getMethod("get"+UCFirst(field.getName()));
        } catch (NoSuchMethodException ex) {
            System.err.println("No such method get"+UCFirst(field.getName())+"() in the class "+clTemp.getSimpleName());
        } catch (SecurityException ex) {
            Logger.getLogger(Generic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
