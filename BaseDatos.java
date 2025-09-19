import java.util.ArrayList;
import java.util.List;

public class BaseDatos {
    private List<Residentes> residentes = new ArrayList<>();
    private List<Departamento> departamentos = new ArrayList<>();
    private List<Servicio> servicios = new ArrayList<>();

    // Árbol binario para departamentos
    private Nodo raiz;

    private class Nodo {
        Departamento dpto;
        Nodo izq, der;
        Nodo(Departamento d) { this.dpto = d; }
    }

    public void agregarResidente(Residentes r) { residentes.add(r); }
    public List<Residentes> getResidentes() { return residentes; }

    public void eliminarResidentePorNombre(String nombre) {
        residentes.removeIf(r -> r.getNombre().equalsIgnoreCase(nombre));
    }

    public void agregarDepartamento(Departamento d) { 
        departamentos.add(d);
        raiz = insertarDepartamento(raiz, d);
    }

    // Árbol binario para organizar departamentos
    private Nodo insertarDepartamento(Nodo actual, Departamento d) {
        if (actual == null) return new Nodo(d);
        if (d.getNumero() < actual.dpto.getNumero())
            actual.izq = insertarDepartamento(actual.izq, d);
        else
            actual.der = insertarDepartamento(actual.der, d);
        return actual;
    }

    public Departamento buscarDepartamento(int numero) {
        return buscarDepartamentoRec(raiz, numero);
    }

    private Departamento buscarDepartamentoRec(Nodo actual, int numero) {
        if (actual == null) return null;
        if (numero == actual.dpto.getNumero()) return actual.dpto;
        return numero < actual.dpto.getNumero() ? buscarDepartamentoRec(actual.izq, numero) : buscarDepartamentoRec(actual.der, numero);
    }

    public List<Departamento> getDepartamentos() { return departamentos; }

    public void registrarServicio(Servicio s) { servicios.add(s); }
    public List<Servicio> getServicios() { return servicios; }
}
