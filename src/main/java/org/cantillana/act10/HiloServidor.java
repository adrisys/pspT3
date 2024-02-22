package org.cantillana.act10;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
public class HiloServidor extends Thread {
	ObjectInputStream recepcion;
	ObjectOutputStream envio;
	Socket s;
	ObjetoCompartido obj;
	int id;
	int intentos = 0;
	public HiloServidor(Socket s, ObjetoCompartido obj, int id) {
		this.s = s;
		this.obj = obj;
		this.id = id;
		try {

			envio = new ObjectOutputStream(s.getOutputStream());
			recepcion = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			System.err.println("Error en el Hilo al crear los flujos de entrada/salida:");
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		System.out.println("=> Cliente conectado: " + id);
		Datos datos = new Datos("Adivina el numero (entre 1 y 25)", intentos, id);
		if (obj.seAcabo()) {
			datos.setStr("El juego ya ha acabado. Ya hay un ganador.");
			datos.setJuega(false);
		}
		try {
			envio.reset();
			envio.writeObject(datos);
		} catch (IOException e) {
			System.err.println("Error al enviar el mensaje al jugador: " + id);
			return;
		}
        if (!(obj.seAcabo() && datos.getIntentos() == 5)) do {
            int numElegido;
            try {
                Datos d = (Datos) recepcion.readObject();
                numElegido = Integer.parseInt(d.getStr());
            } catch (IOException e) {
                System.err.println("Error en el Hilo al leer del jugador: " + id);
                break;
            } catch (InputMismatchException e) {
                System.err.println("El jugador: " + id + " se ha desconectado");
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
            String str = obj.nuevaJugada(id, numElegido);
            intentos++;
            datos = new Datos(str, intentos, id);
            if (obj.seAcabo()) {
                datos.setJuega(false);
                if (id == obj.getGanador())
                    datos.setGana(true);
            }
            try {
                envio.reset();
                envio.writeObject(datos);
            } catch (IOException e) {
                System.err
                        .println("Error en el flujo de salida del jugador: " + id + " * " + e.getMessage());
                break;
            } catch (NullPointerException e) {
                System.err.println("El jugador " + id + " se ha desconectado");
                break;
            }
        } while (!(obj.seAcabo() && datos.getIntentos() == 5));
		if(obj.seAcabo()) {
			System.out.println("FIN");
		}
		try {
			envio.close();
			recepcion.close();
			s.close();
		} catch (IOException e) {
			System.err.println("Error durante cierre de cliente " + id);
			e.printStackTrace();
		}
	}

}
