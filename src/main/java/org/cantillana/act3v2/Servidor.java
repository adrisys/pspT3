package org.cantillana.act3v2;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            Socket clientSocket = serverSocket.accept();

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String mensaje = "MENSAJE DEL SERVIDOR";
            out.println(mensaje);

            String mensajeRecibido = in.readLine();
            System.out.println("Mensaje recibido del cliente: " + mensajeRecibido);

            serverSocket.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}