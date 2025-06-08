package pe.edu.utp.isi.dwi.iglesia_nazareno.model;

import java.util.List;

public class ReporteMinisterios {
    private int idMinisterio;
    private String nombreMinisterio; 
    private List<Ofrenda> entradas;
    private List<Salida> salidas;
    private double totalEntradas;
    private double totalSalidas;
    private double totalEnCaja; 

    // Constructor
    public ReporteMinisterios() {
    }

    // Getters y Setters
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

    public List<Ofrenda> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Ofrenda> entradas) {
        this.entradas = entradas;
    }

    public List<Salida> getSalidas() {
        return salidas;
    }

    public void setSalidas(List<Salida> salidas) {
        this.salidas = salidas;
    }

    public double getTotalEntradas() {
        return totalEntradas;
    }

    public void setTotalEntradas(double totalEntradas) {
        this.totalEntradas = totalEntradas;
    }

    public double getTotalSalidas() {
        return totalSalidas;
    }

    public void setTotalSalidas(double totalSalidas) {
        this.totalSalidas = totalSalidas;
    }

    public double getTotalEnCaja() {
        return totalEnCaja;
    }

    public void setTotalEnCaja(double totalEnCaja) {
        this.totalEnCaja = totalEnCaja;
    }
}