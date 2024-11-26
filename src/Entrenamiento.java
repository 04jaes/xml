public class Entrenamiento {
    private int id;
    private int duracion;
    private String nombre;
    private String nivel;

    public Entrenamiento(int id, String nombre, String nivel,int duracion) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Entrenamiento{" +
                "id=" + id +
                ", duracion=" + duracion +
                ", nombre='" + nombre + '\'' +
                ", nivel='" + nivel + '\'' +
                '}';
    }
}
