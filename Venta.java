import java.time.LocalDate;
import java.util.ArrayList;

public class Venta {
    private String codigo;
    private LocalDate fechaRealizacion;
    private Sucursal sucursal;
    private Vendedor vendedor;
    private Cliente cliente;
    private ArrayList<DetalleVenta> listaDetalleVenta;

    // La fecha de realización es la fecha de hoy
    public Venta(String codigo, Sucursal sucursal, Vendedor vendedor, Cliente cliente) {
        this.codigo = codigo;
        this.fechaRealizacion = LocalDate.now();
        this.sucursal = sucursal;
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.listaDetalleVenta = new ArrayList<>();
    }

    public String getCodigo() {return codigo;}
    public ArrayList<DetalleVenta> getListaDetalleVenta() {return listaDetalleVenta;}

    // Suma las cantidades de todos los detalles que piden el mismo inventario
    // (así se controla que pidan varias veces la misma prenda, talla y color)
    private int calcularCantidadSolicitada(Inventario inventario) {
        int cantidadSolicitada = 0;
        for (DetalleVenta detalle : listaDetalleVenta) {
            if (detalle.getInventario() == inventario) {
                cantidadSolicitada = cantidadSolicitada + detalle.getCantidad();
            }
        }
        return cantidadSolicitada;
    }

    // Verifica que la venta se pueda realizar: que tenga detalles, que el vendedor y todos los
    // inventarios sean de la sucursal y que alcancen las existencias.
    // Solo si todo está bien descuenta las unidades y registra cada detalle en su inventario.
    public boolean verificarVenta() {
        if (listaDetalleVenta.size() == 0 || vendedor.getSucursal() != sucursal) {
            return false;
        }

        for (DetalleVenta detalle : listaDetalleVenta) {
            Inventario inventario = detalle.getInventario();
            if (inventario.getSucursal() != sucursal
                    || calcularCantidadSolicitada(inventario) > inventario.getCantidadDisponible()) {
                return false;
            }
        }

        for (DetalleVenta detalle : listaDetalleVenta) {
            detalle.getInventario().descontarCantidad(detalle.getCantidad());
            detalle.getInventario().agregarDetalleVenta(detalle);
        }
        return true;
    }

    // Suma los subtotales de todos los detalles
    public double calcularValorTotal() {
        double valorTotal = 0;
        for (DetalleVenta detalle : listaDetalleVenta) {
            valorTotal = valorTotal + detalle.getSubTotal();
        }
        return valorTotal;
    }

    @Override
    public String toString() {
        return "Venta " + codigo + " | " + fechaRealizacion
                + " | Sucursal: " + sucursal
                + " | Vendedor: " + vendedor
                + " | Cliente: " + cliente
                + " | Total: $" + Math.round(calcularValorTotal());
    }
}
