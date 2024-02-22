package org.cantillana.act4;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Servidor {
    public static void main(String[] args) {
        try {
            // Crear un DatagramSocket en el puerto 1234
            DatagramSocket serverSocket = new DatagramSocket(1234);

            byte[] recibirDatos = new byte[1024];
            byte[] enviarDatos;

            while (true) {
                // Esperar a que un cliente envíe un datagrama
                DatagramPacket recibirPaquete = new DatagramPacket(recibirDatos, recibirDatos.length);
                serverSocket.receive(recibirPaquete);

                String mensaje = new String(recibirPaquete.getData(), 0, recibirPaquete.getLength());

                // Preparar los datos para enviar al cliente
                enviarDatos = ("Hola desde el servidor, recibí: " + mensaje).getBytes();

                // Enviar un datagrama al cliente
                InetAddress direccionIP = recibirPaquete.getAddress();
                int puerto = recibirPaquete.getPort();
                DatagramPacket enviarPaquete = new DatagramPacket(enviarDatos, enviarDatos.length, direccionIP, puerto);
                serverSocket.send(enviarPaquete);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}