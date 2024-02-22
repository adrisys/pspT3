package org.cantillana.act5;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPServidor = InetAddress.getLocalHost();
            int puerto = 1234;

            Persona persona = new Persona("Nombre original", 25);
            System.out.println("Original: " + persona);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(persona);
            byte[] enviados = baos.toByteArray();

            DatagramPacket envio = new DatagramPacket(enviados, enviados.length, IPServidor, puerto);
            clientSocket.send(envio);

            byte[] recibidos = new byte[1024];
            DatagramPacket recibo = new DatagramPacket(recibidos, recibidos.length);
            clientSocket.receive(recibo);

            ByteArrayInputStream bais = new ByteArrayInputStream(recibidos);
            ObjectInputStream ois = new ObjectInputStream(bais);
            persona = (Persona) ois.readObject();
            System.out.println("Recibido: " + persona);

            clientSocket.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}