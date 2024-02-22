package org.cantillana.act10;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class ClientePlayer extends JFrame {
	private static final long serialVersionUID = 1L;
	private final JTextField txtTry;
	private final JTextField txtNumber;
	private final JTextField txtRespuesta;
	private Datos dt;
	static {
		new Datos();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientePlayer frame = new ClientePlayer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public ClientePlayer() throws IOException, ClassNotFoundException {

		String host = "localhost";
		int port = 23456;

		Socket cli = new Socket(host, port);
		final ObjectOutputStream envio = new ObjectOutputStream(cli.getOutputStream());
		final ObjectInputStream recepcion = new ObjectInputStream(cli.getInputStream());
		dt = (Datos) recepcion.readObject();
		int id_player = dt.getId();
		System.out.println(dt.getStr());
		if (dt.isJuega()) {
			dispose();
			JOptionPane.showMessageDialog(null, "Ya se ha finalizado el juego", "info",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		setResizable(false);
		setTitle("Adivina el número");
		JLabel lblNewLabel = new JLabel("id del jugador:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(22, 24, 104, 18);
		contentPane.add(lblNewLabel);
		JLabel lblIntentos = new JLabel("intentos:");
		lblIntentos.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblIntentos.setBounds(249, 24, 104, 18);
		contentPane.add(lblIntentos);
		JTextField txtIdPlayer = new JTextField();
		txtIdPlayer.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtIdPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		txtIdPlayer.setEnabled(false);
		txtIdPlayer.setEditable(false);
		txtIdPlayer.setBounds(139, 23, 50, 25);
		contentPane.add(txtIdPlayer);
		txtIdPlayer.setColumns(10);
		txtIdPlayer.setText(String.valueOf(id_player));
		txtTry = new JTextField();
		txtTry.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtTry.setHorizontalAlignment(SwingConstants.CENTER);
		txtTry.setEnabled(false);
		txtTry.setEditable(false);
		txtTry.setColumns(10);
		txtTry.setBounds(352, 24, 50, 25);
		contentPane.add(txtTry);
		txtTry.setText(String.valueOf(dt.getIntentos() + 1));
		JLabel lblNmeroAAdivinar = new JLabel("Numero a adivinar:");
		lblNmeroAAdivinar.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNmeroAAdivinar.setBounds(22, 93, 182, 18);
		contentPane.add(lblNmeroAAdivinar);
		txtNumber = new JTextField();
		txtNumber.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		txtNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtNumber.setColumns(10);
		txtNumber.setBounds(202, 93, 50, 24);
		contentPane.add(txtNumber);
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int mal_number = -1;
				if (dt.isJuega()) {
					dispose();
					JOptionPane.showMessageDialog(null, "Juego finalizado", "info",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					Datos data = new Datos();
					if (!validarCadena(txtNumber.getText())) {
						data.setStr("0"); // Se envía un número fuera del rango
						mal_number = 0;
						txtRespuesta.setText("El número debe estar entre 1 y 25");
					} else
						data.setStr(txtNumber.getText());

					try {
						envio.reset();
						envio.writeObject(data);
						dt = (Datos) recepcion.readObject();
						if (mal_number != 0)
							txtRespuesta.setText(dt.getStr());
						txtTry.setText(String.valueOf(dt.getIntentos() + 1));
					} catch (IOException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					if (dt.getIntentos() >= 5) {
						dispose();
						JOptionPane.showMessageDialog(null, "No quedan intentos", "info",
								JOptionPane.INFORMATION_MESSAGE);
					} else if (dt.isGana()) {
						dispose();
						JOptionPane.showMessageDialog(null, "Has ganado!", "Info",
								JOptionPane.INFORMATION_MESSAGE);
					} else if (dt.isJuega()) {
						dispose();
						JOptionPane.showMessageDialog(null, "Juego finalizado", "info",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}

			}
		});
		btnEnviar.setBounds(299, 89, 115, 30);
		contentPane.add(btnEnviar);
		txtRespuesta = new JTextField();
		txtRespuesta.setHorizontalAlignment(SwingConstants.CENTER);
		txtRespuesta.setEnabled(false);
		txtRespuesta.setEditable(false);
		txtRespuesta.setColumns(10);
		txtRespuesta.setBounds(22, 164, 392, 30);
		contentPane.add(txtRespuesta);
		txtRespuesta.setText(dt.getStr());
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSalir.setBounds(165, 216, 115, 37);
		contentPane.add(btnSalir);
	}
	private static boolean validarCadena(String cadena) {
		boolean valor = false;
		try {
			Integer.valueOf(cadena);
			valor = true;
		} catch (NumberFormatException e) {
		}
		return valor;
	}
}
