package pe.edu.utp.isi.dwi.iglesia_nazareno.model;

import java.sql.Timestamp;

public class Asistencia {
    private int idAsistencia;
    private Timestamp fecha;
    private int idMinisterio;
    private int cantidadAdultos;
    private int cantidadJovenes;
    private int cantidadAdolescentes;
    private int cantidadNinos;
    private int registradoPor;

    // Getters y Setters
    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public int getIdMinisterio() {
        return idMinisterio;
    }

    public void setIdMinisterio(int idMinisterio) {
        this.idMinisterio = idMinisterio;
    }

    public int getCantidadAdultos() {
        return cantidadAdultos;
    }

    public void setCantidadAdultos(int cantidadAdultos) {
        this.cantidadAdultos = cantidadAdultos;
    }

    public int getCantidadJovenes() {
        return cantidadJovenes;
    }

    public void setCantidadJovenes(int cantidadJovenes) {
        this.cantidadJovenes = cantidadJovenes;
    }

    public int getCantidadAdolescentes() {
        return cantidadAdolescentes;
    }

    public void setCantidadAdolescentes(int cantidadAdolescentes) {
        this.cantidadAdolescentes = cantidadAdolescentes;
    }

    public int getCantidadNinos() {
        return cantidadNinos;
    }

    public void setCantidadNinos(int cantidadNinos) {
        this.cantidadNinos = cantidadNinos;
    }

    public int getRegistradoPor() {
        return registradoPor;
    }

    public void setRegistradoPor(int registradoPor) {
        this.registradoPor = registradoPor;
    }
}
