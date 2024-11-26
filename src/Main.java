import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ListaEntrenamiento entrenamientos = entrenamiento();
        System.out.println(entrenamientos);
        exportar();
    }

    public static ListaEntrenamiento entrenamiento(){
        int id  = 0;
        String nombre = "";
        int duracion = 0;
        String nivel = "";

        ArrayList<Entrenamiento> entrenamientos = new ArrayList<Entrenamiento>();

        File file = null;

        DocumentBuilder builder = null;
        DocumentBuilderFactory factory = null;
        Document doc = null;

        NodeList nodeList = null;
        Node node = null;

        Element elemento = null;
        try {
            //La ruta del fichero
            file = new File("xml/entrenamientos.xml");
            //Adadtador del fichero
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            //El documento guardado en memoria
            doc = builder.parse(file);

            //La lista de nodos
            nodeList = doc.getElementsByTagName("entrenamiento");


            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);

                if (node.getNodeType() == node.ELEMENT_NODE) {
                    elemento = (Element) node;
                    //item sirve para elegir cual aparicion se quiere coger en caso que haya repeticion
                    id = Integer.parseInt(elemento.getAttribute("id"));
                    nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
                    duracion = Integer.parseInt(elemento.getElementsByTagName("duracion").item(0).getTextContent());
                    nivel = elemento.getElementsByTagName("nivel").item(0).getTextContent();

                }
                entrenamientos.add(new Entrenamiento(id,nombre,nivel,duracion));
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        return new ListaEntrenamiento(entrenamientos);
    }

    public static void exportar(){
        try {
            File file = new File("xml/entrenamiento.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element entrenamientos = doc.createElement("Entrenamientos");
            doc.appendChild(entrenamientos);

            // Crear un nuevo elemento "entrenamiento"
            Element nuevoEntrenamiento = doc.createElement("entrenamiento");
            nuevoEntrenamiento.setAttribute("id", "2");

            Element nombre = doc.createElement("nombre");
            nombre.setTextContent("HIIT Avanzado");

            Element duracion = doc.createElement("duracion");
            duracion.setTextContent("40");

            Element nivel = doc.createElement("nivel");
            nivel.setTextContent("Duro");

            nuevoEntrenamiento.appendChild(nombre);
            nuevoEntrenamiento.appendChild(duracion);
            nuevoEntrenamiento.appendChild(nivel);



// Guardar los cambios en el archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 File file = null;
        DocumentBuilder builder = null;
        try {
            //La ruta del fichero
            file = new File("xml/personas.xml");
            //Adadtador del fichero
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList nodeList = doc.getElementsByTagName("persona");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == node.ELEMENT_NODE) {
                    Element elemento = (Element) node;
                    System.out.print(elemento.getElementsByTagName("id").item(0).getTextContent() + " ");
                    System.out.println(elemento.getElementsByTagName("nombre").item(0).getTextContent());

                }

            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
*/