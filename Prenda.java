import java.util.ArrayList;

public class Prenda {
    private String codigo;
    private String nombre;
    private String marca;
    private double precioBase;
    private CategoriaPrenda categoria;
    private ArrayList<Inventario> listaInventarios;

    public Prenda(String codigo, String nombre, String marca, double precioBase, CategoriaPrenda categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.marca = marca;
        this.precioBase = precioBase;
        this.categoria = categoria;
        this.listaInventarios = new ArrayList<>();
    }

    public String getCodigo() {return codigo;}
    public double getPrecioBase() {return precioBase;}
    public CategoriaPrenda getCategoria() {return categoria;}

    // Dice si esta prenda tiene el mismo nombre, marca y categoría (sin importar el código)
    public boolean coincidenCaracteristicas(String nombre, String marca, CategoriaPrenda categoria) {
        return this.nombre.equalsIgnoreCase(nombre)
                && this.marca.equalsIgnoreCase(marca)
                && this.categoria == categoria;
    }

    public void agregarInventario(Inventario inventario) {
        listaInventarios.add(inventario);
    }

    // Precio base + el recargo que le corresponde según su categoría
    public double calcularPrecioConRecargo() {
        return precioBase + (precioBase * categoria.getPorcentajeRecargo());
    }

    // Suma las unidades vendidas en todos sus inventarios (todas las sucursales, tallas y colores)
    public int calcularUnidadesVendidas() {
        int unidadesVendidas = 0;
        for (Inventario inventario : listaInventarios) {
            unidadesVendidas = unidadesVendidas + inventario.calcularUnidadesVendidasPorCompra();
        }
        return unidadesVendidas;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + marca + ")";
    }
}
