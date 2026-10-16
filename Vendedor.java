import java.time.LocalDate;
import java.util.ArrayList;

public class Vendedor extends Persona {
    private String codigoEmpleado;
    private LocalDate fechaContratacion;
    private double salarioBasico;
    private Sucursal sucursal;
    private ArrayList<Venta> listaVenta;

    public Vendedor(String documento, String nombre, String correo, String telefono,
                    String codigoEmpleado, LocalDate fechaContratacion, double salarioBasico, Sucursal sucursal) {
        super(nombre, documento, correo, telefono);
        this.codigoEmpleado = codigoEmpleado;
        this.fechaContratacion = fechaContratacion;
        this.salarioBasico = salarioBasico;
        this.sucursal = sucursal;
        this.listaVenta = new ArrayList<>();
    }

    public Sucursal getSucursal() {return sucursal;}
    public ArrayList<Venta> getListaVenta() {return listaVenta;}

    public void agregarVenta(Venta venta) {
        listaVenta.add(venta);
    }
}
