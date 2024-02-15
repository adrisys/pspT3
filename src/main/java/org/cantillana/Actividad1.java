package org.cantillana;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Actividad1 {
    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                InetAddress inetAddress = InetAddress.getByName(args[0]);
                System.out.println("Nombre del Host: " + inetAddress.getHostName());
                System.out.println("Dirección IP: " + inetAddress.getHostAddress());
            } catch (UnknownHostException e) {
                System.out.println("Host no encontrado: " + args[0]);
            }
        } else {
            System.out.println("Por favor, proporciona un nombre de host o una dirección IP como argumento.");
        }
    }
}