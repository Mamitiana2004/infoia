/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.database.util;

import com.project.database.Interface.InterfaceDAO;
import com.project.database.object.Entity;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author mamit
 */
public class Generic implements InterfaceDAO{
    

    @Override
    public void insert(Entity entity) throws Exception {
        try (Connection con = Connect.getConnection();
             PreparedStatement stmt = con.prepareStatement(GeneratorGen.insert(entity))) {
            stmt.execute();
        }
    }

    @Override
    public void update(Entity entity, String... columnNames) throws Exception {
        String sql="";
        if(columnNames.length==0){
            sql=GeneratorGen.update(entity);
        }
        else{
            sql=Generator.updateBy(entity, columnNames);
        }
        try (Connection con = Connect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.execute();
        }
    }

    @Override
    public void delete(Entity entity, String... columnNames) throws Exception {
        String sql="";
        if(columnNames.length==0){
            sql=GeneratorGen.delete(entity);
        }
        else{
            sql=Generator.deleteBy(entity, columnNames);
        }
        try (Connection con = Connect.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.execute();
        }
    }

    @Override
    public int count(Entity entity) throws Exception {
        int value=0;
        String sql=GeneratorGen.count(entity);
        try (Connection con = Connect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet res=stmt.executeQuery()){
            while(res.next()){
                value=res.getInt("nbr");
            }
        }
        return value;
    }

    @Override
    public ArrayList get(Entity entity, String... columnNames) throws Exception {
        ArrayList list=new ArrayList<>();
        String sql="";
        if(columnNames.length==0){
            sql=GeneratorGen.getAll(entity);
        }
        else{
            sql=Generator.getBy(entity, columnNames);
        }
        try(Connection con=Connect.getConnection();
            PreparedStatement stmt=con.prepareStatement(sql);
            ResultSet res=stmt.executeQuery()){
            while(res.next()){
                Entity object=entity.getClass().getDeclaredConstructor().newInstance();
                Field[] fields=TableHelper.getAllAnnotedFieldWithColumn(object);
                for (Field field : fields) {
                    Method set=Helper.setter(field);
                    set.invoke(object, res.getObject(ColumnHelper.getColumnName(field)));
                }
                Field[] fieldFKs=TableHelper.getAllForeignKey(object);
                for (Field fieldFK : fieldFKs) {
                    Field fkField=FKHelper.getField(fieldFK);
                    Method set=Helper.setter(fkField);
                    Entity o=(Entity) FKHelper.getForeignKeyTableClassReference(fieldFK).newInstance();
                    set.invoke(object, OtherGeneric.getById(o, res.getObject(ColumnHelper.getColumnName(fieldFK))));
                }
                list.add(object);
            }
        }
        return list;
    }

    @Override
    public ArrayList get(Entity entity, String columnName, int operation) throws Exception {
        ArrayList list=new ArrayList<>();
        try (Connection con = Connect.getConnection();
            PreparedStatement stmt = con.prepareStatement(Generator.getBy(entity, columnName, operation));
            ResultSet res = stmt.executeQuery()) {
            while (res.next()) {
                Entity object = entity.getClass().getDeclaredConstructor().newInstance();
                Field[] fields = TableHelper.getAllAnnotedFieldWithColumn(object);
                for (Field field : fields) {
                    Method set = Helper.setter(field);
                    set.invoke(object, res.getObject(ColumnHelper.getColumnName(field)));
                }
                Field[] fieldFKs = TableHelper.getAllForeignKey(object);
                for (Field fieldFK : fieldFKs) {
                    Field fkField = FKHelper.getField(fieldFK);
                    Method set = Helper.setter(fkField);
                    Entity o = (Entity) FKHelper.getForeignKeyTableClassReference(fieldFK).newInstance();
                    set.invoke(object, OtherGeneric.getById(o, res.getObject(ColumnHelper.getColumnName(fieldFK))));
                }
                list.add(object);
            }
       }

        return list;
    }
    
}
