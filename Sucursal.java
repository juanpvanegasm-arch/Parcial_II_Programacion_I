import java.util.ArrayList;

public class Sucursal {
    private String codigo;
    private String nombre;
    private String direccion;
    private String ciudad;
    private ArrayList<Vendedor> listaVendedores;
    private ArrayList<Inventario> listaInventarios;
    private ArrayList<Venta> listaVentas;

    public Sucursal(String codigo, String nombre, String direccion, String ciudad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.listaVendedores = new ArrayList<>();
        this.listaInventarios = new ArrayList<>();
        this.listaVentas = new ArrayList<>();
    }

    public String getCodigo() {return codigo;}
    public ArrayList<Vendedor> getListaVendedores() {return listaVendedores;}

    public void agregarVendedor(Vendedor vendedor) {
        listaVendedores.add(vendedor);
    }

    public void agregarVenta(Venta venta) {
        listaVentas.add(venta);
    }

    public Vendedor buscarVendedor(String documento) {
        for (Vendedor vendedor : listaVendedores) {
            if (vendedor.getDocumento().equals(documento)) {
                return vendedor;
            }
        }
        return null;
    }

    public Venta buscarVenta(String codigoVenta) {
        for (Venta venta : listaVentas) {
            if (venta.getCodigo().equalsIgnoreCase(codigoVenta)) {
                return venta;
            }
        }
        return null;
    }

    // Busca el inventario de esa combinación de prenda, talla y color
    public Inventario buscarInventario(Prenda prenda, Talla talla, String color) {
        for (Inventario inventario : listaInventarios) {
            if (inventario.coincidenCaracteristicas(prenda, talla, color)) {
                return inventario;
            }
        }
        return null;
    }

    // No permite dos inventarios con la misma combinación de prenda, talla y color
    public boolean registrarInventario(Inventario inventario) {
        if (buscarInventario(inventario.getPrenda(), inventario.getTalla(), inventario.getColor()) != null) {
            return false;
        }
        listaInventarios.add(inventario);
        inventario.getPrenda().agregarInventario(inventario);
        return true;
    }

    public ArrayList<Inventario> consultarInventarioDisponible() {
        ArrayList<Inventario> disponibles = new ArrayList<>();
        for (Inventario inventario : listaInventarios) {
            if (inventario.getCantidadDisponible() > 0) {
                disponibles.add(inventario);
            }
        }
        return disponibles;
    }

    public ArrayList<Inventario> consultarInventarioAgotado() {
        ArrayList<Inventario> agotados = new ArrayList<>();
        for (Inventario inventario : listaInventarios) {
            if (inventario.getCantidadDisponible() == 0) {
                agotados.add(inventario);
            }
        }
        return agotados;
    }

    // Suma el valor total de todas las ventas confirmadas de la sucursal
    public double calcularIngresos() {
        double ingresos = 0;
        for (Venta venta : listaVentas) {
            ingresos = ingresos + venta.calcularValorTotal();
        }
        return ingresos;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}
