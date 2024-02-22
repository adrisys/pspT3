package org.cantillana.act7;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    private static final int PUERTO = 5555;

    public static void main(String[] args) {
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("Introduce un número: ");
                int numero = Integer.parseInt(lector.readLine());

                if (numero <= 0) {
                    break;
                }

                Numeros numeros = new Numeros();
                numeros.setNumero(numero);

                try (Socket socket = new Socket("localhost", PUERTO);
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    out.writeObject(numeros);

                    numeros = (Numeros) in.readObject();

                    System.out.println("Cuadrado: " + numeros.getCuadrado());
                    System.out.println("Cubo: " + numeros.getCubo());
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}