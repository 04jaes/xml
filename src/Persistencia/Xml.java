package Persistencia;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Xml {
    public static Xml instance;
    private String filePath;

    public static Xml getInstance(String filePath) {
        if (instance == null) {
            instance = new Xml(filePath);
        }
        return instance;
    }

    public Xml(String filePath) {
        this.filePath = filePath;
    }

    public Document genDoc() {

        File file = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc = null;

        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(file);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } finally {
            return doc;
        }

    }

    public ArrayList<String> leer(String item, String[] atributes, String[] elements) {
        Document doc = genDoc();
        NodeList nodeList = null;
        StringBuilder result = new StringBuilder("");
        Node node = null;
        Element elemento = null;
        ArrayList<String> results = new ArrayList<String>();

        try {

            nodeList = doc.getElementsByTagName(item);
            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);

                if (node.getNodeType() == node.ELEMENT_NODE) {

                    elemento = (Element) node;

                    for (int j = 0; j < atributes.length; j++) {

                        result.append(elemento.getAttribute(atributes[j]));
                        result.append(";");

                    }

                    for (int j = 0; j < elements.length; j++) {

                        elemento = (Element) node;
                        result.append(elemento.getElementsByTagName(elements[j]).item(0).getTextContent());
                        result.append(";");
                    }

                }
                results.add(result.toString());

                result.setLength(0);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    public void add(String item) {
        int mark;
        Document doc = genDoc();
        String[] parts = item.split(";");
        Element newElement = null;
        Element childElement;

        try {

            newElement = doc.createElement(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                if (parts[i].charAt(0) == '-') {
                    mark = parts[i].indexOf(":");
                    newElement.setAttribute(parts[i].substring(1, mark), parts[i].substring(mark+1));
                } else {
                    mark = parts[i].indexOf(":");
                    childElement = doc.createElement(parts[i].substring(0, mark));
                    childElement.setTextContent(parts[i].substring(mark+1));
                    newElement.appendChild(childElement);
                }
            }
            doc.getDocumentElement().appendChild(newElement);
            // Guardar los cambios en el archivo XML
            guardarFichero(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void eliminar(int id, String item) {

        Document doc = instance.genDoc();

        try {
            NodeList nodeList = doc.getElementsByTagName(item);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) node;

                    if (Integer.parseInt(elemento.getAttribute("id")) == id) {
                        elemento.getParentNode().removeChild(elemento);
                        break;
                    }
                }
            }
            // Guardar los cambios en el archivo XML
            guardarFichero(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void guardarFichero(Document doc) {
        try {
            // Configurar el transformer para indentación
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Establecer la propiedad de indentación para que el XML sea tabulado
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Usar 4 espacios para la indentación

            // Crear un StreamResult para guardar el archivo
            StreamResult result = new StreamResult(new File(Xml.instance.filePath)); // Asumiendo que filePath es una variable de clase que contiene la ruta del archivo XML

            // Transformar el documento a archivo
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);

        } catch (TransformerException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el archivo XML: " + e.getMessage());
        }
    }
    public void exportXml( String filePath) {
        try {
            // Crear un Transformer para convertir el Document a un archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Configuración de la salida: convertir a un formato más legible (con indentación)
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // Crear un objeto DOMSource con el Document
            DOMSource source = new DOMSource(genDoc());

            // Crear un StreamResult con la ruta del archivo donde se guardará el XML
            StreamResult result = new StreamResult(new File(filePath));

            // Transformar el documento a un archivo
            transformer.transform(source, result);


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al exportar el archivo XML: " + e.getMessage());
        }
    }
}




