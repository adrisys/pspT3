package org.cantillana.act9;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class HiloServidor extends Thread {
	BufferedReader recepcion;
	PrintWriter envio;
	Socket conexion;
	
	public HiloServidor(Socket conexion) {
		
		this.conexion = conexion;
		try {
			this.envio = new PrintWriter(conexion.getOutputStream(), true);
			this.recepcion = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		String txt = "";
		
		String ip_cli = this.conexion.getInetAddress().toString();
		String port_remoto = String.valueOf(this.conexion.getPort());
		System.out.println("=>Conecta IP " + ip_cli + ", Puerto Remoto: " + port_remoto);
		
		while (!txt.trim().equals("*")) {
			try {
				txt = recepcion.readLine();
				envio.println(txt.trim().toUpperCase());
			} catch (IOException e) {
			}
		}
		
		System.out.println("\t=>Desconecta IP " + ip_cli + ", Puerto Remoto: " + port_remoto);
		
		try {
			this.envio.close();
			this.recepcion.close();
			this.conexion.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
