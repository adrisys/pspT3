package org.cantillana.act7;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private static final int PUERTO = 5555;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

                    Numeros numeros = (Numeros) in.readObject();

                    if (numeros.getNumero() <= 0) {
                        break;
                    }
                    System.out.println("Número recibido: " + numeros.getNumero());
                    System.out.println("Calculando cuadrado...");
                    numeros.setCuadrado((long) Math.pow(numeros.getNumero(), 2));
                    System.out.println("Calculando cubo...");
                    numeros.setCubo((long) Math.pow(numeros.getNumero(), 3));
                    out.writeObject(numeros);
                    System.out.println("Resultado enviado");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
