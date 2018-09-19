package com.example.valdemar.daaduniva.Models;

public class ItemPrograma {
    String id,fecha, horaIni, horaFin,nombre, descripcion, pdf;

    public String getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHoraIni() {
        return horaIni;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPdf() {
        return pdf;
    }

    public ItemPrograma(String id, String fecha, String horaIni, String horaFin, String nombre, String descripcion, String pdf) {

        this.id = id;
        this.fecha = fecha;
        this.horaIni = horaIni;
        this.horaFin = horaFin;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.pdf = pdf;
    }
}
