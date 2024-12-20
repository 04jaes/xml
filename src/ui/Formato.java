package ui;

public class Formato {
    public static String azul(String text){
        return "\u001B[34m" + text +"\u001B[0m";
    }
    public static String verde(String text){
        return "\u001B[32m" + text +"\u001B[0m";
    }
    public static String amarillo(String text){
        return "\u001B[33m" + text +"\u001B[0m";
    }
    public static String rojo(String text){
        return "\u001B[31m" + text +"\u001B[0m";
    }
    public static String morado(String text){
        return "\u001B[35m" + text +"\u001B[0m";
    }
}
