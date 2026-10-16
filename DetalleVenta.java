public class DetalleVenta {
    private int cantidad;
    private double precioUnitarioFinal;
    private double subtotal;
    private Inventario inventario;

    // Al crear el detalle se calcula el precio final: recargo de la prenda y luego descuento del cliente
    public DetalleVenta(int cantidad, Inventario inventario, Cliente cliente) {
        this.cantidad = cantidad;
        this.inventario = inventario;
        this.precioUnitarioFinal = cliente.calcularPrecioUnitarioFinal(inventario.getPrenda());
        this.subtotal = precioUnitarioFinal * cantidad;
    }

    public Inventario getInventario() {return inventario;}
    public int getCantidad() {return cantidad;}
    public double getSubTotal() {return subtotal;}

    //Se utilizó el Math.round para poder redondearlo a un número mas bien cerrado y que no saque muchos decimales
    @Override
    public String toString() {
        return inventario + " Cantidad: " + cantidad+
                ", Precio unitario final: $" + Math.round(precioUnitarioFinal)+
                ", Subtotal: $" + Math.round(subtotal);
    }
}
