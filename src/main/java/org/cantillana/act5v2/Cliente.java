package org.cantillana.act5v2;
import java.io.DataInputStream;
import java.net.Socket;

public class Cliente {
    private static final int PUERTO = 4444;

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("localhost", PUERTO);
             DataInputStream in = new DataInputStream(socket.getInputStream())) {
            String mensajeDelServidor = in.readUTF();
            System.out.println("Mensaje del servidor: " + mensajeDelServidor);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}