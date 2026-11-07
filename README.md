# FlyTrap IoT

Aplicación Android en Java para registrar y administrar trampas de mosca de la fruta en terreno. Guarda los datos localmente en SQLite y se puede usar sin conexión.

## Abrir y ejecutar

1. Descomprime el ZIP y abre la carpeta `FlyTrapIoT` en Android Studio con **File > Open**.
2. Instala **Android SDK Platform 37** desde **Tools > SDK Manager**.
3. Usa una versión de Android Studio compatible con **Android Gradle Plugin 9.4.1** y selecciona el **JDK integrado de Android Studio** como Gradle JDK. El Wrapper descarga **Gradle 9.6.0** durante la primera sincronización.
4. Ejecuta el módulo **app** en un emulador o teléfono con Android 7.0 o posterior. Para compilar desde la terminal, usa `gradlew.bat :app:assembleDebug` en Windows o `./gradlew :app:assembleDebug` en Linux y macOS.

## Prueba del CRUD

1. Abre la app. El listado debe aparecer vacío en una instalación nueva.
2. Toca **Registrar trampa**. Guarda `ARI-01`, `McPhail` y `12`. Puedes dejar Observaciones vacío.
3. Registra una segunda trampa y comprueba que aparecen ambas. Cierra y vuelve a abrir la app para verificar la persistencia.
4. Selecciona `ARI-01` y comprueba que se cargan sus datos. Cambia las capturas a `15` y guarda. El listado debe reflejar el cambio en ese registro.
5. Abre de nuevo `ARI-01` y toca **Eliminar trampa**. Debe desaparecer inmediatamente, mientras el otro registro sigue en el listado.
6. En un formulario nuevo, intenta guardar sin sector o con el nivel de captura vacío. La app debe señalar el campo y dejar el formulario abierto.

La interfaz usa una `ListView`, que vuelve a consultar SQLite al regresar del formulario. Al seleccionar una fila se envía su clave primaria mediante `Intent.putExtra()`. El formulario recupera, actualiza o elimina únicamente el registro identificado por esa clave. El nivel de captura se valida como un entero no negativo y las observaciones son opcionales.

## Requisitos de la pauta

| Puntos | Implementación |
| --- | --- |
| 10 | `DBHelper.onCreate` crea `flytrap_db` y la tabla `trampas` con el esquema obligatorio. |
| 12 | `FormularioActivity` valida los datos y `DBHelper.insertar` los registra mediante `ContentValues`. |
| 12 | `MainActivity` muestra la consulta completa de SQLite en una `ListView` y refresca en `onResume`. |
| 8 | El clic sobre una fila envía el ID real de SQLite mediante `Intent.putExtra`. |
| 10 | El formulario recibe el ID, precarga los datos y guarda los cambios mediante `update`. |
| 8 | El botón **Eliminar trampa** ejecuta `delete` por ID de forma inmediata, sin diálogo. |

## Referencias de clase

- [SQL_EJEMPLO_1](https://github.com/patodof/SQL_EJEMPLO_1) orientó el uso de `SQLiteOpenHelper`, `ContentValues`, `ListView` y el flujo de consulta, actualización y eliminación por ID.
- [Actividades_Android](https://github.com/patodof/Actividades_Android) orientó el envío y la recepción de datos entre actividades mediante `Intent` y sus extras.
- [Ejemplo_Sensores_2](https://github.com/patodof/Ejemplo_Sensores_2) orientó la organización de la interfaz de monitoreo en XML y su conexión con Java mediante `findViewById` y eventos de botones. Las capturas se ingresan en el formulario, según el flujo de la evaluación.

## Publicar en GitHub

El ZIP incluye el historial Git local. Desde la carpeta del proyecto, puedes revisarlo con `git log --oneline`.

Crea un repositorio vacío en tu cuenta de GitHub. Copia su URL y, en la terminal del proyecto, ejecuta estos comandos, sustituyendo la URL por la de tu repositorio.

```bash
git remote add origin https://github.com/TU-USUARIO/FlyTrapIoT.git
git push -u origin main
```

Adjunta el enlace del repositorio en el Aula Virtual y envíalo a `poyaneder@santotomas.cl`.
