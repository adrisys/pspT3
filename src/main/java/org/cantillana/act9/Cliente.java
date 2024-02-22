package org.cantillana.act9;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class Cliente extends JFrame {
	private static final long serialVersionUID = 1L;
	private final JTextField txtEnvio;
	private final JTextField txtRespuesta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente frame = new Cliente();
					frame.setVisible(true);
				} catch (Exception e) {

					JOptionPane.showMessageDialog(null, "SERVIDOR DESCONECTADO\n" + e.getMessage(),
							"ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cliente() throws IOException {

		String ip_server = "localhost";
		int port = 44444;
		Socket cli = new Socket(ip_server, port);
		final PrintWriter envio = new PrintWriter(cli.getOutputStream(), true);
		final BufferedReader recepcion = new BufferedReader(new InputStreamReader(cli.getInputStream()));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3,
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3, 465, 200);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("conversor de texto");
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblNewLabel = new JLabel("Escribe texto:");
		lblNewLabel.setBounds(10, 23, 92, 23);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);
		txtEnvio = new JTextField();
		txtEnvio.setBounds(20, 56, 267, 32);
		contentPane.add(txtEnvio);
		txtEnvio.setColumns(10);
		txtRespuesta = new JTextField();
		txtRespuesta.setBounds(20, 108, 267, 32);
		contentPane.add(txtRespuesta);
		txtRespuesta.setColumns(10);
		txtRespuesta.setEnabled(false);
		txtRespuesta.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				envio.println(txtEnvio.getText().trim());
				if (txtEnvio.getText().trim().equals("*"))
					dispose();
				try {
					txtRespuesta.setText(recepcion.readLine());
				} catch (IOException e1) {
					txtRespuesta.setText("ERROR");
				}
			}
		});
		btnEnviar.setBounds(328, 33, 108, 32);
		contentPane.add(btnEnviar);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				envio.println("*");
				dispose();
			}
		});
		btnSalir.setBounds(328, 75, 108, 32);
		contentPane.add(btnSalir);

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtEnvio.setText("");
				txtRespuesta.setText("");
			}
		});
		btnLimpiar.setBounds(328, 117, 108, 32);
		contentPane.add(btnLimpiar);
	}
}
