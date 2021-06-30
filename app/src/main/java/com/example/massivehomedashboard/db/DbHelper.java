package com.example.massivehomedashboard.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NOMBRE = "massivehome.db";

    public static final String TABLE_PROSPECTOS = " t_prospectos ";
    public static final String TABLE_PRODUCTOS = " t_productos ";
    public static final String TABLE_CATEGORIAS = " t_categorias ";
    public static final String TABLE_MARCAS = " t_marcas ";
    public static final String TABLE_GALLERY_PRODUCTS = " t_gallery_products ";

    public static final String CREATE = " CREATE TABLE IF NOT EXISTS ";

    // INTEGER FIELD
    public static final String C_id = " id INTEGER PRIMARY KEY AUTOINCREMENT ";
    public static final String C_id_ni = " id INTEGER PRIMARY KEY ";

    public static final String C_upload_flag = " upload_flag INTEGER NOT NULL DEFAULT 0 ";
    public static final String C_category_id = " category_id INTEGER ";
    public static final String C_brand_id = " brand_id INTEGER ";
    public static final String C_product_id = " product_id INTEGER ";

    // TEXT FIELDS
    public static final String C_name = " nombre TEXT NOT NULL ";
    public static final String C_name_contacto = " nombre_contacto TEXT NOT NULL ";
    public static final String C_codigo = " codigo TEXT NOT NULL ";
    public static final String C_empresa = " empresa TEXT NOT NULL ";
    public static final String C_telefono = " telefono TEXT NOT NULL ";
    public static final String C_email = " email TEXT NOT NULL ";
    public static final String C_comentarios = " comentarios TEXT NOT NULL ";
    public static final String C_descripcion = " descripcion TEXT NOT NULL ";
    public static final String C_image_name = " image_name TEXT NOT NULL ";
    public static final String C_created_at = " created_at TEXT ";
    public static final String C_statusId = " statusId INTEGER ";

    // DOUBLE FIELDS
    public static final String C_precio = " precio double ";
    public static final String C_precio2 = " precio2 double ";
    public static final String C_precio3 = " precio3 double ";

    public static final String coma = " , ";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE+TABLE_PROSPECTOS+"("+C_id+coma+C_name_contacto+coma+C_telefono+coma+C_email+coma+C_empresa+coma+C_comentarios+coma+C_created_at+coma+C_upload_flag+")");
        db.execSQL(CREATE+TABLE_PRODUCTOS+"("+C_id_ni+coma+C_codigo+coma+C_image_name+coma+C_category_id+coma+C_brand_id+coma+C_name+coma+C_descripcion+coma+C_precio+coma+C_precio2+coma+C_precio3+")");
        db.execSQL(CREATE+TABLE_CATEGORIAS+"("+C_id_ni+coma+C_category_id+coma+C_name+")");
        db.execSQL(CREATE+TABLE_MARCAS+"("+C_id_ni+coma+C_name+")");
        db.execSQL(CREATE+TABLE_GALLERY_PRODUCTS+"("+C_product_id+coma+C_image_name+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(" ALTER TABLE "+TABLE_CATEGORIAS+" ADD "+C_statusId);
        //db.execSQL(" DROP TABLE "+TABLE_PRODUCTOS);
        onCreate(db);
    }
}
