public enum CategoriaCliente {
    BRONCE(0.05),
    PLATA(0.1),
    ORO(0.15);

    private double porcentajeDescuento;

    CategoriaCliente(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public double getPorcentajeDescuento() {return porcentajeDescuento;}
}
