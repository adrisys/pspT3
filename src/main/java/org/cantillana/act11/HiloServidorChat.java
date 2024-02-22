package org.cantillana.act11;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
public class HiloServidorChat extends Thread {
	DataInputStream recepcion;
	Socket cli;
	ComunHilos ch;
	public HiloServidorChat(Socket cli, ComunHilos ch) {
		this.cli = cli;
		this.ch = ch;
		try {
			this.recepcion = new DataInputStream(cli.getInputStream());
		} catch (IOException e) {
			System.out.println("Error de entrada/salida en el servidor");
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		System.out.println("Conexiones actuales: " + ch.getAct());
		String txtContent = ch.getMsg();
		mgsSpray(txtContent);
		while (true) {
			String txt;
			try {
				txt = recepcion.readUTF();
				if (txt.trim().equals("*")) {
					ch.setAct(ch.getAct() - 1);
					System.out.println("Conexiones actuales: " + ch.getAct());
					break;
				}
				ch.setMsg(ch.getMsg() + txt + "\n");
				mgsSpray(ch.getMsg());
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		try {
			cli.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void mgsSpray(String msg) {
		for (int i = 0; i < ch.getCons(); i++) {
			Socket aux = ch.getConectadoN(i);
			if (!aux.isClosed()) {
				try {
					DataOutputStream envio = new DataOutputStream(aux.getOutputStream());
					envio.writeUTF(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
