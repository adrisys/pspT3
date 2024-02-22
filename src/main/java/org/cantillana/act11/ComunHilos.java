package org.cantillana.act11;
import java.net.Socket;
public class ComunHilos {
	int conexiones;
	int actuales;
	int max;
	Socket[] conectados;
	String msg;
	public ComunHilos(int max, int actuales, int conexiones, Socket[] conectados) {
		this.max = max;
		this.actuales = actuales;
		this.conexiones = conexiones;
		this.conectados = conectados;
		this.msg = "";
	}
	public int getCons() {
		return conexiones;
	}
	public synchronized void setCons(int conexiones) {
		this.conexiones = conexiones;
	}
	public int getAct() {
		return actuales;
	}
	public synchronized void setAct(int actuales) {
		this.actuales = actuales;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public String getMsg() {
		return msg;
	}
	public synchronized void setMsg(String msg) {
		this.msg = msg;
	}
	public synchronized void addCon(Socket s, int i) {
		conectados[i] = s;
	}
	public Socket getConectadoN(int n) {
		return conectados[n];
	}
}
