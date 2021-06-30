package com.example.massivehomedashboard.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.massivehomedashboard.entidades.E_Categories;
import com.example.massivehomedashboard.entidades.E_GalleryProducts;
import com.example.massivehomedashboard.entidades.E_Productos;

import java.util.ArrayList;
import java.util.HashMap;

public class DbGalleryProducts extends DbHelper{

    Context oContext;
    String table_name;
    public DbGalleryProducts(@Nullable Context context) {
        super(context);
        this.oContext = context;
        this.table_name = TABLE_GALLERY_PRODUCTS.trim();
    }
    public long insert(HashMap<String,String> oHm){
        long id = 0;
        try{
            DbHelper oDbHelper = new DbHelper(oContext);
            SQLiteDatabase db = oDbHelper.getWritableDatabase();
            ContentValues cValues = new ContentValues();

            cValues.put("product_id", oHm.get("product_id").toString());
            cValues.put("image_name", oHm.get("image_name").toString());

            id = db.insert(this.table_name,null, cValues);
            db.close();
        }catch (Exception ex){
            ex.getMessage();
        }
        return id;
    }
    public ArrayList<E_GalleryProducts> getGallery(String filtro){
        ArrayList<E_GalleryProducts> list = new ArrayList<>();
        E_GalleryProducts oGalleryProducts = null;
        Cursor oCursor = null;
        try{
            DbHelper oDbHelper = new DbHelper(oContext);
            SQLiteDatabase db = oDbHelper.getWritableDatabase();
            String sQuery = "SELECT product_id, image_name "+
                    "FROM "+this.table_name+"" +filtro;
            oCursor = db.rawQuery(sQuery,null);

            if(oCursor.moveToFirst()){
                do{
                    oGalleryProducts = new E_GalleryProducts();

                    oGalleryProducts.setProduct_id(oCursor.getInt(0));
                    oGalleryProducts.setImage_name(oCursor.getString(1));


                    list.add(oGalleryProducts);
                }while(oCursor.moveToNext());
            }
            oCursor.close();

        }catch (Exception exp){

        }
        return list;
    }
    public boolean deleteGallery(int product_id){
        boolean flag = false;
        DbHelper oDbHelper = new DbHelper(oContext);
        SQLiteDatabase db = oDbHelper.getWritableDatabase();
        try{
            db.execSQL(" DELETE FROM "+this.table_name+" WHERE product_id = "+product_id);
            flag = true;
        }catch (Exception exp){

        }
        return flag;
    }
}
