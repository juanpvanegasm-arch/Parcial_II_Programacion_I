import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Aplicacion {

    static Scanner sc = new Scanner(System.in);
    static Empresa empresa = new Empresa("ModaStyle", 900123456, "Calle 15 # 14-20, Armenia, Quindío");

    public static void main(String[] args) {

        cargarDatosIniciales();

        int opcion;
        do {
            System.out.println();
            System.out.println("===== " + empresa + " =====");
            System.out.println("1. Registrar una sucursal");
            System.out.println("2. Registrar una prenda en el catálogo");
            System.out.println("3. Registrar un cliente");
            System.out.println("4. Registrar un vendedor");
            System.out.println("5. Registrar existencias (inventario) en una sucursal");
            System.out.println("6. Consultar las prendas disponibles de una sucursal");
            System.out.println("7. Registrar una venta");
            System.out.println("8. Calcular el precio unitario final de una prenda para un cliente");
            System.out.println("9. Consultar el valor total de una venta");
            System.out.println("10. Consultar las prendas agotadas");
            System.out.println("11. Consultar las ventas de un vendedor");
            System.out.println("12. Consultar las compras de un cliente");
            System.out.println("13. Calcular los ingresos de una sucursal");
            System.out.println("14. Identificar la prenda con más unidades vendidas");
            System.out.println("15. Salir");
            opcion = leerOpcion(15);

            // En cada caso: primero se le habla al usuario y se leen los datos,
            // luego se invoca la función que hace el trabajo y se muestra el resultado.
            switch (opcion) {
                case 1: {
                    System.out.println("--- Registrar una sucursal ---");
                    String codigo = leerTexto("Código de la sucursal: ");
                    String nombre = leerTexto("Nombre: ");
                    String direccion = leerTexto("Dirección: ");
                    String ciudad = leerTexto("Ciudad: ");

                    if (empresa.registrarSucursal(codigo, nombre, direccion, ciudad)) {
                        System.out.println("Sucursal registrada.");
                    } else {
                        System.out.println("Ya existe una sucursal con ese código.");
                    }
                    break;
                }
                case 2: {
                    System.out.println("--- Registrar una prenda en el catálogo ---");
                    String codigo = leerTexto("Código de la prenda: ");
                    String nombre = leerTexto("Nombre: ");
                    String marca = leerTexto("Marca: ");
                    double precioBase = leerNumero("Precio base unitario: ");
                    CategoriaPrenda categoria = leerCategoriaPrenda();

                    if (empresa.registrarPrenda(codigo, nombre, marca, precioBase, categoria)) {
                        System.out.println("Prenda registrada.");
                    } else {
                        System.out.println("No se registró: ya existe una prenda con ese código o con el mismo nombre, marca y categoría.");
                    }
                    break;
                }
                case 3: {
                    System.out.println("--- Registrar un cliente ---");
                    String documento = leerTexto("Documento de identidad: ");
                    String nombre = leerTexto("Nombre completo: ");
                    String correo = leerTexto("Correo electrónico: ");
                    String telefono = leerTexto("Teléfono: ");
                    String codigoAfiliacion = leerTexto("Código de afiliación: ");
                    CategoriaCliente categoria = leerCategoriaCliente();
                    Cliente cliente = new Cliente(documento, nombre, correo, telefono, codigoAfiliacion, categoria);

                    if (empresa.registrarCliente(cliente)) {
                        System.out.println("Cliente registrado.");
                    } else {
                        System.out.println("Ya existe una persona registrada con ese documento.");
                    }
                    break;
                }
                case 4: {
                    System.out.println("--- Registrar un vendedor ---");
                    Sucursal sucursal = leerSucursal();

                    if (sucursal != null) {
                        String documento = leerTexto("Documento de identidad: ");
                        String nombre = leerTexto("Nombre completo: ");
                        String correo = leerTexto("Correo electrónico: ");
                        String telefono = leerTexto("Teléfono: ");
                        String codigoEmpleado = leerTexto("Código de empleado: ");
                        System.out.println("Fecha de contratación:");
                        LocalDate fechaContratacion = leerFecha();
                        double salarioBasico = leerNumero("Salario básico: ");
                        Vendedor vendedor = new Vendedor(documento, nombre, correo, telefono,
                                codigoEmpleado, fechaContratacion, salarioBasico, sucursal);

                        if (empresa.registrarVendedor(vendedor, sucursal)) {
                            System.out.println("Vendedor registrado.");
                        } else {
                            System.out.println("Ya existe una persona registrada con ese documento.");
                        }
                    }
                    break;
                }
                case 5: {
                    System.out.println("--- Registrar existencias en una sucursal ---");
                    Sucursal sucursal = leerSucursal();
                    Prenda prenda = null;
                    if (sucursal != null) {
                        prenda = leerPrenda();
                    }

                    if (prenda != null) {
                        Talla talla = leerTalla(prenda.getCategoria());
                        String color = leerTexto("Color: ");
                        int cantidad = leerCantidad("Cantidad disponible: ");
                        Inventario inventario = new Inventario(color, cantidad, sucursal, prenda, talla);

                        if (sucursal.registrarInventario(inventario)) {
                            System.out.println("Existencias registradas.");
                        } else {
                            System.out.println("Ya existe un inventario de esa prenda, talla y color en la sucursal.");
                        }
                    }
                    break;
                }
                case 6: {
                    System.out.println("--- Prendas disponibles de una sucursal ---");
                    Sucursal sucursal = leerSucursal();

                    if (sucursal != null) {
                        ArrayList<Inventario> disponibles = sucursal.consultarInventarioDisponible();
                        if (disponibles.size() == 0) {
                            System.out.println("La sucursal no tiene prendas disponibles.");
                        } else {
                            for (Inventario inventario : disponibles) {
                                System.out.println(inventario + " | Cantidad: " + inventario.getCantidadDisponible());
                            }
                        }
                    }
                    break;
                }
                case 7: {
                    System.out.println("--- Registrar una venta ---");
                    Sucursal sucursal = leerSucursal();
                    Vendedor vendedor = null;
                    Cliente cliente = null;
                    if (sucursal != null) {
                        vendedor = leerVendedor(sucursal);
                    }
                    if (vendedor != null) {
                        cliente = leerCliente();
                    }

                    if (cliente != null) {
                        if (sucursal.consultarInventarioDisponible().size() == 0) {
                            System.out.println("La sucursal no tiene prendas disponibles para vender.");
                        } else {
                            String codigoVenta = leerTexto("Código de la venta: ");
                            Venta venta = empresa.crearVenta(codigoVenta, cliente, sucursal, vendedor);

                            if (venta == null) {
                                System.out.println("Ya existe una venta con ese código.");
                            } else {
                                int agregarOtro = 1;
                                while (agregarOtro == 1) {
                                    Inventario inventario = leerInventarioDisponible(sucursal);
                                    int cantidad = leerCantidad("Cantidad a comprar: ");
                                    venta.getListaDetalleVenta().add(new DetalleVenta(cantidad, inventario, cliente));

                                    System.out.println("1. Agregar otra prenda");
                                    System.out.println("2. Terminar y confirmar la venta");
                                    agregarOtro = leerOpcion(2);
                                }

                                if (venta.verificarVenta()) {
                                    sucursal.agregarVenta(venta);
                                    vendedor.agregarVenta(venta);
                                    cliente.agregarCompra(venta);
                                    System.out.println("Venta confirmada. Valor total: $" + Math.round(venta.calcularValorTotal()));
                                } else {
                                    System.out.println("La venta no se pudo confirmar: la cantidad solicitada supera las existencias.");
                                }
                            }
                        }
                    }
                    break;
                }
                case 8: {
                    System.out.println("--- Precio unitario final de una prenda ---");
                    Prenda prenda = leerPrenda();
                    Cliente cliente = null;
                    if (prenda != null) {
                        cliente = leerCliente();
                    }

                    if (cliente != null) {
                        System.out.println("Precio base: $" + Math.round(prenda.getPrecioBase()));
                        System.out.println("Precio con recargo de " + prenda.getCategoria() + ": $" + Math.round(prenda.calcularPrecioConRecargo()));
                        System.out.println("Precio unitario final para " + cliente + ": $" + Math.round(cliente.calcularPrecioUnitarioFinal(prenda)));
                    }
                    break;
                }
                case 9: {
                    System.out.println("--- Valor total de una venta ---");
                    String codigoVenta = leerTexto("Código de la venta: ");
                    Venta venta = empresa.buscarVenta(codigoVenta);

                    if (venta == null) {
                        System.out.println("No existe una venta confirmada con ese código.");
                    } else {
                        System.out.println(venta);
                        for (DetalleVenta detalle : venta.getListaDetalleVenta()) {
                            System.out.println("   - " + detalle);
                        }
                    }
                    break;
                }
                case 10: {
                    System.out.println("--- Prendas agotadas ---");
                    ArrayList<Inventario> agotados = empresa.consultarInventarioAgotado();

                    if (agotados.size() == 0) {
                        System.out.println("No hay prendas agotadas.");
                    } else {
                        for (Inventario inventario : agotados) {
                            System.out.println(inventario.getSucursal() + " | " + inventario);
                        }
                    }
                    break;
                }
                case 11: {
                    System.out.println("--- Ventas de un vendedor ---");
                    Sucursal sucursal = leerSucursal();
                    Vendedor vendedor = null;
                    if (sucursal != null) {
                        vendedor = leerVendedor(sucursal);
                    }

                    if (vendedor != null) {
                        if (vendedor.getListaVenta().size() == 0) {
                            System.out.println("El vendedor no ha registrado ventas.");
                        } else {
                            for (Venta venta : vendedor.getListaVenta()) {
                                System.out.println(venta);
                            }
                        }
                    }
                    break;
                }
                case 12: {
                    System.out.println("--- Compras de un cliente ---");
                    Cliente cliente = leerCliente();

                    if (cliente != null) {
                        if (cliente.getListaVentas().size() == 0) {
                            System.out.println("El cliente no ha realizado compras.");
                        } else {
                            for (Venta compra : cliente.getListaVentas()) {
                                System.out.println(compra);
                            }
                        }
                    }
                    break;
                }
                case 13: {
                    System.out.println("--- Ingresos de una sucursal ---");
                    Sucursal sucursal = leerSucursal();

                    if (sucursal != null) {
                        System.out.println("Ingresos de " + sucursal + ": $" + Math.round(sucursal.calcularIngresos()));
                    }
                    break;
                }
                case 14: {
                    System.out.println("--- Prenda con más unidades vendidas ---");
                    Prenda masVendida = empresa.buscarPrendaMasVendida();

                    if (masVendida == null) {
                        System.out.println("Todavía no se han vendido prendas.");
                    } else {
                        System.out.println("Referencia más vendida: " + masVendida
                                + " (" + masVendida.calcularUnidadesVendidas() + " unidades)");
                    }
                    break;
                }
                case 15: {
                    System.out.println("Hasta pronto.");
                    break;
                }
                default: {
                    System.out.println("Opción no válida.");
                    break;
                }
            }
        } while (opcion != 15);
    }

    // ---------- Lectura de datos ----------

    // Lee lo que escribe el usuario como texto y lo compara con cada número entre el mínimo y el máximo.
    // Repite hasta que escriba un número válido (así no se cae si escribe letras).
    static int leerEntero(String mensaje, int minimo, int maximo) {
        int valor = minimo - 1;
        while (valor < minimo) {
            System.out.print(mensaje);
            String texto = sc.nextLine();
            for (int i = minimo; i <= maximo; i++) {
                if (texto.equals("" + i)) {
                    valor = i;
                }
            }
            if (valor < minimo) {
                System.out.println("Valor no válido. Debe estar entre " + minimo + " y " + maximo + ".");
            }
        }
        return valor;
    }

    static int leerOpcion(int maximo) {
        return leerEntero("Seleccione una opción (1-" + maximo + "): ", 1, maximo);
    }

    static int leerCantidad(String mensaje) {
        return leerEntero(mensaje, 1, 10000);
    }

    static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    // Lee un número decimal mayor que cero
    static double leerNumero(String mensaje) {
        double valor = 0;
        while (valor <= 0) {
            System.out.print(mensaje);
            valor = sc.nextDouble();
            sc.nextLine(); // limpia el salto de línea que queda en el Scanner
            if (valor <= 0) {
                System.out.println("El valor debe ser mayor que cero.");
            }
        }
        return valor;
    }

    // Lee año, mes y día. Para saber cuántos días tiene el mes se crea el día 1 de ese mes
    // y se le pregunta a LocalDate, así nunca se forma una fecha que no existe (como el 30 de febrero).
    static LocalDate leerFecha() {
        int anio = leerEntero("Año: ", 1950, LocalDate.now().getYear());
        int mes = leerEntero("Mes (1-12): ", 1, 12);
        int diasDelMes = LocalDate.of(anio, mes, 1).lengthOfMonth();
        int dia = leerEntero("Día (1-" + diasDelMes + "): ", 1, diasDelMes);
        return LocalDate.of(anio, mes, dia);
    }

    static Sucursal leerSucursal() {
        ArrayList<Sucursal> sucursales = empresa.getListaSucursales();
        if (sucursales.size() == 0) {
            System.out.println("No hay sucursales registradas.");
            return null;
        }
        System.out.println("Sucursales:");
        for (int i = 0; i < sucursales.size(); i++) {
            System.out.println((i + 1) + ". " + sucursales.get(i));
        }
        return sucursales.get(leerOpcion(sucursales.size()) - 1);
    }

    static Prenda leerPrenda() {
        ArrayList<Prenda> prendas = empresa.getListaPrendas();
        if (prendas.size() == 0) {
            System.out.println("No hay prendas registradas en el catálogo.");
            return null;
        }
        System.out.println("Prendas:");
        for (int i = 0; i < prendas.size(); i++) {
            System.out.println((i + 1) + ". " + prendas.get(i));
        }
        return prendas.get(leerOpcion(prendas.size()) - 1);
    }

    static Cliente leerCliente() {
        ArrayList<Cliente> clientes = empresa.getListaClientes();
        if (clientes.size() == 0) {
            System.out.println("No hay clientes registrados.");
            return null;
        }
        System.out.println("Clientes:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + ". " + clientes.get(i));
        }
        return clientes.get(leerOpcion(clientes.size()) - 1);
    }

    static Vendedor leerVendedor(Sucursal sucursal) {
        ArrayList<Vendedor> vendedores = sucursal.getListaVendedores();
        if (vendedores.size() == 0) {
            System.out.println("La sucursal no tiene vendedores registrados.");
            return null;
        }
        System.out.println("Vendedores:");
        for (int i = 0; i < vendedores.size(); i++) {
            System.out.println((i + 1) + ". " + vendedores.get(i));
        }
        return vendedores.get(leerOpcion(vendedores.size()) - 1);
    }

    // Muestra solo los inventarios de la sucursal que todavía tienen unidades
    static Inventario leerInventarioDisponible(Sucursal sucursal) {
        ArrayList<Inventario> disponibles = sucursal.consultarInventarioDisponible();
        System.out.println("Prendas disponibles:");
        for (int i = 0; i < disponibles.size(); i++) {
            System.out.println((i + 1) + ". " + disponibles.get(i) + " | Cantidad: " + disponibles.get(i).getCantidadDisponible());
        }
        return disponibles.get(leerOpcion(disponibles.size()) - 1);
    }

    static CategoriaPrenda leerCategoriaPrenda() {
        CategoriaPrenda[] categorias = CategoriaPrenda.values();
        System.out.println("Categorías de prenda:");
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + ". " + categorias[i]);
        }
        return categorias[leerOpcion(categorias.length) - 1];
    }

    static CategoriaCliente leerCategoriaCliente() {
        CategoriaCliente[] categorias = CategoriaCliente.values();
        System.out.println("Categorías de cliente:");
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + ". " + categorias[i]);
        }
        return categorias[leerOpcion(categorias.length) - 1];
    }

    // Las tallas de prenda son las 5 primeras (XS a XL) y las de calzado son las demás (T35 a T44)
    static Talla leerTalla(CategoriaPrenda categoria) {
        Talla[] tallas = Talla.values();
        int primera = 0;
        int ultima = 4;
        if (categoria == CategoriaPrenda.CALZADO) {
            primera = 5;
            ultima = tallas.length - 1;
        }
        System.out.println("Tallas:");
        for (int i = primera; i <= ultima; i++) {
            System.out.println((i - primera + 1) + ". " + tallas[i]);
        }
        return tallas[primera + leerOpcion(ultima - primera + 1) - 1];
    }

    // ---------- Datos de prueba para la demostración ----------

    static void cargarDatosIniciales() {
        empresa.registrarSucursal("S01", "ModaStyle Armenia", "Carrera 14 # 20-10", "Armenia");
        empresa.registrarSucursal("S02", "ModaStyle Calarcá", "Calle 40 # 25-15", "Calarcá");
        Sucursal armenia = empresa.getListaSucursales().get(0);
        Sucursal calarca = empresa.getListaSucursales().get(1);

        empresa.registrarPrenda("CM001", "Camiseta básica", "Nike", 50000, CategoriaPrenda.CAMISETA);
        empresa.registrarPrenda("VS001", "Vestido floral", "Zara", 120000, CategoriaPrenda.VESTIDO);
        empresa.registrarPrenda("CZ001", "Tenis deportivos", "Adidas", 200000, CategoriaPrenda.CALZADO);
        Prenda camiseta = empresa.buscarPrenda("CM001");
        Prenda vestido = empresa.buscarPrenda("VS001");
        Prenda tenis = empresa.buscarPrenda("CZ001");

        empresa.registrarVendedor(new Vendedor("1001", "Carlos Gómez", "carlos@modastyle.com", "3001112233",
                "E001", LocalDate.of(2024, 1, 10), 1800000, armenia), armenia);
        empresa.registrarVendedor(new Vendedor("1002", "Ana Ruiz", "ana@modastyle.com", "3002223344",
                "E002", LocalDate.of(2025, 3, 15), 1900000, calarca), calarca);

        empresa.registrarCliente(new Cliente("2001", "Laura Pérez", "laura@correo.com", "3104445566",
                "AF001", CategoriaCliente.ORO));
        empresa.registrarCliente(new Cliente("2002", "Pedro Díaz", "pedro@correo.com", "3115556677",
                "AF002", CategoriaCliente.BRONCE));

        armenia.registrarInventario(new Inventario("Negro", 10, armenia, camiseta, Talla.M));
        armenia.registrarInventario(new Inventario("Rojo", 5, armenia, vestido, Talla.S));
        armenia.registrarInventario(new Inventario("Blanco", 0, armenia, tenis, Talla.T40));
        calarca.registrarInventario(new Inventario("Blanco", 6, calarca, camiseta, Talla.L));
    }

}
