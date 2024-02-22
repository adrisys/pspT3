package org.cantillana.act10;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
            try (ServerSocket serv = new ServerSocket(23456)) {
                System.out.println("Servidor iniciado...");
                int solucion = (int) (1 + (25 * Math.random()));
                // Todos los hilos comparten el objeto
                ObjetoCompartido obj = new ObjetoCompartido(solucion);
                int id = 0;
                boolean running = true;
                while (running) {
                    Socket cli = serv.accept();
                    id++;
                    HiloServidor hilo = new HiloServidor(cli, obj, id);
                    hilo.start();
                    if (id >= 10) {
                        running = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
