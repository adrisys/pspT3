package org.cantillana.act11;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class ClienteChat extends JFrame implements ActionListener, Runnable {
	private static final long serialVersionUID = 1L;
	transient Socket cli;
	transient DataInputStream reception;
	transient DataOutputStream envio;
	String nick;
	static JTextField mensaje = new JTextField();
	static JTextArea textarea1 = null;
	JButton botonEnviar = new JButton("Enviar");
	JButton botonSalir = new JButton("Salir");
	boolean repetir = true;

	public ClienteChat(Socket s, String nick) {
		super(nick);
		setLayout(null);

		mensaje.setBounds(10, 10, 400, 30);
		add(mensaje);
		JScrollPane scrollpanel = new JScrollPane(textarea1);
		scrollpanel.setBounds(10, 50, 400, 300);
		add(scrollpanel);
		botonEnviar.setBounds(420, 10, 100, 30);
		add(botonEnviar);
		botonSalir.setBounds(420, 50, 100, 30);
		add(botonSalir);
		textarea1.setEditable(false);
		botonEnviar.addActionListener(this);
		botonSalir.addActionListener(this);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		cli = s;
		this.nick = nick;
		try {
			reception = new DataInputStream(cli.getInputStream());
			envio = new DataOutputStream(cli.getOutputStream());
			String txt = " > Entra en el chat " + this.nick;
			envio.writeUTF(txt);
		} catch (Exception e) {
			System.out.println("Error de entrada/salida en el cliente");
			e.printStackTrace();
			System.exit(0);
		}
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == botonEnviar) {
			if (mensaje.getText().trim().isEmpty())
				return;
			String txt = this.nick + "> " + mensaje.getText();
			try {
				mensaje.setText("");
				envio.writeUTF(txt);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (ae.getSource() == botonSalir) {
			String txt = " > " + this.nick + " abandonó el chat";
			try {
				envio.writeUTF(txt);
				envio.writeUTF("*");
				repetir = false;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	@Override
	public void run() {
		String txt;
		while (repetir) {
			try {
				txt = reception.readUTF();
				textarea1.setText(txt);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "No se puede conectar con el servidor\n" + e.getMessage(),
						"<<MENSAJE DE ERROR: 2>>", JOptionPane.ERROR_MESSAGE);
				repetir = false;
			}
		}
		try {
			cli.close();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		int port = 44444;
		Socket s;
		String nick = JOptionPane.showInputDialog("Introduce tu nombre:");
		if (nick.trim().isEmpty()) {
			System.out.println("El nombre está vacío");
			return;
		}
		try {
			s = new Socket("localhost", port);
			ClienteChat cliente = new ClienteChat(s, nick);
			cliente.setBounds(0, 0, 540, 400);
			cliente.setVisible(true);
			new Thread(cliente).start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se puede conectar con el servidor\n" + e.getMessage(),
					"<<MENSAJE DE ERROR: 1>>", JOptionPane.ERROR_MESSAGE);
		}
	}
}
