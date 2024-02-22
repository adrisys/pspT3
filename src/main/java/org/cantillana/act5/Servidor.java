package org.cantillana.act5;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(1234);
            byte[] recibirDatos = new byte[1024];
            byte[] enviarDatos;

            while (!serverSocket.isClosed()) {
                DatagramPacket recibirPaquete = new DatagramPacket(recibirDatos, recibirDatos.length);
                serverSocket.receive(recibirPaquete);

                ByteArrayInputStream bais = new ByteArrayInputStream(recibirDatos);
                ObjectInputStream ois = new ObjectInputStream(bais);
                Persona persona = (Persona) ois.readObject();
                System.out.println("Recibido: " + persona);

                persona.setNombre("Nuevo nombre");
                persona.setEdad(30);
                System.out.println("Modificado: " + persona);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(persona);
                enviarDatos = baos.toByteArray();

                InetAddress direccionIP = recibirPaquete.getAddress();
                int puerto = recibirPaquete.getPort();
                DatagramPacket enviarPaquete = new DatagramPacket(enviarDatos, enviarDatos.length, direccionIP, puerto);
                serverSocket.send(enviarPaquete);
            }
        } catch (SocketException e) {
            System.out.println("Socket cerrado: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}