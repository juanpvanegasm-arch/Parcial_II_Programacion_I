import java.util.ArrayList;
public class Inventario {
    private String color;
    private int cantidadDisponible;
    private Sucursal sucursal;
    private Prenda prenda;
    private Talla talla;
    private ArrayList<DetalleVenta> listaDetalleVenta;
    public Inventario(String color, int cantidadDisponible, Sucursal sucursal, Prenda prenda, Talla talla) {
        this.color = color;
        this.cantidadDisponible = cantidadDisponible;
        this.sucursal = sucursal;
        this.prenda = prenda;
        this.talla = talla;
        this.listaDetalleVenta = new ArrayList<>();
    }

    public int getCantidadDisponible() {return cantidadDisponible;}
    public Sucursal getSucursal() {return sucursal;}
    public Prenda getPrenda() {return prenda;}
    public Talla getTalla() {return talla;}
    public String getColor() {return color;}
    @Override
    public String toString() {
        return prenda +
                ", "+ talla +
                "," + color;
    }
    // Dice si este inventario es de esa combinación de prenda, talla y color
    public boolean coincidenCaracteristicas(Prenda prenda, Talla talla, String color) {
        return this.prenda == prenda && this.talla == talla && this.color.equalsIgnoreCase(color);
    }

    public void descontarCantidad(int cantidad) {
        cantidadDisponible = cantidadDisponible - cantidad;
    }

    public void agregarDetalleVenta(DetalleVenta detalle) {
        listaDetalleVenta.add(detalle);
    }

    // Suma las cantidades de todos los detalles de venta de este inventario
    public int calcularUnidadesVendidasPorCompra() {
        int unidadesVendidas = 0;
        for (DetalleVenta detalle : listaDetalleVenta) {
            unidadesVendidas = unidadesVendidas + detalle.getCantidad();
        }
        return unidadesVendidas;
    }
}
