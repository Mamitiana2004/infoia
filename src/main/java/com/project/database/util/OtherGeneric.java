/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.database.util;

import com.project.database.object.Entity;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author mamit
 */
public class OtherGeneric {
    
    public static Object getById(Entity entity,Object id) throws Exception {
       Object value=null;
        try(Connection con=Connect.getConnection();
            PreparedStatement stmt=con.prepareStatement(Generator.getByPKValue(entity, id));
            ResultSet res=stmt.executeQuery()){
            Entity object=entity.getClass().getDeclaredConstructor().newInstance();
            while(res.next()){
                Field[] fields=TableHelper.getAllAnnotedFieldWithColumn(object);
                for (Field field : fields) {
                    Method set=Helper.setter(field);
                    set.invoke(object, res.getObject(ColumnHelper.getColumnName(field)));
                }
            }
            value=object;
        }
        return value;
    }
    
}
