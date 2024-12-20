package DAO;

import Persistencia.Xml;

import java.util.ArrayList;

public class Videojuego {

    private int id;
    private String name;
    private String company;
    private float price;

    public Videojuego(int id, String name, String company, float price) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.price = price;
    }

    //Setter y getter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    //ToString

    @Override
    public String toString() {
        return "Videojuegos{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", price=" + price +
                '}';
    }

    //Metodo para el tratado de datos
    public static ArrayList<Videojuego> genList(ArrayList<String> datas) {
        //El indice de los marcadores
        int first, middle, end;

        //Los atributos de los objetos
        int id;
        float price;
        String name, company;

        //Lista de videojuegos
        ArrayList<Videojuego> videojuegos = new ArrayList<Videojuego>();

        for (String s : datas) {

            first = s.indexOf(";");
            middle = s.indexOf(";", first + 1);
            end = s.indexOf(";", middle + 1);

            id = Integer.parseInt(s.substring(0, first));
            name = s.substring(first + 1, middle);
            company = s.substring(middle + 1, end);
            price = Float.parseFloat(s.substring(end + 1, s.length() - 1));

            videojuegos.add(new Videojuego(id, name, company, price));
        }

        return videojuegos;
    }

    public String[] show() {
        String[] data = new String[4];

        data[0] = "id: " + getId();
        data[1] = "nombre: " + getName();
        data[2] = "Compañia: " + getCompany();
        data[3] = "Precio: " + getPrice();

        return data;
    }

    public String buildStringXml() {
        String line;

        line = "videojuego;-id:" + getId() + ";nombre:" + getName() + ";empresa:" + getCompany() + ";precio:" + getPrice();

        return line;
    }

    public void addXML() {
        Xml xml = Xml.getInstance("xml/videojuegos.xml");
        xml.add(buildStringXml());
    }

    public void deleteXMl() {
        Xml xml = Xml.getInstance("xml/videojuegos.xml");
        xml.eliminar(getId(), "videojuego");
    }

    public void export(){
        Xml xml = Xml.getInstance("xml/videojuegos.xml");
        xml.exportXml("C:\\xampp\\htdocs\\xml\\videojuegos.xml");
    }
}
