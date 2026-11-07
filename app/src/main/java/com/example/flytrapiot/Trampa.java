package com.example.flytrapiot;

final class Trampa {
    final long id;
    final String codigoSector;
    final String tipoTrampa;
    final int nivelCaptura;
    final String observaciones;

    Trampa(long id, String codigoSector, String tipoTrampa, int nivelCaptura,
           String observaciones) {
        this.id = id;
        this.codigoSector = codigoSector;
        this.tipoTrampa = tipoTrampa;
        this.nivelCaptura = nivelCaptura;
        this.observaciones = observaciones;
    }
}
