public abstract class Persona {
    private String nombre;
    private String documento;
    private String correo;
    private String telefono;

    public Persona(String nombre, String documento, String correo, String telefono) {
        this.nombre = nombre;
        this.documento = documento;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getDocumento() {return documento;}

    @Override
    public String toString() {
        return documento + " - " + nombre;
    }
}
