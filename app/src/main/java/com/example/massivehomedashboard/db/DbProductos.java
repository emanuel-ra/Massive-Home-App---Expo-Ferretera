package com.example.massivehomedashboard.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.massivehomedashboard.entidades.E_Categories;
import com.example.massivehomedashboard.entidades.E_Productos;

import java.util.ArrayList;
import java.util.HashMap;

public class DbProductos extends DbHelper{
    Context oContext;
    String table_name;
    public DbProductos(@Nullable Context context) {
        super(context);
        this.oContext = context;
        this.table_name = TABLE_PRODUCTOS.trim();
    }

    public long insert(HashMap<String,String> oHm){
        long id = 0;
        DbHelper oDbHelper = new DbHelper(oContext);
        SQLiteDatabase db = oDbHelper.getWritableDatabase();
        db.beginTransaction();
        try{

            ContentValues cValues = new ContentValues();

            cValues.put("codigo", oHm.get("codigo").toString());
            cValues.put("nombre", oHm.get("nombre").toString());
            cValues.put("descripcion", oHm.get("descripcion").toString());
            cValues.put("id", oHm.get("id").toString());
            cValues.put("category_id", oHm.get("category_id").toString());
            cValues.put("brand_id", oHm.get("brand_id").toString());
            cValues.put("image_name", oHm.get("image_name").toString());
            cValues.put("precio", oHm.get("precio").toString());
            cValues.put("precio2", oHm.get("precio2").toString());
            cValues.put("precio3", oHm.get("precio3").toString());

            id = db.insert(this.table_name,null, cValues);
            db.setTransactionSuccessful();
            //db.close();
        }catch (Exception ex){
            ex.getMessage();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return id;
    }
    public ArrayList<E_Productos> getProductos(String filtro){
        ArrayList<E_Productos> list = new ArrayList<>();
        E_Productos oProductos = null;
        DbHelper oDbHelper = new DbHelper(oContext);
        SQLiteDatabase db = oDbHelper.getWritableDatabase();
        Cursor oCursor = null;
        try{
            String sQuery = "SELECT id, nombre, descripcion , category_id, brand_id , image_name , precio , precio2 , precio3 "+
                    "FROM "+this.table_name+"" +filtro;
            oCursor = db.rawQuery(sQuery,null);

            if(oCursor.moveToFirst()){
                do{
                    oProductos = new E_Productos();

                    oProductos.setId(oCursor.getInt(0));
                    oProductos.setNombre(oCursor.getString(1));
                    oProductos.setDescripcion(oCursor.getString(2));
                    oProductos.setCategory_id(oCursor.getInt(3));
                    oProductos.setBrand_id(oCursor.getInt(4));
                    oProductos.setImage_name(oCursor.getString(5));
                    oProductos.setPrecio(oCursor.getDouble(6));
                    oProductos.setPrecio2(oCursor.getDouble(7));
                    oProductos.setPrecio3(oCursor.getDouble(8));

                    list.add(oProductos);
                }while(oCursor.moveToNext());
            }

        }catch (Exception exp){
            exp.printStackTrace();
        }
        finally {
            db.close();
        }
        return list;
    }
    public boolean ifExist(String filter){
        boolean flag = false;
        Cursor oCursor = null;
        DbHelper oDbHelper = new DbHelper(oContext);
        SQLiteDatabase db = oDbHelper.getWritableDatabase();
        try{

            String sQuery = "SELECT id FROM "+this.table_name+"" +filter;
            oCursor = db.rawQuery(sQuery,null);
            if(oCursor.getCount() > 0){
                flag = true;
            }
        }catch (Exception exp){
            exp.printStackTrace();
        }
        finally {
            db.close();
        }
        return flag;
    }
    public boolean update(HashMap<String,String> oHm){
        boolean flag = false;
        DbHelper oDbHelper = new DbHelper(oContext);
        SQLiteDatabase db = oDbHelper.getWritableDatabase();

        db.beginTransaction();
        try{
            // id, nombre, descripcion , category_id, brand_id , image_name , precio , precio2 , precio3
            ContentValues cValues = new ContentValues();
            cValues.put("category_id", oHm.get("category_id").toString());
            cValues.put("nombre", oHm.get("nombre").toString());
            cValues.put("descripcion", oHm.get("descripcion").toString());
            cValues.put("image_name", oHm.get("image_name").toString());
            cValues.put("precio", oHm.get("precio").toString());
            cValues.put("precio2", oHm.get("precio2").toString());
            cValues.put("precio3", oHm.get("precio3").toString());
            cValues.put("brand_id", oHm.get("brand_id").toString());
            db.update(this.table_name,cValues,"id = "+oHm.get("id"),null);
            db.setTransactionSuccessful();
            flag = true;

        }catch (Exception exp){
            exp.toString();
            flag = false;
        }finally {
            db.endTransaction();
            db.close();
        }
        return flag;
    }
}
