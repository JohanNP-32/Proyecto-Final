import java.util.Date;

public class Pago {
    private double monto;
    private Date fecha;
    private Departamento departamento;
    private String concepto;

    public Pago(double monto, Date fecha, Departamento departamento, String concepto) {
        this.monto = monto;
        this.fecha = fecha;
        this.departamento = departamento;
        this.concepto = concepto;
    }

    public boolean validarPago() { return monto > 0 && departamento != null; }
    public double getMonto() { return monto; }
    public Departamento getDepartamento() { return departamento; }
    public String getConcepto() { return concepto; }
    public Date getFecha() { return fecha; }

    public void mostrarRecibo() {
        System.out.println("Recibo: " + concepto + " | $" + monto +
                " | Departamento " + departamento.getNumero() + " | " + fecha);
    }
}