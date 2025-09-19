import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class GestorMantenimiento {
    private final List<Servicio> servicios = new ArrayList<>();

    // Cola de prioridad para servicios según costo
    private PriorityQueue<Servicio> colaServicios = new PriorityQueue<>((s1, s2) -> Double.compare(s2.calcularCostoMensual(), s1.calcularCostoMensual()));

    public void registrarServicio(Servicio s) {
        servicios.add(s);
        colaServicios.offer(s);
        System.out.println("Servicio registrado: " + s);
    }

    public double dividirGastosEntre(List<Departamento> departamentos) {
        if (departamentos.isEmpty()) return 0;
        double total = dividirCostosRec(servicios, 0, servicios.size() - 1);
        return total / departamentos.size();
    }

    // Divide y vencerás para sumar costos
    private double dividirCostosRec(List<Servicio> lista, int inicio, int fin) {
        if (inicio > fin) return 0;
        if (inicio == fin) return lista.get(inicio).calcularCostoMensual();
        int mid = (inicio + fin) / 2;
        return dividirCostosRec(lista, inicio, mid) + dividirCostosRec(lista, mid + 1, fin);
    }

    public List<Servicio> getServicios() { return servicios; }

    public PriorityQueue<Servicio> getColaServicios() {
        return colaServicios;
    }

    public void setColaServicios(PriorityQueue<Servicio> colaServicios) {
        this.colaServicios = colaServicios;
    }
}
