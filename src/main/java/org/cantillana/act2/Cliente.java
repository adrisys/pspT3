package org.cantillana.act2;
import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            // Crear un socket de cliente en el puerto 1234
            Socket socket = new Socket("localhost", 1234);

            // Obtener los flujos de entrada y salida
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());

            // Leer el mensaje del servidor
            String mensaje = entrada.readUTF();
            System.out.println("Mensaje del servidor: " + mensaje);

            // Enviar una respuesta al servidor
            salida.writeUTF("Este es el mensaje devuelto al servidor!");

            // Cerrar el socket
            socket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
