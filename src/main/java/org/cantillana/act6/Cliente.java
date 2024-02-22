package org.cantillana.act6;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Cliente {
    private static final int PUERTO = 5555;

    public static void main(String[] args) throws Exception {
        try (DatagramSocket socket = new DatagramSocket();
             BufferedReader lector = new BufferedReader(new InputStreamReader(System.in))) {
            InetAddress direccionServidor = InetAddress.getByName("localhost");
            socket.setSoTimeout(5000);

            while (true) {
                System.out.print("Introduce un mensaje: ");
                String mensaje = lector.readLine();

                DatagramPacket paqueteSaliente = new DatagramPacket(mensaje.getBytes(), mensaje.length(), direccionServidor, PUERTO);
                socket.send(paqueteSaliente);

                if (mensaje.equals("*")) {
                    break;
                }

                byte[] buffer = new byte[1024];
                DatagramPacket paqueteEntrante = new DatagramPacket(buffer, buffer.length);

                try {
                    socket.receive(paqueteEntrante);
                    mensaje = new String(paqueteEntrante.getData(), 0, paqueteEntrante.getLength());
                    System.out.println("Mensaje del servidor: " + mensaje);
                } catch (java.net.SocketTimeoutException e) {
                    System.out.println("El paquete se ha perdido.");
                }
            }
        }
    }
}
