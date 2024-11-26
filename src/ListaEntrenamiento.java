import java.util.ArrayList;

public class ListaEntrenamiento {
    private ArrayList<Entrenamiento> entrenamientos;

    public ListaEntrenamiento(ArrayList<Entrenamiento> entrenamientos) {
        this.entrenamientos = entrenamientos;
    }

    @Override
    public String toString() {
        return "ListaEntrenamiento{" +
                "entrenamientos=" + entrenamientos +
                '}';
    }
}
