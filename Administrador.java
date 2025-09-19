import java.util.List;

public class Administrador {
    private String nombre;
    private String usuario;
    private String contrasena;

    public Administrador(String nombre, String usuario, String contrasena) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getNombre() { return nombre; }

    public void generarReporte() {
        System.out.println("Reporte general generado por " + nombre + ".");
    }

    public void asignarGasto(String concepto, double monto) {
        System.out.println("Gasto asignado: " + concepto + " por $" + monto);
    }

    // Recursividad (divide y vencerás) para sumar gastos de varios departamentos
    public double sumarGastos(List<Double> gastos) {
        if (gastos.isEmpty()) return 0;
        if (gastos.size() == 1) return gastos.get(0);
        int mid = gastos.size() / 2;
        double left = sumarGastos(gastos.subList(0, mid));
        double right = sumarGastos(gastos.subList(mid, gastos.size()));
        return left + right;
    }
}
