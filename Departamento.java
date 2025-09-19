import java.util.ArrayList;
import java.util.List;

public class Departamento {
    private final int numero;
    private Residentes propietario;
    private List<Residentes> residentes = new ArrayList<>();
    private double cuotaMensual;
    private double deudaAcumulada = 0.0;

    public Departamento(int numero, double cuotaMensual) {
        this.numero = numero;
        this.cuotaMensual = cuotaMensual;
    }

    public Departamento(int numero) {
        this.numero = numero;
    }

    public int getNumero() { return numero; }
    public Residentes getPropietario() { return propietario; }
    public void setPropietario(Residentes propietario) { this.propietario = propietario; }
    public List<Residentes> getResidentes() { return residentes; }
    public void agregarResidente(Residentes r) { residentes.add(r); }
    public void registrarCargoMensual() { deudaAcumulada += cuotaMensual; }
    public void registrarPago(double monto) { deudaAcumulada = Math.max(0, deudaAcumulada - monto); }
    public double calcularDeuda() { return deudaAcumulada; }

    @Override
    public String toString() {
        String prop = (propietario == null ? "Libre" : propietario.getNombre());
        return "Departamento " + numero + " | Prop: " + prop + " | Cuota: $" + cuotaMensual + " | Deuda: $" + deudaAcumulada;
    }

    public void setResidentes(List<Residentes> residentes) {
        this.residentes = residentes;
    }
}
