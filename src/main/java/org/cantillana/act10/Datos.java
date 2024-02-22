package org.cantillana.act10;
import java.io.Serializable;
public class Datos implements Serializable {
	private static final long serialVersionUID = 1L;
	String str;
	int intentos;
	int id;
	boolean gana;
	boolean juega;
	
	public Datos(String str, int intentos, int id) {
		this.str = str;
		this.intentos = intentos;
		this.id = id;
		this.gana = false;
		this.juega = true;
	}
	
	public Datos() {}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public int getIntentos() {
		return intentos;
	}
	public void setIntentos(int intentos) {
		this.intentos = intentos;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isGana() {
		return gana;
	}
	public void setGana(boolean gana) {
		this.gana = gana;
	}
	public boolean isJuega() {
		return !juega;
	}
	public void setJuega(boolean juega) {
		this.juega = juega;
	}

}
