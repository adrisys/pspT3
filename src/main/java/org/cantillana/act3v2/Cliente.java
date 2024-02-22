package org.cantillana.act3v2;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 4444);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String mensajeRecibido = in.readLine();
            System.out.println("Mensaje recibido: " + mensajeRecibido);

            String mensajeEnMinusculas = mensajeRecibido.toLowerCase();
            out.println(mensajeEnMinusculas);

            clientSocket.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}