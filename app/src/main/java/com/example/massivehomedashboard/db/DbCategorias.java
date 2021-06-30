package com.example.massivehomedashboard.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.massivehomedashboard.entidades.E_Categories;
import com.example.massivehomedashboard.entidades.E_Prospecto;

import java.util.ArrayList;
import java.util.HashMap;

public class DbCategorias extends DbHelper {

    Context oContext;
    public DbCategorias(@Nullable Context context) {
        super(context);
        this.oContext = context;
    }

    public long insert(HashMap<String,String> oHm){
        long id = 0;
        try{
            DbHelper oDbHelper = new DbHelper(oContext);
            SQLiteDatabase db = oDbHelper.getWritableDatabase();
            ContentValues cValues = new ContentValues();

            cValues.put("nombre", oHm.get("nombre").toString());
            cValues.put("id", oHm.get("id").toString());
            cValues.put("category_id", oHm.get("category_id").toString());
            cValues.put("statusId", oHm.get("statusId").toString());

            id = db.insert(TABLE_CATEGORIAS,null, cValues);
            db.close();
        }catch (Exception ex){
            ex.getMessage();
        }
        return id;
    }
    public ArrayList<E_Categories> getProspectos(String filtro){
        ArrayList<E_Categories> list = new ArrayList<>();
        E_Categories oCategories = null;
        Cursor oCursor = null;
        try{
            DbHelper oDbHelper = new DbHelper(oContext);
            SQLiteDatabase db = oDbHelper.getWritableDatabase();
            String sQuery = "SELECT id, category_id, nombre , statusId "+
                    "FROM "+TABLE_CATEGORIAS+"" +filtro;
            oCursor = db.rawQuery(sQuery,null);

            if(oCursor.moveToFirst()){
                do{
                    oCategories = new E_Categories();

                    oCategories.setId(oCursor.getInt(0));
                    oCategories.setCategory_id(oCursor.getInt(1));
                    oCategories.setNombre(oCursor.getString(2));
                    oCategories.setStatusId(oCursor.getInt(3));

                    list.add(oCategories);
                }while(oCursor.moveToNext());
            }
            oCursor.close();

        }catch (Exception exp){

        }
        return list;
    }
    public boolean ifExist(String filter){
        boolean flag = false;
        Cursor oCursor = null;
        try{
            DbHelper oDbHelper = new DbHelper(oContext);
            SQLiteDatabase db = oDbHelper.getWritableDatabase();
            String sQuery = "SELECT id FROM "+TABLE_CATEGORIAS+"" +filter;
            oCursor = db.rawQuery(sQuery,null);
            if(oCursor.getCount() > 0){
                flag = true;
            }
            oCursor.close();
        }catch (Exception exp){
            exp.printStackTrace();
        }
        return flag;
    }
    public boolean update(HashMap<String,String> oHm){
        boolean flag = false;
        DbHelper oDbHelper = new DbHelper(oContext);
        SQLiteDatabase db = oDbHelper.getWritableDatabase();
        try{
            ContentValues cValues = new ContentValues();
            cValues.put("category_id", oHm.get("category_id").toString());
            cValues.put("nombre", oHm.get("nombre").toString());
            cValues.put("statusId", oHm.get("statusId").toString());
            db.update(TABLE_CATEGORIAS,cValues,"id = "+oHm.get("id"),null);
            flag = true;

        }catch (Exception exp){
            exp.toString();
            flag = false;
        }finally {
            db.close();
        }
        return flag;
    }
}
