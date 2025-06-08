package pe.edu.utp.isi.dwi.iglesia_nazareno.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Feligres {
    private int id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String estado; 
    private String telefono;      
    private String direccion;     
    private LocalDateTime fechaRegistro; 

    public Feligres() {}

    // Constructor actualizado
    public Feligres(int id, String nombre, String apellido, LocalDate fechaNacimiento, String estado, 
                    String telefono, String direccion, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y setters...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getTelefono() { return telefono; }   // Getter para Telefono
    public void setTelefono(String telefono) { this.telefono = telefono; }  // Setter para Telefono

    public String getDireccion() { return direccion; }   // Getter para Direccion
    public void setDireccion(String direccion) { this.direccion = direccion; } // Setter para Direccion

    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
