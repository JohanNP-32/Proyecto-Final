import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

public class GestorPagos {
    private final List<Pago> pagos = new ArrayList<>();

// Cola de prioridades para pagos urgentes
    private PriorityQueue<Pago> colaPagosUrgentes = new PriorityQueue<>((p1, p2) -> Double.compare(p2.getMonto(), p1.getMonto()));

    public void registrarPago(Departamento d, double monto, String concepto) {
        if (d == null) { System.out.println("Departamento no encontrado."); return; }
        Pago p = new Pago(monto, new Date(), d, concepto);
        if (p.validarPago()) {
            pagos.add(p);
            colaPagosUrgentes.offer(p);
            d.registrarPago(monto);
            p.mostrarRecibo();
        } else {
            System.out.println("Pago invalido.");
        }
    }

    public double totalRecaudado() {
        return sumarMontos(pagos, 0, pagos.size() - 1);
    }

    // Recursividad (divide y vencerás) para sumar pagos
    private double sumarMontos(List<Pago> lista, int inicio, int fin) {
        if (inicio > fin) return 0;
        if (inicio == fin) return lista.get(inicio).getMonto();
        int mid = (inicio + fin) / 2;
        return sumarMontos(lista, inicio, mid) + sumarMontos(lista, mid + 1, fin);
    }

    public List<Pago> getPagos() { return pagos; }
}
