package Logica;

import DAO.Videojuego;
import ui.Lector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListaVideojuegos {
    private ArrayList<Videojuego> videojuegos;

    public ListaVideojuegos(ArrayList<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }

    public ArrayList<Videojuego> getVideojuegos() {
        return videojuegos;
    }

    public void setVideojuegos(ArrayList<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListaVideojuegos that)) return false;
        return Objects.equals(getVideojuegos(), that.getVideojuegos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVideojuegos());
    }

    @Override
    public String toString() {
        return "ListaVideojuegos{" +
                "videojuegos=" + videojuegos +
                '}';
    }

    public String[] showEverything() {
        String[] data = new String[videojuegos.size() + 1];

        // Formatear encabezados
        String header = String.format("%-5s %-20s %-20s %-10s", "ID", "Nombre", "Compañía", "Precio");
        data[0] = header;

        // Recorrer la lista de videojuegos
        for (int i = 0; i < videojuegos.size(); i++) {
            String id = String.valueOf(videojuegos.get(i).getId());
            String name = videojuegos.get(i).getName();
            String company = videojuegos.get(i).getCompany();
            double price = videojuegos.get(i).getPrice();

            // Formatear la fila y agregarla al array
            data[i + 1] = String.format("%-5s %-20s %-20s €%-10.2f", id, name, company, price);
        }

        return data; // Devolver el array con la tabla formateada
    }

    public String lookCheaper() {

        float min = videojuegos.get(0).getPrice();
        int pos = 0;

        for (int i = 0; i < videojuegos.size(); i++) {
            if (min > videojuegos.get(i).getPrice() && pos != i) {
                pos = i;
                min = videojuegos.get(i).getPrice();
                i = 0;
            }
        }

        return videojuegos.get(pos).getName();
    }

    public String lookExpensive() {

        float max = videojuegos.get(0).getPrice();
        int pos = 0;

        for (int i = 0; i < videojuegos.size(); i++) {
            if (max < videojuegos.get(i).getPrice() && pos != i) {
                pos = i;
                max = videojuegos.get(i).getPrice();
                i = 0;
            }
        }

        return videojuegos.get(pos).getName();

    }
    public int higherId(){
        {

            int max = videojuegos.get(0).getId();
            int pos = 0;

            for (int i = 0; i < videojuegos.size(); i++) {
                if (max < videojuegos.get(i).getId() && pos != i) {
                    pos = i;
                    max = videojuegos.get(i).getId();
                    i = 0;
                }
            }

            return videojuegos.get(pos).getId();

        }
    }

    public void addGame(Videojuego videojuego){
        videojuegos.add(videojuego);
        videojuego.addXML();
    }

    public void deleteGame(int id){
        findId(id).deleteXMl();
        videojuegos.remove(findId(id));

    }

    public String[] showGame(int id){
        return findId(id).show();
    }
    public Videojuego findId(int id){

         return videojuegos.stream()
                .filter(videojuego -> videojuego.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Videojuego> searchCompanyGame(String company){
        return videojuegos.stream()
                .filter(videojuego ->  videojuego.getCompany().equalsIgnoreCase(company)).collect(Collectors.toList());
    }
    public String[] gameCompany(List<Videojuego> videojuegos){
        return videojuegos.stream().map(Videojuego::getCompany).toArray(String[]::new);
    }

    public String[] searchGame(String name) {

        return videojuegos.stream()
                .filter(v -> v.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(game -> showGame(game.getId()))
                .orElse(new String[]{"Juego no encontrado"});
    }
    public void export(){
        for (Videojuego v :videojuegos){
            v.export();
        }
    }
}
