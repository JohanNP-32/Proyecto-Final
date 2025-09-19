import java.util.ArrayList;
import java.util.List;

public class Condominio {
    private String nombre;
    private String direccion;
    private final List<Departamento> departamentos = new ArrayList<>();
    private final Administrador administrador;

    public Condominio(String nombre, String direccion, Administrador administrador) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.administrador = administrador;
    }

    public Condominio(Administrador administrador) {
        this.administrador = administrador;
    }

    public void agregarDepartamento(Departamento d) { departamentos.add(d); }

    public Departamento buscarDepartamento(int numero) {
        for (Departamento d : departamentos) {
            if (d.getNumero() == numero) return d;
        }
        return null;
    }

    public List<Departamento> getDepartamentos() { return departamentos; }

    // Aplica el cargo mensual de la cuota a todos
    public void generarCargosMensuales() {
        for (Departamento d : departamentos) d.registrarCargoMensual();
    }

    public double deudaTotal() {
        double t = 0;
        for (Departamento d : departamentos) t += d.calcularDeuda();
        return t;
    }

    @Override
    public String toString() {
        return nombre + " - " + direccion + " (Admin: " + (administrador == null ? "—" : administrador.getNombre()) + ")";
    }
}
