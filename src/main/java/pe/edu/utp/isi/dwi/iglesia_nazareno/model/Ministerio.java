package pe.edu.utp.isi.dwi.iglesia_nazareno.model;

import java.sql.Timestamp;

public class Ministerio {
    private int idMinisterio;
    private String nombreMinisterio;
    private Timestamp fechaRegistro;

    public Ministerio() {
    }

    public Ministerio(int idMinisterio, String nombreMinisterio, Timestamp fechaRegistro) {
        this.idMinisterio = idMinisterio;
        this.nombreMinisterio = nombreMinisterio;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdMinisterio() {
        return idMinisterio;
    }

    public void setIdMinisterio(int idMinisterio) {
        this.idMinisterio = idMinisterio;
    }

    public String getNombreMinisterio() {
        return nombreMinisterio;
    }

    public void setNombreMinisterio(String nombreMinisterio) {
        this.nombreMinisterio = nombreMinisterio;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
