package org.cantillana.act6;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Servidor {
    private static final int PUERTO = 5555;

    public static void main(String[] args) throws Exception {
        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            byte[] buffer = new byte[1024];
            DatagramPacket paqueteEntrante = new DatagramPacket(buffer, buffer.length);

            while (true) {
                socket.receive(paqueteEntrante);
                String mensaje = new String(paqueteEntrante.getData(), 0, paqueteEntrante.getLength());

                if (mensaje.equals("*")) {
                    break;
                }

                System.out.println("Mensaje recibido: " + mensaje);

                mensaje = mensaje.toUpperCase();
                DatagramPacket paqueteSaliente = new DatagramPacket(mensaje.getBytes(), mensaje.length(), paqueteEntrante.getAddress(), paqueteEntrante.getPort());
                socket.send(paqueteSaliente);
            }
        }
    }
}
