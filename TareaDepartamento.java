import java.util.Date;

public class TareaDepartamento implements Comparable<TareaDepartamento> {
    private String descripcion;
    private Departamento departamento;
    private int urgencia; // 1 = Alta, 2 = Media, 3 = Baja
    private Date fechaEntrega;

    public TareaDepartamento(String descripcion, Departamento departamento, int urgencia, Date fechaEntrega) {
        this.descripcion = descripcion;
        this.departamento = departamento;
        this.urgencia = urgencia;
        this.fechaEntrega = fechaEntrega;
    }

    public String getDescripcion() { return descripcion; }
    public Departamento getDepartamento() { return departamento; }
    public int getUrgencia() { return urgencia; }
    public Date getFechaEntrega() { return fechaEntrega; }

    @Override
    public String toString() {
        return descripcion + " | Dpto: " + (departamento == null ? "General" : departamento.getNumero()) +
               " | Urgencia: " + urgencia + " | Fecha: " + fechaEntrega;
    }

    //Ordena primero por urgencia, luego por fecha
    @Override
    public int compareTo(TareaDepartamento otra) {
        if (this.urgencia != otra.urgencia) {
            return Integer.compare(this.urgencia, otra.urgencia);
        }
        return this.fechaEntrega.compareTo(otra.fechaEntrega);
    }
}
