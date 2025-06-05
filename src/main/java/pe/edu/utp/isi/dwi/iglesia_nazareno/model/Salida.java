package pe.edu.utp.isi.dwi.iglesia_nazareno.model;

import java.time.LocalDate;
import java.time.ZoneId; // Necesario para la conversión
import java.util.Date;   // Necesario para la conversión

/**
 * Representa una Salida de dinero de la iglesia (egresos).
 */
public class Salida {
    private int idSalida;
    private LocalDate fecha;
    private double monto;
    private String descripcion;
    private int idMinisterio;
    private int registradoPor;
    private String nombreMinisterio; // NUEVO: Para el nombre del ministerio
    private String nombreUsuarioRegistrador; // NUEVO: Para el nombre del usuario que registró

    public Salida() {
        // Constructor por defecto
    }

    public Salida(int idSalida, LocalDate fecha, double monto, String descripcion, int idMinisterio, int registradoPor) {
        this.idSalida = idSalida;
        this.fecha = fecha;
        this.monto = monto;
        this.descripcion = descripcion;
        this.idMinisterio = idMinisterio;
        this.registradoPor = registradoPor;
    }

    public int getIdSalida() {
        return idSalida;
    }
    public void setIdSalida(int idSalida) {
        this.idSalida = idSalida;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    // --- ¡Añade este nuevo getter para la compatibilidad! ---
    public Date getFechaAsUtilDate() {
        if (this.fecha == null) {
            return null;
        }
        // Convertimos LocalDate a java.util.Date usando la zona horaria por defecto del sistema.
        return Date.from(this.fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    // --------------------------------------------------------

    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public int getIdMinisterio() {
        return idMinisterio;
    }
    public void setIdMinisterio(int idMinisterio) {
        this.idMinisterio = idMinisterio;
    }
    public int getRegistradoPor() {
        return registradoPor;
    }
    public void setRegistradoPor(int registradoPor) {
        this.registradoPor = registradoPor;
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