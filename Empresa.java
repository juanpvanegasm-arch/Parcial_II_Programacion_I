import java.util.ArrayList;

public class Empresa {
    private String nombreComercial;
    private int NIT;
    private String direccion;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Prenda> listaPrendas;
    private ArrayList<Cliente> listaClientes;

    public Empresa(String nombreComercial, int NIT, String direccion) {
        this.nombreComercial = nombreComercial;
        this.NIT = NIT;
        this.direccion = direccion;
        this.listaSucursales = new ArrayList<>();
        this.listaPrendas = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
    }

    public String getNombreComercial() {return nombreComercial;}
    public void setNombreComercial(String nombreComercial) {this.nombreComercial = nombreComercial;}
    public int getNIT() {return NIT;}
    public void setNIT(int NIT) {this.NIT = NIT;}
    public String getDireccion() {return direccion;}
    public void setDireccion(String direccion) {this.direccion = direccion;}
    public ArrayList<Sucursal> getListaSucursales() {return listaSucursales;}
    public ArrayList<Prenda> getListaPrendas() {return listaPrendas;}
    public ArrayList<Cliente> getListaClientes() {return listaClientes;}

    @Override
    public String toString() {
        return nombreComercial +
                "NIT: " + NIT +
                "Dirección: "+ direccion;
    }
    // No permite dos sucursales con el mismo código
    public boolean registrarSucursal(String codigo, String nombre, String direccion, String ciudad) {
        for (Sucursal sucursal : listaSucursales) {
            if (sucursal.getCodigo().equalsIgnoreCase(codigo)) {
                return false;
            }
        }
        listaSucursales.add(new Sucursal(codigo, nombre, direccion, ciudad));
        return true;
    }

    public Prenda buscarPrenda(String codigo) {
        for (Prenda prenda : listaPrendas) {
            if (prenda.getCodigo().equalsIgnoreCase(codigo)) {
                return prenda;
            }
        }
        return null;
    }

    // No permite códigos repetidos ni dos prendas con el mismo nombre, marca y categoría
    public boolean registrarPrenda(String codigo, String nombre, String marca, double precioBase, CategoriaPrenda categoria) {
        if (buscarPrenda(codigo) != null) {
            return false;
        }
        for (Prenda prenda : listaPrendas) {
            if (prenda.coincidenCaracteristicas(nombre, marca, categoria)) {
                return false;
            }
        }
        listaPrendas.add(new Prenda(codigo, nombre, marca, precioBase, categoria));
        return true;
    }

    // Retorna true si ya existe una persona (cliente o vendedor de cualquier sucursal) con ese documento
    public boolean verificarCliente(String documento) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getDocumento().equals(documento)) {
                return true;
            }
        }
        for (Sucursal sucursal : listaSucursales) {
            if (sucursal.buscarVendedor(documento) != null) {
                return true;
            }
        }
        return false;
    }

    public boolean registrarCliente(Cliente cliente) {
        if (verificarCliente(cliente.getDocumento())) {
            return false;
        }
        listaClientes.add(cliente);
        return true;
    }

    public boolean registrarVendedor(Vendedor vendedor, Sucursal sucursal) {
        if (verificarCliente(vendedor.getDocumento())) {
            return false;
        }
        sucursal.agregarVendedor(vendedor);
        return true;
    }

    // Busca una venta en todas las sucursales
    public Venta buscarVenta(String codigoVenta) {
        for (Sucursal sucursal : listaSucursales) {
            Venta venta = sucursal.buscarVenta(codigoVenta);
            if (venta != null) {
                return venta;
            }
        }
        return null;
    }

    // Crea la venta (sin detalles) si el código no existe; si ya existe retorna null
    public Venta crearVenta(String codigo, Cliente cliente, Sucursal sucursal, Vendedor vendedor) {
        if (buscarVenta(codigo) != null) {
            return null;
        }
        return new Venta(codigo, sucursal, vendedor, cliente);
    }

    // Junta los inventarios agotados de todas las sucursales
    public ArrayList<Inventario> consultarInventarioAgotado() {
        ArrayList<Inventario> agotados = new ArrayList<>();
        for (Sucursal sucursal : listaSucursales) {
            agotados.addAll(sucursal.consultarInventarioAgotado());
        }
        return agotados;
    }

    // Retorna la prenda con más unidades vendidas (o null si todavía no se ha vendido nada)
    public Prenda buscarPrendaMasVendida() {
        Prenda masVendida = null;
        int mayorCantidad = 0;
        for (Prenda prenda : listaPrendas) {
            int unidadesVendidas = prenda.calcularUnidadesVendidas();
            if (unidadesVendidas > mayorCantidad) {
                mayorCantidad = unidadesVendidas;
                masVendida = prenda;
            }
        }
        return masVendida;
    }

}
