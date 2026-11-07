package com.example.flytrapiot;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public final class MainActivity extends Pantalla {
    private ArrayAdapter<Trampa> adaptador;
    private TextView resumen;
    private TextView vacio;

    @Override
    protected void onCreate(Bundle estado) {
        super.onCreate(estado);
        mostrarPantalla(R.layout.activity_main);
        resumen = findViewById(R.id.txt_resumen);
        vacio = findViewById(R.id.txt_vacio);
        ListView lista = findViewById(R.id.lista_trampas);
        lista.setEmptyView(vacio);

        findViewById(R.id.boton_registrar).setOnClickListener(vista ->
                startActivity(new Intent(this, FormularioActivity.class)));

        adaptador = new ArrayAdapter<Trampa>(this, android.R.layout.simple_list_item_2) {
            @Override
            public View getView(int posicion, View reutilizada, ViewGroup contenedor) {
                View fila = reutilizada != null ? reutilizada : getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_2, contenedor, false);
                Trampa trampa = getItem(posicion);
                TextView titulo = fila.findViewById(android.R.id.text1);
                TextView detalle = fila.findViewById(android.R.id.text2);
                titulo.setText(getString(R.string.fila_titulo, trampa.codigoSector, trampa.tipoTrampa));
                detalle.setText(getString(R.string.fila_detalle, trampa.id, trampa.nivelCaptura));
                return fila;
            }
        };
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener((padre, vista, posicion, idFila) -> {
            Trampa trampa = adaptador.getItem(posicion);
            Intent intent = new Intent(this, FormularioActivity.class);
            intent.putExtra(FormularioActivity.EXTRA_ID, trampa.id);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            List<Trampa> trampas = db.listar();
            adaptador.setNotifyOnChange(false);
            adaptador.clear();
            adaptador.addAll(trampas);
            adaptador.notifyDataSetChanged();
            int total = trampas.size();
            resumen.setText(getResources().getQuantityString(R.plurals.total_trampas, total, total));
            vacio.setText(getString(R.string.vacio));
        } catch (SQLiteException error) {
            resumen.setText(getString(R.string.error_lectura));
            vacio.setText(getString(R.string.error_listado));
            avisar(getString(R.string.error_bd));
        }
    }
}
