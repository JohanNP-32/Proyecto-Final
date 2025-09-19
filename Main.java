import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    
    private static int leerInt(Scanner sc, String msg) {
        System.out.print(msg);
        while (!sc.hasNextInt()) {
            System.out.print("Numero invalido. Intenta de nuevo: ");
            sc.next();
        }
        int v = sc.nextInt();
        sc.nextLine();
        return v;
    }

    private static double leerDouble(Scanner sc, String msg) {
        System.out.print(msg);
        while (!sc.hasNextDouble()) {
            System.out.print("Monto invalido. Intenta de nuevo: ");
            sc.next();
        }
        double v = sc.nextDouble();
        sc.nextLine();
        return v;
    }


    static class TareaDepartamento implements Comparable<TareaDepartamento> {
        private String descripcion;
        private Departamento departamento;
        private int prioridad; 
        private Date fechaEntrega; 
        private int tiempoEstimadoMin; 
        public TareaDepartamento(String descripcion, Departamento departamento) {
            this(descripcion, departamento, 0, null, 0);
        }

        public TareaDepartamento(String descripcion, Departamento departamento, int prioridad, Date fechaEntrega, int tiempoEstimadoMin) {
            this.descripcion = descripcion;
            this.departamento = departamento;
            this.prioridad = prioridad;
            this.fechaEntrega = fechaEntrega;
            this.tiempoEstimadoMin = tiempoEstimadoMin;
        }

        public String getDescripcion() { return descripcion; }
        public Departamento getDepartamento() { return departamento; }
        public int getPrioridad() { return prioridad; }
        public Date getFechaEntrega() { return fechaEntrega; }
        public int getTiempoEstimadoMin() { return tiempoEstimadoMin; }

        @Override
        public String toString() {
            String dep = (departamento == null ? "General" : String.valueOf(departamento.getNumero()));
            String fechaStr = (fechaEntrega == null ? "—" : new SimpleDateFormat("yyyy-MM-dd").format(fechaEntrega));
            return "[Depto " + dep + "] " + descripcion + " | P:" + prioridad + " | Entrega:" + fechaStr + " | Est:" + tiempoEstimadoMin + "m";
        }

        
        @Override
        public int compareTo(TareaDepartamento o) {
            if (this.prioridad != o.prioridad) return Integer.compare(o.prioridad, this.prioridad); 
            if (this.fechaEntrega == null && o.fechaEntrega == null) return 0;
            if (this.fechaEntrega == null) return 1;
            if (o.fechaEntrega == null) return -1;
            return this.fechaEntrega.compareTo(o.fechaEntrega);
        }
    }

    
    static class Empleado {
        int id;
        String nombre;
        String departamento;
        

        public Empleado(int id, String nombre, String departamento) {
            this.id = id;
            this.nombre = nombre;
            this.departamento = departamento;
        }

        @Override
        public String toString() {
            return "Empleado{" + id + " - " + nombre + " - " + departamento + "}";
        }
    }

    static class NodoEmpleado {
        Empleado emp;
        NodoEmpleado izq, der;
        public NodoEmpleado(Empleado e) { this.emp = e; }
    }

    static class BSTEmpleado {
        private NodoEmpleado raiz = null;

        public void insertar(Empleado e) {
            raiz = insertarRec(raiz, e);
        }

        private NodoEmpleado insertarRec(NodoEmpleado nodo, Empleado e) {
            if (nodo == null) return new NodoEmpleado(e);
            // ordenar por id 
            if (e.id < nodo.emp.id) nodo.izq = insertarRec(nodo.izq, e);
            else nodo.der = insertarRec(nodo.der, e);
            return nodo;
        }

        public Empleado buscarPorId(int id) {
            NodoEmpleado n = raiz;
            while (n != null) {
                if (id == n.emp.id) return n.emp;
                if (id < n.emp.id) n = n.izq; else n = n.der;
            }
            return null;
        }

        // recorrido inorder  para listar empleados ordenados
        public void inorderImprimir() { inorder(raiz); }
        private void inorder(NodoEmpleado n) {
            if (n == null) return;
            inorder(n.izq);
            System.out.println(n.emp);
            inorder(n.der);
        }
    }

    // ===================== Main =====================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Estructuras de datos para las tareas
        Stack<TareaDepartamento> pilaUrgentes = new Stack<>();
        Queue<TareaDepartamento> colaRegulares = new LinkedList<>();
        ArrayList<TareaDepartamento> listaPendientes = new ArrayList<>();

        //cola de prioridad (gestiona tareas por prioridad+fecha)
        PriorityQueue<TareaDepartamento> colaPrioridad = new PriorityQueue<>();

        //Inicialización de objetos del programa 
        BaseDatos db = new BaseDatos();
        Administrador admin = new Administrador("Admin General", "admin", "1234");
        Condominio condominio = new Condominio("Condominio Central", "Av. Principal 100", admin);
        GestorPagos gPagos = new GestorPagos();
        GestorMantenimiento gMant = new GestorMantenimiento();

        // BST de empleados 
        BSTEmpleado arbolEmpleados = new BSTEmpleado();
        arbolEmpleados.insertar(new Empleado(10, "Hector", "Mantenimiento"));
        arbolEmpleados.insertar(new Empleado(20, "Johan", "Administración"));
        arbolEmpleados.insertar(new Empleado(5, "Mauricio", "Seguridad"));

        int opcion;
        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1) Clientes (Residentes)");
            System.out.println("2) Departamentos");
            System.out.println("3) Pagos y Deudas");
            System.out.println("4) Mantenimiento");
            System.out.println("5) Reportes");
            System.out.println("6) Tareas del condominio");
            System.out.println("7) Empleados (árbol)");
            System.out.println("0) Salir");
            opcion = leerInt(sc, "Opcion: ");

            switch (opcion) {
                case 1: menuClientes(sc, db); break;
                case 2: menuDepartamentos(sc, db, condominio); break;
                case 3: menuPagos(sc, db, gPagos); break;
                case 4: menuMantenimiento(sc, db, gMant); break;
                case 5:
                    System.out.println("\n--- REPORTE GENERAL ---");
                    System.out.println("Condominio: " + condominio);
                    System.out.println("Total recaudado: $" + gPagos.totalRecaudado());
                    System.out.println("Deuda total (todos los deptos): $" + condominio.deudaTotal());
                    System.out.println("Servicios registrados: " + db.getServicios().size());
                    admin.generarReporte();
                    break;
                case 6:
                    menuTareasCondominio(sc, pilaUrgentes, colaRegulares, listaPendientes, colaPrioridad, db, arbolEmpleados);
                    break;
                case 7:
                    System.out.println("Empleados (inorder):");
                    arbolEmpleados.inorderImprimir();
                    System.out.print("Buscar empleado por ID (0 para omitir): ");
                    int id = Integer.parseInt(sc.nextLine());
                    if (id != 0) {
                        Empleado e = arbolEmpleados.buscarPorId(id);
                        System.out.println(e == null ? "No encontrado." : "Encontrado: " + e);
                    }
                    break;
                case 0: System.out.println("Fin del sistema."); break;
                default: System.out.println("Opcion inválida.");
            }
        } while (opcion != 0);

        sc.close();
    }

    

    private static void menuClientes(Scanner sc, BaseDatos db) {
       
        int op;
        do {
            System.out.println("\n--- CLIENTES (Residentes) ---");
            System.out.println("1) Registrar residente");
            System.out.println("2) Listar residentes");
            System.out.println("3) Eliminar residente por nombre");
            System.out.println("0) Volver");
            op = leerInt(sc, "Opcion: ");
            switch (op) {
                case 1:
                    System.out.print("Nombre: "); String nombre = sc.nextLine();
                    System.out.print("Teléfono: "); String tel = sc.nextLine();
                    System.out.print("Correo: "); String correo = sc.nextLine();
                    db.agregarResidente(new Residentes(nombre, tel, correo));
                    System.out.println("Residente registrado."); break;
                case 2:
                    List<Residentes> rs = db.getResidentes();
                    if (rs.isEmpty()) System.out.println("Sin residentes.");
                    else for (Residentes r : rs) System.out.println("- " + r);
                    break;
                case 3:
                    System.out.print("Nombre a eliminar: "); String n = sc.nextLine();
                    db.eliminarResidentePorNombre(n);
                    System.out.println("Operación realizada."); break;
            }
        } while (op != 0);
    }

    private static void menuDepartamentos(Scanner sc, BaseDatos db, Condominio condominio) {
        int op;
        do {
            System.out.println("\n--- DEPARTAMENTOS ---");
            System.out.println("1) Crear departamento");
            System.out.println("2) Listar departamentos");
            System.out.println("3) Asignar propietario a un departamento");
            System.out.println("4) Agregar residente a un departamento");
            System.out.println("5) Generar cargos mensuales (a todos los departamentos)");
            System.out.println("0) Volver");
            op = leerInt(sc, "Opción: ");
            switch (op) {
                case 1:
                    int num = leerInt(sc, "Número de departamento: ");
                    double cuota = leerDouble(sc, "Cuota mensual: $");
                    Departamento d = new Departamento(num, cuota);
                    condominio.agregarDepartamento(d);
                    db.agregarDepartamento(d);
                    System.out.println("Departamento creado."); break;
                case 2:
                    if (db.getDepartamentos().isEmpty()) System.out.println("Sin departamentos.");
                    else for (Departamento dep : db.getDepartamentos()) System.out.println("- " + dep); break;
                case 3:
                    int nd = leerInt(sc, "Número de departamento: ");
                    Departamento dep = db.buscarDepartamento(nd);
                    if (dep == null) { System.out.println("No existe ese departamento."); break; }
                    System.out.print("Nombre del propietario: "); String nombreProp = sc.nextLine();
                    Residentes prop = null;
                    for (Residentes r : db.getResidentes()) {
                        if (r.getNombre().equalsIgnoreCase(nombreProp)) { prop = r; break; }
                    }
                    if (prop == null) System.out.println("Residente no encontrado.");
                    else { dep.setPropietario(prop); System.out.println("Propietario asignado."); }
                    break;
                case 4:
                    int nd2 = leerInt(sc, "Número de departamento: ");
                    Departamento dep2 = db.buscarDepartamento(nd2);
                    if (dep2 == null) { System.out.println("No existe ese departamento."); break; }
                    System.out.print("Nombre del residente: "); String nr = sc.nextLine();
                    Residentes res = null;
                    for (Residentes r : db.getResidentes()) {
                        if (r.getNombre().equalsIgnoreCase(nr)) { res = r; break; }
                    }
                    if (res == null) System.out.println("Residente no encontrado.");
                    else {
                        dep2.agregarResidente(res);
                        if (dep2.getPropietario() == null) dep2.setPropietario(res);
                        System.out.println("Residente agregado al departamento.");
                    }
                    break;
                case 5:
                    condominio.generarCargosMensuales();
                    System.out.println("Cargos mensuales aplicados a todos los departamentos."); break;
            }
        } while (op != 0);
    }

    private static void menuPagos(Scanner sc, BaseDatos db, GestorPagos gPagos) {
        int op;
        do {
            System.out.println("\n--- PAGOS Y DEUDAS ---");
            System.out.println("1) Registrar pago a un departamento");
            System.out.println("2) Ver deuda de un departamento");
            System.out.println("3) Ver deudas de todos");
            System.out.println("0) Volver");
            op = leerInt(sc, "Opción: ");
            switch (op) {
                case 1:
                    int num = leerInt(sc, "Número de departamento: ");
                    Departamento d = db.buscarDepartamento(num);
                    if (d == null) { System.out.println("No existe ese departamento."); break; }
                    double monto = leerDouble(sc, "Monto: $");
                    System.out.print("Concepto: "); String concepto = sc.nextLine();
                    gPagos.registrarPago(d, monto, concepto); break;
                case 2:
                    int num2 = leerInt(sc, "Número de departamento: ");
                    Departamento d2 = db.buscarDepartamento(num2);
                    if (d2 == null) { System.out.println("No existe ese departamento."); break; }
                    System.out.println("Deuda actual: $" + d2.calcularDeuda()); break;
                case 3:
                    for (Departamento dep : db.getDepartamentos())
                        System.out.println("Departamento " + dep.getNumero() + " -> Deuda: $" + dep.calcularDeuda());
                    break;
            }
        } while (op != 0);
    }

    private static void menuMantenimiento(Scanner sc, BaseDatos db, GestorMantenimiento gMant) {
        int op;
        do {
            System.out.println("\n--- MANTENIMIENTO ---");
            System.out.println("1) Registrar servicio");
            System.out.println("2) Dividir gastos entre departamentos");
            System.out.println("3) Listar servicios");
            System.out.println("0) Volver");
            op = leerInt(sc, "Opción: ");
            switch (op) {
                case 1:
                    System.out.print("Tipo de servicio: "); String tipo = sc.nextLine();
                    double costo = leerDouble(sc, "Costo mensual: $");
                    System.out.print("Proveedor: "); String prov = sc.nextLine();
                    Servicio s = new Servicio(tipo, costo, prov);
                    db.registrarServicio(s);
                    gMant.registrarServicio(s);
                    break;
                case 2:
                    double porDepto = gMant.dividirGastosEntre(db.getDepartamentos());
                    if (db.getDepartamentos().isEmpty()) System.out.println("No hay departamentos.");
                    else System.out.println("Cada departamento debe aportar: $" + porDepto);
                    break;
                case 3:
                    if (db.getServicios().isEmpty()) System.out.println("Sin servicios.");
                    else for (Servicio serv : db.getServicios()) System.out.println("- " + serv);
                    break;
            }
        } while (op != 0);
    }

    // ------------------ MENÚ TAREAS  -------------------
private static void menuTareasCondominio(Scanner sc,
Stack<TareaDepartamento> pilaUrgentes,
Queue<TareaDepartamento> colaRegulares,
ArrayList<TareaDepartamento> listaPendientes,
PriorityQueue<TareaDepartamento> colaPrioridad,
BaseDatos db,
BSTEmpleado arbolEmpleados) {
int op;
do {
System.out.println("\n--- TAREAS DEL CONDOMINIO ---");
System.out.println("1) Urgentes (pila)");
System.out.println("2) Programadas (cola / prioridad)");
System.out.println("3) Pendientes (lista)");
System.out.println("4) Estadísticas (recursivo)");
System.out.println("0) Volver");
op = leerInt(sc, "Opción: ");

switch (op) {
case 1:
menuPilaUrgentes(sc, pilaUrgentes, db);
break;
case 2:
menuColaProgramadas(sc, colaRegulares, colaPrioridad, db);
break;
case 3:
menuListaPendientes(sc, listaPendientes, db, arbolEmpleados);
break;
case 4:
if (listaPendientes.isEmpty()) {
System.out.println("No hay tareas pendientes para estadísticas.");
} else {
int total = tiempoTotalRecursivo(listaPendientes, 0, listaPendientes.size()-1);
System.out.println("Tiempo total estimado (min): " + total);

List<List<TareaDepartamento>> distrib = distribuirTareasDivideYVenceras(listaPendientes, 3);

// Lista de empleados definida por ID (Johan, Mauricio, Hector)
List<Empleado> empleados = List.of(
arbolEmpleados.buscarPorId(20), // Johan
arbolEmpleados.buscarPorId(5),  // Mauricio
arbolEmpleados.buscarPorId(10)  // Hector
);

for (int i = 0; i < distrib.size(); i++) {
Empleado e = empleados.get(i);
System.out.println("Empleado " + e.nombre + " (ID " + e.id + ") recibe " + distrib.get(i).size() + " tareas.");
}
}
break;
}
} while (op != 0);
}

// ------------------ MÉTODOS AUXILIARES -------------------

    // Submenús de pila/cola/lista
    private static void menuPilaUrgentes(Scanner sc, Stack<TareaDepartamento> pilaUrgentes, BaseDatos db) {
        System.out.println("\n--- PILA: TAREAS URGENTES ---");
        System.out.println("1) Agregar tarea urgente");
        System.out.println("2) Realizar tarea urgente");
        System.out.println("3) Ver próximas tareas urgentes");
        System.out.println("0) Volver");
        int p = leerInt(sc, "Opción: ");
        switch (p) {
            case 1:
                Departamento depUrg = seleccionarDepartamento(sc, db);
                System.out.print("Descripción de la tarea urgente: ");
                String tareaUrg = sc.nextLine();
                int pri = pedirPrioridad(sc);
                int tiempo = pedirTiempoEstimado(sc);
                Date fecha = pedirFechaEntrega(sc);
                pilaUrgentes.push(new TareaDepartamento(tareaUrg, depUrg, pri, fecha, tiempo));
                System.out.println("Tarea urgente agregada.");
                break;
            case 2:
                if (!pilaUrgentes.isEmpty()) System.out.println("Tarea realizada: " + pilaUrgentes.pop());
                else System.out.println("No hay tareas urgentes.");
                break;
            case 3:
                System.out.println(pilaUrgentes);
                break;
        }
    }

    private static void menuColaProgramadas(Scanner sc, Queue<TareaDepartamento> colaRegulares,
                                            PriorityQueue<TareaDepartamento> colaPrioridad,
                                            BaseDatos db) {
        System.out.println("\n--- COLA: TAREAS PROGRAMADAS ---");
        System.out.println("1) Agregar tarea programada");
        System.out.println("2) Realizar próxima tarea programada");
        System.out.println("3) Realizar próxima tarea por prioridad");
        System.out.println("4) Ver próximas tareas programadas");
        System.out.println("0) Volver");
        int c = leerInt(sc, "Opción: ");
        switch (c) {
            case 1:
                Departamento depCola = seleccionarDepartamento(sc, db);
                System.out.print("Descripción de la tarea programada: ");
                String tareaCola = sc.nextLine();
                int pri = pedirPrioridad(sc);
                int tiempo = pedirTiempoEstimado(sc);
                Date fecha = pedirFechaEntrega(sc);
                TareaDepartamento t = new TareaDepartamento(tareaCola, depCola, pri, fecha, tiempo);
                colaRegulares.add(t);
                colaPrioridad.add(t); 
                System.out.println("Tarea programada agregada.");
                break;
            case 2:
                if (!colaRegulares.isEmpty()) System.out.println("Tarea realizada (FIFO): " + colaRegulares.poll());
                else System.out.println("No hay tareas programadas.");
                break;
            case 3:
                if (!colaPrioridad.isEmpty()) System.out.println("Tarea realizada (prioridad): " + colaPrioridad.poll());
                else System.out.println("No hay tareas en la cola de prioridad.");
                break;
            case 4:
                System.out.println("FIFO view: " + colaRegulares);
                System.out.println("Priority view: " + colaPrioridad);
                break;
        }
    }

    private static void menuListaPendientes(Scanner sc, ArrayList<TareaDepartamento> listaPendientes, BaseDatos db, BSTEmpleado arbolEmpleados) {
        System.out.println("\n--- LISTA: TAREAS PENDIENTES ---");
        System.out.println("1) Agregar tarea pendiente");
        System.out.println("2) Eliminar tarea pendiente");
        System.out.println("3) Ver tareas pendientes");
        System.out.println("0) Volver");
        int l = leerInt(sc, "Opción: ");
        switch (l) {
            case 1 -> {
                Departamento depLista = seleccionarDepartamento(sc, db);
                System.out.print("Descripción de la tarea pendiente: ");
                String tareaLista = sc.nextLine();
                int pri = pedirPrioridad(sc);
                int tiempo = pedirTiempoEstimado(sc);
                Date fecha = pedirFechaEntrega(sc);
                listaPendientes.add(new TareaDepartamento(tareaLista, depLista, pri, fecha, tiempo));
                System.out.println("Tarea pendiente agregada.");
            }
            case 2 -> {
                System.out.print("Tarea a eliminar (descripción exacta): ");
                String elim = sc.nextLine();
                listaPendientes.removeIf(t -> t.getDescripcion().equalsIgnoreCase(elim));
                System.out.println("Operación realizada.");
            }
            case 3 -> System.out.println(listaPendientes);
        }
    }

    

    private static int pedirPrioridad(Scanner sc) {
        System.out.print("Prioridad (1-baja .. 5-alta) [0 por defecto]: ");
        String s = sc.nextLine();
        try { int v = Integer.parseInt(s); return Math.max(0, Math.min(5, v)); } catch (Exception ex) { return 0; }
    }

    private static int pedirTiempoEstimado(Scanner sc) {
        System.out.print("Tiempo estimado (minutos) [0 por defecto]: ");
        String s = sc.nextLine();
        try { return Math.max(0, Integer.parseInt(s)); } catch (Exception ex) { return 0; }
    }

    private static Date pedirFechaEntrega(Scanner sc) {
        System.out.print("Fecha de entrega (yyyy-MM-dd) [vacío=ninguna]: ");
        String s = sc.nextLine().trim();
        if (s.isEmpty()) return null;
        try { return new SimpleDateFormat("yyyy-MM-dd").parse(s); } catch (Exception ex) { System.out.println("Fecha inválida. Ignorada."); return null; }
    }

    //Recursividad
    private static int tiempoTotalRecursivo(List<TareaDepartamento> tareas, int inicio, int fin) {
        if (tareas == null || tareas.isEmpty() || inicio > fin) return 0;
        if (inicio == fin) return tareas.get(inicio).getTiempoEstimadoMin();
        int mid = (inicio + fin) / 2;
        int left = tiempoTotalRecursivo(tareas, inicio, mid);
        int right = tiempoTotalRecursivo(tareas, mid + 1, fin);
        return left + right;
    }

    
    private static List<List<TareaDepartamento>> distribuirTareasDivideYVenceras(List<TareaDepartamento> tareas, int nEmpleados) {
       
        List<TareaDepartamento> lista = new ArrayList<>(tareas);
        lista.sort((a,b) -> Integer.compare(b.getTiempoEstimadoMin(), a.getTiempoEstimadoMin()));

        // nicializar n listas vacías
        List<List<TareaDepartamento>> res = new ArrayList<>();
        int[] carga = new int[nEmpleados];
        for (int i=0;i<nEmpleados;i++) res.add(new ArrayList<>());

      
        for (TareaDepartamento t : lista) {
            int idxMin = 0;
            for (int i=1;i<nEmpleados;i++) if (carga[i] < carga[idxMin]) idxMin = i;
            res.get(idxMin).add(t);
            carga[idxMin] += t.getTiempoEstimadoMin();
        }
        return res;
    }

    // ------------------ Selección de departamento  -------------------
    private static Departamento seleccionarDepartamento(Scanner sc, BaseDatos db) {
        System.out.println("Selecciona un departamento:");
        for (Departamento d : db.getDepartamentos()) {
            String residentesInfo;
            if (!d.getResidentes().isEmpty()) {
                List<String> nombres = new ArrayList<>();
                for (Residentes r : d.getResidentes()) { nombres.add(r.getNombre()); }
                residentesInfo = String.join(", ", nombres);
            } else residentesInfo = "Libre";
            System.out.println(d.getNumero() + " - " + residentesInfo);
        }
        int num = leerInt(sc, "Número: ");
        if (num == 0) return null;
        Departamento d = db.buscarDepartamento(num);
        if (d == null) System.out.println("Departamento no encontrado. Se asignará como general.");
        return d;
    }
}
