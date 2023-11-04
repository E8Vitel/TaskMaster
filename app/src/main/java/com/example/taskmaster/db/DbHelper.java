package com.example.taskmaster.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NOMBRE = "notas.db";
    public static final String TABLE_NOTAS = "t_notas";
    public static final String TABLE_SESION = "t_sesion";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NOTAS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "descripcion TEXT," +
                "fecha TEXT NOT NULL )");

        db.execSQL("CREATE TABLE " + TABLE_SESION + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "usuario TEXT NOT NULL, " +
                "email TEXT NOT NULL," +
                "contrasena TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("ALTER TABLE " + TABLE_NOTAS + " RENAME TO temp_" + TABLE_NOTAS);
            db.execSQL("CREATE TABLE " + TABLE_NOTAS + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "descripcion TEXT," +
                    "fecha TEXT NOT NULL )");
            db.execSQL("INSERT INTO " + TABLE_NOTAS + " (id, nombre, descripcion, fecha) " +
                    "SELECT id, nombre, descripcion, strftime('%Y-%m-%d', fecha) FROM temp_" + TABLE_NOTAS);
            db.execSQL("DROP TABLE temp_" + TABLE_NOTAS);
        }

    }

