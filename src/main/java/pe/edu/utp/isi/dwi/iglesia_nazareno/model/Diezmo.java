package pe.edu.utp.isi.dwi.iglesia_nazareno.model;

import java.time.LocalDate;
import java.time.ZoneId; 
import java.util.Date;
/**
 * Representa un Diezmo registrado.
 */
public class Diezmo {
    private int id;
    private int idFeligres;
    private LocalDate fecha;
    private double monto;
    private int idRegistradoPor; // Campo para almacenar el ID del usuario que registró
    private String nombreFeligres; //  Para el nombre completo del feligrés
    private String nombreUsuarioRegistrador; //  Para el nombre del usuario que registró

    public Diezmo() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdFeligres() {
        return idFeligres;
    }
    public void setIdFeligres(int idFeligres) {
        this.idFeligres = idFeligres;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
     
    public Date getFechaAsUtilDate() {
        if (this.fecha == null) {
            return null;
        }
        // Convierte LocalDate a java.util.Date.
        return Date.from(this.fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    
    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }

    // Getter para el ID del usuario que registró
    public int getIdRegistradoPor() {
        return idRegistradoPor;
    }

    // Setter para el ID del usuario que registró
    public void setIdRegistradoPor(int idRegistradoPor) {
        this.idRegistradoPor = idRegistradoPor;
    }
    
 
    public String getNombreFeligres() {
        return nombreFeligres;
    }
    public void setNombreFeligres(String nombreFeligres) {
        this.nombreFeligres = nombreFeligres;
    }

    public String getNombreUsuarioRegistrador() {
        return nombreUsuarioRegistrador;
    }
    public void setNombreUsuarioRegistrador(String nombreUsuarioRegistrador) {
        this.nombreUsuarioRegistrador = nombreUsuarioRegistrador;
    }
}