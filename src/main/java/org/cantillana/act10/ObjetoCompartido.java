package org.cantillana.act10;
public class ObjetoCompartido {
	private final int numeroCorrecto;
    private int ganador;
	private boolean acabo;
	public ObjetoCompartido(int numero) {
		this.numeroCorrecto = numero;
		this.acabo = false;
	}
	public boolean seAcabo() {
		return acabo;
	}
	public int getGanador() {
		return ganador;
	}
	public synchronized String nuevaJugada(int jugador, int numElegido) {
		String str;
		if (!seAcabo()) {
			if (numElegido > numeroCorrecto)
				str = "Número demasiado grande";
			else if (numElegido < numeroCorrecto)
				str = "Número demasiado bajo";
			else {
				str = "Jugador " + jugador + " gana, adivinó el número: " + numeroCorrecto;
				acabo = true;
				ganador = jugador;
			}
		} else
			str = "Jugador " + ganador + " adivinó el número: " + numeroCorrecto;
		return str;
	}
	
}
