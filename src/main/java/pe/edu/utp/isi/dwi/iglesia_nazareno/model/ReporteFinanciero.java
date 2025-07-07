package pe.edu.utp.isi.dwi.iglesia_nazareno.model;

/**
 * Clase para agrupar los datos de un reporte financiero consolidado.
 */
public class ReporteFinanciero {
    private double totalDiezmos;
    private double totalOfrendasIglesia; // Solo las de ID_Ministerio = 1
    private double totalSalidas;
    private double saldoCaja; // totalDiezmos + totalOfrendasIglesia - totalSalidas

    public ReporteFinanciero() {
    }

    // Getters y Setters
    public double getTotalDiezmos() {
        return totalDiezmos;
    }

    public void setTotalDiezmos(double totalDiezmos) {
        this.totalDiezmos = totalDiezmos;
    }

    public double getTotalOfrendasIglesia() {
        return totalOfrendasIglesia;
    }

    public void setTotalOfrendasIglesia(double totalOfrendasIglesia) {
        this.totalOfrendasIglesia = totalOfrendasIglesia;
    }

    public double getTotalSalidas() {
        return totalSalidas;
    }

    public void setTotalSalidas(double totalSalidas) {
        this.totalSalidas = totalSalidas;
    }

    public double getSaldoCaja() {
        return saldoCaja;
    }

    public void setSaldoCaja(double saldoCaja) {
        this.saldoCaja = saldoCaja;
    }
}