package ui;

import java.util.Scanner;

public class Lector {
    public static String pedir() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static String pedir(String texto) {
        Scanner sc = new Scanner(System.in);
        System.out.println(texto);
        return sc.nextLine();
    }
}
