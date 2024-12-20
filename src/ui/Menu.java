package ui;

import DAO.Videojuego;
import Logica.ListaVideojuegos;
import Persistencia.Xml;

public class Menu {
    public static void Start() {
        int opc;
        Xml xml = Xml.getInstance("xml/videojuegos.xml");
        String[] atributes = {"id"};
        String[] elements = {"nombre", "empresa", "precio"};

        ListaVideojuegos lv = new ListaVideojuegos(Videojuego.genList(xml.leer("videojuego", atributes, elements)));

        do {
            opc = mainMenu();
            switch (opc) {
                case 1:
                    showup(lv);
                    break;
                case 2:
                    crud(lv);
                    break;
                case 3:
                    System.out.println(Formato.morado("El mas barato es:") + "\n" + lv.lookCheaper() + "\n");
                    System.out.println(Formato.morado("El mas caro es:") + "\n" + lv.lookExpensive() + "\n");

                    break;
                case 4:
                    lv.export();
                    System.out.println("Para verlo copie el url localhost/xml/xmlGame.html");
                    break;
                case 5:
                    gamesCompany(lv);
                    break;
                case 6:
                    searchGame(lv);
                    break;
                case 7:
                    System.out.println(Formato.verde("GRACIAS POR USAR EL PROGRAMA :)"));
                    break;

                default:
                    System.out.println(Formato.rojo("Elija una de las opciones propuestas" + "\n"));
                    break;
            }
        } while (opc != 7);

    }


    //Metodo que muestra y recolecta las opciones
    public static int mainMenu() {
        System.out.println(Formato.azul("Elija una de la opciones, introduciendi el número que le corresponde"));
        System.out.println("""
                    1. Mostrar datos
                    2. CRUD
                    3. Buscar el mas barato y caro
                    4. Exportar
                    5. Buscar juegos de una empresa
                    6. Buscar un juego
                    7. Salir
                """);
        return Integer.parseInt(Lector.pedir());
    }


    //Metodo para mostrar todos los datos de la lista
    public static void showup(ListaVideojuegos lv) {
        System.out.printf("€-5s %-20s %-20s %-10s%n", "Nombre", "Compañía", "Precio");
        System.out.println("-------------------------------------------------------------");
        for (String s : lv.showEverything()) {
            System.out.println(s);
        }
        System.out.println("");
    }


    //Metodos del crud
    public static void crud(ListaVideojuegos lv) {
        int opc = menuCrud();
        switch (opc) {
            case 1:
                create(lv);
                break;
            case 2:
                update(lv);
                break;
            case 3:
                delete(lv);
                break;
            default:
                System.out.println(Formato.rojo("va a volver al menu iniciar, si de verdad desea hacer la acción" +
                        "vuelva a eligir la opcion 2 y luego elija una opcion correcta"));
        }
    }

    public static int menuCrud() {
        System.out.println(Formato.azul("Elija una de la opciones, introduciendi el número que le corresponde"));
        System.out.println("""
                    1. Crear
                    2. Modificar
                    3. Eliminar
                """);
        return Integer.parseInt(Lector.pedir());
    }

    public static void create(ListaVideojuegos lv) {
        int id = lv.higherId() + 1;
        float price;
        String name;
        String company;

        price = Float.parseFloat(Lector.pedir("Indruzca el precio"));
        name = Lector.pedir("Indruzca el nombre");
        company = Lector.pedir("Indruzama la compañia");

        lv.addGame(new Videojuego(id, name, company, price));
    }

    public static void delete(ListaVideojuegos lv) {
        showup(lv);
        lv.deleteGame(Integer.parseInt(Lector.pedir("Indruzca un id")));
    }

    public static void update(ListaVideojuegos lv) {
        int id;
        String[] data = new String[4];

        int newId;
        float price;
        String name, company;

        showup(lv);

        id = Integer.parseInt(Lector.pedir("Indroduzca el id que del juego que quierre cambiar"));
        data = lv.showGame(id);
        System.out.println("Si no desea cambiar nada y introduzca el mismo valor");

        System.out.println(Formato.morado("antiguo ->" + data[0]));
        newId = Integer.parseInt(Lector.pedir("Nuevo id"));

        System.out.println(Formato.morado("antiguo -> " + data[1]));
        name = Lector.pedir("Nuevo nombre");

        System.out.println(Formato.morado("antigua -> " + data[2]));
        company = Lector.pedir("Nueva compañia");

        System.out.println(Formato.morado("antiguo -> " + data[3]));
        price = Float.parseFloat(Lector.pedir("Precio nuevo"));

        lv.deleteGame(id);
        lv.addGame(new Videojuego(newId,name,company,price));
    }

    //Buscar
    //Juegos de una empresa
    public static void gamesCompany(ListaVideojuegos lv){
        String[] games = lv.gameCompany(lv.searchCompanyGame(Lector.pedir("Introduzca una empresa")));
        if (games.length > 0){
            System.out.println(Formato.morado("Nombre del juego"));
            for(String s :games){
                System.out.println(s);
            }
        }else {
            System.out.println(Formato.amarillo("No se ha encontrado ningun juego de esa empresa :/"));
        }

    }

    //juego especifico
    public static void searchGame(ListaVideojuegos lv){
        String[] data = lv.searchGame(Lector.pedir("Introduzca el nombre de un juego"));
        for (String s : data){
            System.out.println(s);
        }
    }

}
