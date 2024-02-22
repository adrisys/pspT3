package org.cantillana.act5v2;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private static final int PUERTO = 4444;

    public static void main(String[] args) throws Exception {
        int numeroDeClientes = 3;
        try (ServerSocket servidorSocket = new ServerSocket(PUERTO)) {
            for (int i = 1; i <= numeroDeClientes; i++) {
                Socket socketCliente = servidorSocket.accept();
                new Thread(new ClienteHandler(socketCliente, i)).start();
            }
        }
    }
}

class ClienteHandler implements Runnable {
    private Socket clienteSocket;
    private int numeroDeCliente;

    public ClienteHandler(Socket clienteSocket, int numeroDeCliente) {
        this.clienteSocket = clienteSocket;
        this.numeroDeCliente = numeroDeCliente;
    }

    @Override
    public void run() {
        try (DataOutputStream out = new DataOutputStream(clienteSocket.getOutputStream())) {
            out.writeUTF("Eres el cliente número " + numeroDeCliente);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clienteSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}