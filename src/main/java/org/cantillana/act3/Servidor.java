package org.cantillana.act3;
import java.io.*;
import java.net.*;
public class Servidor {
    public static void main(String[] args) {
        final int NUM_CLIENTES = 3; // Número de clientes
        int contadorClientes = 0; // Contador de clientes

        try {
            // Crear un ServerSocket en el puerto 1234
            ServerSocket serverSocket = new ServerSocket(1234);

            while (contadorClientes < NUM_CLIENTES) {
                // Esperar a que un cliente se conecte
                Socket socket = serverSocket.accept();
                contadorClientes++;

                // Iniciar un nuevo hilo para manejar la comunicación con el cliente
                new Thread(new ClienteHandler(socket, contadorClientes)).start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class ClienteHandler implements Runnable {
    private final Socket socket;
    private final int numeroCliente;

    public ClienteHandler(Socket socket, int numeroCliente) {
        this.socket = socket;
        this.numeroCliente = numeroCliente;
    }

    @Override
    public void run() {
        try {
            // Obtener los flujos de entrada y salida
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());

            // Enviar un mensaje al cliente
            salida.writeUTF("Eres el cliente número " + numeroCliente);

            // Leer la respuesta del cliente
            String mensaje = entrada.readUTF();
            System.out.println("Mensaje del cliente " + numeroCliente + ": " + mensaje);

            // Cerrar el socket
            socket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}