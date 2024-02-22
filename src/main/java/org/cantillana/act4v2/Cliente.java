package org.cantillana.act4v2;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 4444);
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduce un número entero:");
            int numero = scanner.nextInt();
            out.writeInt(numero);
            int cuadrado = in.readInt();
            System.out.println("El cuadrado del número es: " + cuadrado);
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes introducir un número entero.");
        }
    }
}