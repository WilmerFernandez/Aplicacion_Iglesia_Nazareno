package pe.edu.utp.isi.dwi.iglesia_nazareno.model;

import java.time.LocalDate;
import java.time.ZoneId; // Necesario para la conversión
import java.util.Date;   // Necesario para la conversión

/**
 * Representa una Ofrenda general de la iglesia.
 */
public class Ofrenda {
    private int id;
    private LocalDate fecha;
    private double monto;
    private int idMinisterio; // ID del ministerio asociado a la ofrenda
    private int idUsuarioRegistrador; // ID del usuario que registra la ofrenda
    private String nombreMinisterio; // NUEVO: Para el nombre del ministerio
    private String nombreUsuarioRegistrador; // NUEVO: Para el nombre del usuario que registró

    public Ofrenda() {
        // Constructor por defecto
    }

    // Puedes añadir un constructor con todos los campos si lo necesitas
    public Ofrenda(int id, LocalDate fecha, double monto, int idMinisterio, int idUsuarioRegistrador) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.idMinisterio = idMinisterio;
        this.idUsuarioRegistrador = idUsuarioRegistrador;
    }

    // --- Getters y Setters existentes ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    // --- ¡Añade este nuevo getter! ---
    public Date getFechaAsUtilDate() {
        if (this.fecha == null) {
            return null;
        }
        // Convierte LocalDate a java.util.Date.
        // Se usa ZoneId.systemDefault() para la zona horaria del sistema.
        return Date.from(this.fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    // ----------------------------------

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    // --- Nuevos Getters y Setters para idMinisterio y idUsuarioRegistrador ---

    public int getIdMinisterio() {
        return idMinisterio;
    }

    public void setIdMinisterio(int idMinisterio) {
        this.idMinisterio = idMinisterio;
    }

    public int getIdUsuarioRegistrador() {
        return idUsuarioRegistrador;
    }

    public void setIdUsuarioRegistrador(int idUsuarioRegistrador) {
        this.idUsuarioRegistrador = idUsuarioRegistrador;
    }
    
    // --- NUEVOS Getters y Setters para los nombres ---
    public String getNombreMinisterio() {
        return nombreMinisterio;
    }
    public void setNombreMinisterio(String nombreMinisterio) {
        this.nombreMinisterio = nombreMinisterio;
    }

    public String getNombreUsuarioRegistrador() {
        return nombreUsuarioRegistrador;
    }
    public void setNombreUsuarioRegistrador(String nombreUsuarioRegistrador) {
        this.nombreUsuarioRegistrador = nombreUsuarioRegistrador;
    }
}