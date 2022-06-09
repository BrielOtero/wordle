// Gabriel Otero Lopez
package com.game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Secundario extends JDialog implements ActionListener {
	JLabel lblAcierto;
	JTextField txfnombre;
	Timer tmrTiempo;

	int contTiempo = 0;

	String palabraAcertada;
	String letras = "";

	boolean acierto = false;

	int fila;

	String barra;
	String home;

	public Secundario(Principal p, String palabra, int filaActual) {
		super(p, true);
		setLayout(new FlowLayout());

		tmrTiempo = new Timer(500, this);
		tmrTiempo.start();

		lblAcierto = new JLabel();
		lblAcierto.setOpaque(true);
		lblAcierto.setHorizontalAlignment(JLabel.CENTER);
		lblAcierto.setVerticalAlignment(JLabel.CENTER);
		lblAcierto.setForeground(Color.white);
		lblAcierto.setFont(new Font("Verdana", Font.BOLD, 18));
		lblAcierto.setBackground(Color.decode("#126a06"));
		add(lblAcierto);

		txfnombre = new JTextField(9);
		txfnombre.setToolTipText("Nombre");
		txfnombre.setVisible(false);
		txfnombre.addActionListener(this);
		add(txfnombre);

		palabraAcertada = palabra;
		acierto = p.palabraUsuario.equals(palabra);
		barra = p.barraOS;
		home = p.homeUsu;
		fila = p.filaActual;
		p.filaActual = 0;
		p.jugando = false;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == tmrTiempo) {

			if (contTiempo < palabraAcertada.length()) {
				letras += Character.toString(palabraAcertada.charAt(contTiempo));
				lblAcierto.setText(letras);
				contTiempo++;

				if (contTiempo == palabraAcertada.length()) {
					tmrTiempo.stop();

					if (acierto) {
						txfnombre.setVisible(true);
					}

				}

			}
		}

		if (e.getSource() == txfnombre) {

			try (PrintWriter p = new PrintWriter(new FileWriter(new File(home + barra + "records.txt"), true))) {

				p.println(String.format("%s %d", txfnombre.getText(), fila));

			} catch (IOException u) {
			}

			dispose();
		}

	}
}
