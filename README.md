# FlyTrap IoT

Aplicación Android en Java para registrar y administrar trampas de mosca de la fruta en terreno. Guarda los datos localmente en SQLite y se puede usar sin conexión.

## Prueba del CRUD

1. Abre la app. El listado debe aparecer vacío en una instalación nueva.
2. Toca **Registrar trampa**. Guarda `LLUT-03`, `Jack` y `8`. Puedes dejar Observaciones vacío.
3. Registra una segunda trampa y comprueba que aparecen ambas. Cierra y vuelve a abrir la app para verificar la persistencia.
4. Selecciona `LLUT-03` y comprueba que se cargan sus datos. Cambia las capturas a `8` y guarda. El listado debe reflejar el cambio en ese registro.
5. Abre de nuevo `LLUT-03` y toca **Eliminar trampa**. Debe desaparecer inmediatamente, mientras el otro registro sigue en el listado.
6. En un formulario nuevo, intenta guardar sin sector o con el nivel de captura vacío. La app debe señalar el campo y dejar el formulario abierto.

La interfaz usa una `ListView`, que vuelve a consultar SQLite al regresar del formulario. Al seleccionar una fila se envía su clave primaria mediante `Intent.putExtra()`. El formulario recupera, actualiza o elimina únicamente el registro identificado por esa clave. El nivel de captura se valida como un entero no negativo y las observaciones son opcionales.


## Referencias de clase

- [SQL_EJEMPLO_1](https://github.com/patodof/SQL_EJEMPLO_1) orientó el uso de `SQLiteOpenHelper`, `ContentValues`, `ListView` y el flujo de consulta, actualización y eliminación por ID.
- [Actividades_Android](https://github.com/patodof/Actividades_Android) orientó el envío y la recepción de datos entre actividades mediante `Intent` y sus extras.
- [Ejemplo_Sensores_2](https://github.com/patodof/Ejemplo_Sensores_2) orientó la organización de la interfaz de monitoreo en XML y su conexión con Java mediante 


