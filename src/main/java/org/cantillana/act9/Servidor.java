package org.cantillana.act9;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Servidor {
	static final int PORT = 44444;
	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(PORT);
			System.out.println("Servidor iniciado...");
			while (true) {
				Socket cli;
				cli = ss.accept();
				HiloServidor hiloCli = new HiloServidor(cli);
				hiloCli.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
