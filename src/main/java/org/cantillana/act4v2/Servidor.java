package org.cantillana.act4v2;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        {

                try {
                    ServerSocket serverSocket = new ServerSocket(4444);
                    Socket clientSocket = serverSocket.accept();
                    DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

                    int numero = in.readInt();
                    int cuadrado = numero * numero;

                    out.writeInt(cuadrado);
                } catch (java.io.IOException e) {
                    System.out.println("Error E/S: " + e.getMessage());
                }
            }
        }
    }
