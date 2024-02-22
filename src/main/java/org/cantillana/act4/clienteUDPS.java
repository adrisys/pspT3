package org.cantillana.act4;

import java.io.*;
import java.net.*;

public class clienteUDPS {
    public static void main(String args[]) {
        try {
            // FLUJO PARA ENTRADA STANDARD
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket clientSocket = new DatagramSocket(); //socket cliente
            clientSocket.setSoTimeout(5000); // establecer tiempo de espera a 5000 milisegundos

            byte[] enviados;
            byte[] recibidos = new byte[1024];

            // DATOS DEL SERVIDOR al que enviar mensaje
            InetAddress IPServidor = InetAddress.getLocalHost(); // localhost
            int puerto = 1234; // puerto por el que escucha

            String cadena;
            do {
                // INTRODUCIR DATOS POR TECLADO
                System.out.print("Introduce mensaje: ");
                cadena = in.readLine();
                enviados = cadena.getBytes();

                // ENVIANDO DATAGRAMA AL SERVIDOR
                System.out.println("Enviando " + enviados.length + " bytes al servidor.");
                DatagramPacket envio = new DatagramPacket(enviados, enviados.length, IPServidor, puerto);
                clientSocket.send(envio);

                // RECIBIENDO DATAGRAMA DEL SERVIDOR
                DatagramPacket recibo = new DatagramPacket(recibidos, recibidos.length);
                System.out.println("Esperando datagrama....");
                try {
                    clientSocket.receive(recibo);
                    String mayuscula = new String(recibo.getData());
                    // OBTENIENDO INFORMACIÓN DEL DATAGRAMA
                    InetAddress IPOrigen = recibo.getAddress();
                    int puertoOrigen = recibo.getPort();
                    System.out.println("Procedente de: " + IPOrigen + ":" + puertoOrigen);
                    System.out.println("\tDatos: " + mayuscula.trim());
                } catch (InterruptedIOException e) {
                    System.out.println("El paquete se ha perdido.");
                }
            } while (!cadena.equals("*"));

            clientSocket.close(); //cerrar socket
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
