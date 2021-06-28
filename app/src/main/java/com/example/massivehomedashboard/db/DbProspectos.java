package com.example.massivehomedashboard.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.massivehomedashboard.entidades.E_Prospecto;

import java.util.ArrayList;
import java.util.HashMap;

public class DbProspectos extends DbHelper {

    Context oContext;
    public DbProspectos(@Nullable Context context) {
        super(context);
        this.oContext = context;
    }

    public long insert(HashMap<String,String> oHm){
        long id = 0;
        try{
            DbHelper oDbHelper = new DbHelper(oContext);
            SQLiteDatabase db = oDbHelper.getWritableDatabase();
            ContentValues cValues = new ContentValues();
            cValues.put("nombre_contacto", oHm.get("nombre").toString());
            cValues.put("telefono", oHm.get("telefono").toString());
            cValues.put("email", oHm.get("email").toString());
            cValues.put("empresa", oHm.get("empresa").toString());
            cValues.put("comentarios", oHm.get("comentario").toString());
            cValues.put("created_at", oHm.get("created_at").toString());
            cValues.put("upload_flag", 0 );

            id = db.insert(TABLE_PROSPECTOS,null, cValues);
            db.close();
        }catch (Exception ex){
            ex.getMessage();
        }
        return id;
    }

    public ArrayList<E_Prospecto> getProspectos(String filtro){
        ArrayList<E_Prospecto> listProspectos = new ArrayList<>();
        E_Prospecto oProspectos = null;
        Cursor oCursor = null;
        try{
            DbHelper oDbHelper = new DbHelper(oContext);
            SQLiteDatabase db = oDbHelper.getWritableDatabase();
            String sQuery = "SELECT id, nombre_contacto, email,telefono,empresa,created_at,upload_flag,comentarios "+
                    "FROM "+TABLE_PROSPECTOS+"" +filtro;
            oCursor = db.rawQuery(sQuery,null);

            if(oCursor.moveToFirst()){
                do{
                    oProspectos = new E_Prospecto();
                    oProspectos.setId(oCursor.getInt(0));
                    oProspectos.setNombre_contacto(oCursor.getString(1));
                    oProspectos.setEmail(oCursor.getString(2));
                    oProspectos.setTelefono(oCursor.getString(3));
                    oProspectos.setEmpresa(oCursor.getString(4));
                    oProspectos.setCreated_at(oCursor.getString(5));
                    oProspectos.setUpdated_flag(oCursor.getInt(6));
                    oProspectos.setComentarios(oCursor.getString(7));
                    listProspectos.add(oProspectos);
                }while(oCursor.moveToNext());
            }
            oCursor.close();

        }catch (Exception exp){

        }
        return listProspectos;
    }
    public boolean update(HashMap<String,String> oHm){
        boolean flag = false;
        DbHelper oDbHelper = new DbHelper(oContext);
        SQLiteDatabase db = oDbHelper.getWritableDatabase();
        try{
            ContentValues cValues = new ContentValues();
            cValues.put("nombre_contacto", oHm.get("nombre").toString());
            cValues.put("telefono", oHm.get("telefono").toString());
            cValues.put("email", oHm.get("email").toString());
            cValues.put("empresa", oHm.get("empresa").toString());
            cValues.put("comentarios", oHm.get("comentario").toString());
            db.update(TABLE_PROSPECTOS,cValues,"id = "+oHm.get("id"),null);
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
