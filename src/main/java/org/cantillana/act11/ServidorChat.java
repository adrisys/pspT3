package org.cantillana.act11;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class ServidorChat {
	static final int MAX_CONS = 10;
	public static void main(String[] args) throws IOException {
		int port = 44444;
		ServerSocket servidor = new ServerSocket(port);
		Socket[] conectados = new Socket[MAX_CONS];
		ComunHilos ch = new ComunHilos(MAX_CONS, 0, 0, conectados);
		while (ch.getCons() < MAX_CONS) {
			Socket cli;
			cli = servidor.accept();
			ch.addCon(cli, ch.getCons());
			ch.setAct(ch.getAct() + 1);
			ch.setCons(ch.getCons() + 1);
			HiloServidorChat hiloChat = new HiloServidorChat(cli, ch);
			hiloChat.start();
		}
		servidor.close();
	}
}
