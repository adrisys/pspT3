package org.cantillana.act8;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;

public class Cliente {
    private static final int PUERTO = 8888;

    public static void main(String[] args) throws Exception {
        try (DatagramSocket socket = new DatagramSocket()) {
            Persona persona = new Persona();
            persona.setNombre("Nombre original");
            persona.setEdad(25);

            System.out.println("Persona original: " + persona.getNombre() + ", " + persona.getEdad());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(persona);
            byte[] personaBytes = baos.toByteArray();

            DatagramPacket paqueteSaliente = new DatagramPacket(personaBytes, personaBytes.length, InetAddress.getByName("localhost"), PUERTO);
            socket.send(paqueteSaliente);

            byte[] buffer = new byte[1024];
            DatagramPacket paqueteEntrante = new DatagramPacket(buffer, buffer.length);
            socket.receive(paqueteEntrante);

            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = new ObjectInputStream(bais);
            persona = (Persona) ois.readObject();

            System.out.println("Persona modificada: " + persona.getNombre() + ", " + persona.getEdad());
        }
    }
}
