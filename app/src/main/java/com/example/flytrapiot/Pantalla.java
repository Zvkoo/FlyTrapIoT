package com.example.flytrapiot;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Toast;

abstract class Pantalla extends AppCompatActivity {
    protected DBHelper db;

    @Override
    protected void onCreate(Bundle estado) {
        super.onCreate(estado);
        db = new DBHelper(this);
    }

    protected void mostrarPantalla(int layout) {
        EdgeToEdge.enable(this);
        setContentView(layout);
        View raiz = findViewById(R.id.main);
        int izquierda = raiz.getPaddingLeft();
        int arriba = raiz.getPaddingTop();
        int derecha = raiz.getPaddingRight();
        int abajo = raiz.getPaddingBottom();
        ViewCompat.setOnApplyWindowInsetsListener(raiz, (vista, insets) -> {
            Insets margen = insets.getInsets(WindowInsetsCompat.Type.systemBars()
                    | WindowInsetsCompat.Type.displayCutout() | WindowInsetsCompat.Type.ime());
            vista.setPadding(izquierda + margen.left, arriba + margen.top,
                    derecha + margen.right, abajo + margen.bottom);
            return insets;
        });
        ViewCompat.requestApplyInsets(raiz);
    }

    protected void avisar(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
