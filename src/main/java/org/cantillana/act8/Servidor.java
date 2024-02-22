package org.cantillana.act8;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Servidor {
    private static final int PUERTO = 8888;

    public static void main(String[] args) throws Exception {
        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            byte[] buffer = new byte[1024];
            DatagramPacket paqueteEntrante = new DatagramPacket(buffer, buffer.length);

            socket.receive(paqueteEntrante);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Persona persona = (Persona) ois.readObject();

            System.out.println("Persona recibida: " + persona.getNombre() + ", " + persona.getEdad());

            // Modificar los datos de la persona
            persona.setNombre("Nuevo nombre");
            persona.setEdad(30);

            System.out.println("Persona modificada: " + persona.getNombre() + ", " + persona.getEdad());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(persona);
            byte[] personaBytes = baos.toByteArray();

            DatagramPacket paqueteSaliente = new DatagramPacket(personaBytes, personaBytes.length, paqueteEntrante.getAddress(), paqueteEntrante.getPort());
            socket.send(paqueteSaliente);
        }
    }
}