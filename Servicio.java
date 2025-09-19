public class Servicio {
    private String tipoServicio;   
    private double costo;
    private String proveedor;

    public Servicio(String tipoServicio, double costo, String proveedor) {
        this.tipoServicio = tipoServicio;
        this.costo = costo;
        this.proveedor = proveedor;
    }

    public double calcularCostoMensual() { return costo; }

    @Override
    public String toString() {
        return tipoServicio + " | $" + costo + " | Prov: " + proveedor;
    }
}