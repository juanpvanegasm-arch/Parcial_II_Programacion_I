public enum CategoriaPrenda {
    CAMISETA(0),
    PANTALON(0),
    VESTIDO(0.1),
    CHAQUETA(0.15),
    CALZADO(0.05);

    private double porcentajeRecargo;

    CategoriaPrenda(double porcentajeRecargo) {
        this.porcentajeRecargo = porcentajeRecargo;
    }

    public double getPorcentajeRecargo() {return porcentajeRecargo;}
}
