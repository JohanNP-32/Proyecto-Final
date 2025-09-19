public class Residentes {
    private String nombre;
    private String telefono;
    private String correo;

    public Residentes(String nombre, String telefono, String correo) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getNombre() { return nombre; }
    @Override
    public String toString() { return nombre + " | Tel: " + telefono + " | Correo: " + correo; }
}