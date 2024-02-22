package org.cantillana.act2;
import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        try {
            // Crear un ServerSocket en el puerto 1234
            ServerSocket serverSocket = new ServerSocket(1234);

            // Esperar a que un cliente se conecte
            Socket socket = serverSocket.accept();

            // Obtener los flujos de entrada y salida
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());

            // Enviar un mensaje al cliente
            salida.writeUTF("Hola desde el servidor!");

            // Leer la respuesta del cliente
            String mensaje = entrada.readUTF();
            System.out.println("Mensaje del cliente: " + mensaje);

            // Cerrar el socket
            socket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
