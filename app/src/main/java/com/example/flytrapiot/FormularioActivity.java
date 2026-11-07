package com.example.flytrapiot;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public final class FormularioActivity extends Pantalla {
    static final String EXTRA_ID = "com.example.flytrapiot.ID_TRAMPA";
    private static final long NUEVA_TRAMPA = -1L;
    private long id;
    private EditText sector;
    private EditText tipo;
    private EditText captura;
    private EditText observaciones;

    @Override
    protected void onCreate(Bundle estado) {
        super.onCreate(estado);
        mostrarPantalla(R.layout.activity_formulario);
        id = getIntent().getLongExtra(EXTRA_ID, NUEVA_TRAMPA);
        boolean editando = id != NUEVA_TRAMPA;

        sector = findViewById(R.id.campo_sector);
        tipo = findViewById(R.id.campo_tipo);
        captura = findViewById(R.id.campo_captura);
        observaciones = findViewById(R.id.campo_observaciones);
        Button guardar = findViewById(R.id.boton_guardar);
        Button eliminar = findViewById(R.id.boton_eliminar);

        if (editando) {
            TextView modo = findViewById(R.id.txt_modo);
            TextView titulo = findViewById(R.id.txt_titulo);
            modo.setText(getString(R.string.registro_numero, id));
            titulo.setText(getString(R.string.editar));
            guardar.setText(getString(R.string.guardar_cambios));
            eliminar.setOnClickListener(vista -> eliminar());
        } else {
            eliminar.setVisibility(View.GONE);
        }

        guardar.setOnClickListener(vista -> guardar());
        findViewById(R.id.boton_volver).setOnClickListener(vista -> finish());
        if (estado == null) {
            findViewById(R.id.contenido_formulario).requestFocus();
        }

        if (editando && estado == null) {
            cargar();
        }
    }

    private void cargar() {
        try {
            Trampa trampa = db.buscar(id);
            if (trampa == null) {
                volverPorRegistroAusente();
                return;
            }
            sector.setText(trampa.codigoSector);
            tipo.setText(trampa.tipoTrampa);
            captura.setText(Integer.toString(trampa.nivelCaptura));
            observaciones.setText(trampa.observaciones);
        } catch (SQLiteException error) {
            avisar(getString(R.string.error_cargar));
            finish();
        }
    }

    private void guardar() {
        String codigo = sector.getText().toString().trim();
        String tipoTrampa = tipo.getText().toString().trim();
        if (codigo.isEmpty()) {
            marcarError(sector, getString(R.string.error_sector));
            return;
        }
        if (tipoTrampa.isEmpty()) {
            marcarError(tipo, getString(R.string.error_tipo));
            return;
        }

        int nivel;
        try {
            nivel = Integer.parseInt(captura.getText().toString().trim());
        } catch (NumberFormatException error) {
            marcarError(captura, getString(R.string.error_nivel_entero));
            return;
        }
        if (nivel < 0) {
            marcarError(captura, getString(R.string.error_nivel_negativo));
            return;
        }

        String nota = observaciones.getText().toString().trim();
        Trampa trampa = new Trampa(id, codigo, tipoTrampa, nivel, nota.isEmpty() ? null : nota);
        try {
            if (id == NUEVA_TRAMPA) {
                db.insertar(trampa);
                avisar(getString(R.string.registrada));
            } else if (db.actualizar(trampa)) {
                avisar(getString(R.string.cambios_guardados));
            } else {
                volverPorRegistroAusente();
                return;
            }
            finish();
        } catch (SQLiteException error) {
            avisar(getString(R.string.error_guardar));
        }
    }

    private void eliminar() {
        try {
            if (db.eliminar(id)) {
                avisar(getString(R.string.eliminada));
                finish();
            } else {
                volverPorRegistroAusente();
            }
        } catch (SQLiteException error) {
            avisar(getString(R.string.error_eliminar));
        }
    }

    private void marcarError(EditText campo, String mensaje) {
        campo.requestFocus();
        campo.setError(mensaje);
    }

    private void volverPorRegistroAusente() {
        avisar(getString(R.string.registro_ausente));
        finish();
    }
}
