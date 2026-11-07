package com.example.flytrapiot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

final class DBHelper extends SQLiteOpenHelper {
    private static final String TABLA = "trampas";
    private static final String FILTRO_ID = "id = ?";

    DBHelper(Context context) {
        super(context, "flytrap_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE trampas ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "codigo_sector TEXT NOT NULL, "
                + "tipo_trampa TEXT NOT NULL, "
                + "nivel_captura INTEGER NOT NULL, "
                + "observaciones TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new IllegalStateException("Falta migración de " + oldVersion + " a " + newVersion);
    }

    long insertar(Trampa trampa) {
        return getWritableDatabase().insertOrThrow(TABLA, null, valores(trampa));
    }

    List<Trampa> listar() {
        List<Trampa> trampas = new ArrayList<>();
        try (Cursor cursor = getReadableDatabase().query(
                TABLA, null, null, null, null, null, "id DESC")) {
            while (cursor.moveToNext()) {
                trampas.add(leer(cursor));
            }
        }
        return trampas;
    }

    Trampa buscar(long id) {
        try (Cursor cursor = getReadableDatabase().query(
                TABLA, null, FILTRO_ID, argumentos(id), null, null, null)) {
            return cursor.moveToFirst() ? leer(cursor) : null;
        }
    }

    boolean actualizar(Trampa trampa) {
        return getWritableDatabase().update(
                TABLA, valores(trampa), FILTRO_ID, argumentos(trampa.id)) == 1;
    }

    boolean eliminar(long id) {
        return getWritableDatabase().delete(TABLA, FILTRO_ID, argumentos(id)) == 1;
    }

    private String[] argumentos(long id) {
        return new String[]{Long.toString(id)};
    }

    private ContentValues valores(Trampa trampa) {
        ContentValues valores = new ContentValues();
        valores.put("codigo_sector", trampa.codigoSector);
        valores.put("tipo_trampa", trampa.tipoTrampa);
        valores.put("nivel_captura", trampa.nivelCaptura);
        valores.put("observaciones", trampa.observaciones);
        return valores;
    }

    private Trampa leer(Cursor cursor) {
        return new Trampa(cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("codigo_sector")),
                cursor.getString(cursor.getColumnIndexOrThrow("tipo_trampa")),
                cursor.getInt(cursor.getColumnIndexOrThrow("nivel_captura")),
                cursor.getString(cursor.getColumnIndexOrThrow("observaciones")));
    }
}
