// Gabriel Otero Lopez
package com.game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Principal extends JFrame implements ActionListener, ItemListener {
    JLabel lblLetra;
    JLabel[][] lblLetras = new JLabel[10][5];
    JRadioButton rdbEstandar;
    JRadioButton rdbFacil;
    ButtonGroup grpDificultad;
    JButton btnInicio;
    JTextField txfPalabra;

    int letraX;
    int letraY;
    int letraWH = 40;

    int opW = 100;
    int opH = 20;
    int opX = 7;
    int opSeparacion = 10;

    String barraOS = System.getProperty("file.separator");
    String homeUsu = System.getProperty("user.home");

    int numeroRandom = 0;

    String[] palabrasSplit;
    String palabras = "";

    private String palabra = "";
    private int intentos = 6;

    String palabraUsuario = "";
    int filaActual = 0;

    boolean jugando = false;

    public Principal() {
        super("Wordle");
        setLayout(null);
        setFocusable(true);

        MouseHandler mouseHandler = new MouseHandler();
        KeyHandler keyHandler = new KeyHandler();

        rdbEstandar = new JRadioButton("Estándar");
        rdbEstandar.setSize(opW, opH);
        rdbEstandar.setLocation(opX, 10);
        rdbEstandar.setSelected(true);
        rdbEstandar.addMouseListener(mouseHandler);
        rdbEstandar.addItemListener(this);
        add(rdbEstandar);

        rdbFacil = new JRadioButton("Fácil");
        rdbFacil.setSize(opW, opH);
        rdbFacil.setLocation(opX, rdbEstandar.getLocation().y + rdbEstandar.getHeight() + opSeparacion);
        rdbFacil.addMouseListener(mouseHandler);
        rdbFacil.addItemListener(this);
        add(rdbFacil);

        grpDificultad = new ButtonGroup();
        grpDificultad.add(rdbEstandar);
        grpDificultad.add(rdbFacil);

        btnInicio = new JButton("Inicio");
        btnInicio.setSize(opW, opH);
        btnInicio.setLocation(opX, rdbFacil.getLocation().y + rdbFacil.getHeight() + opSeparacion);
        btnInicio.addActionListener(this);
        add(btnInicio);

        txfPalabra = new JTextField();
        txfPalabra.setSize(opW, opH);
        txfPalabra.setLocation(opX, btnInicio.getLocation().y + btnInicio.getHeight() + opSeparacion);
        txfPalabra.setEnabled(false);
        txfPalabra.addActionListener(this);
        add(txfPalabra);

        letraX = 120;
        letraY = 10;
        for (int i = 0; i < lblLetras.length; i++) {

            for (int j = 0; j < lblLetras[i].length; j++) {

                lblLetra = new JLabel();
                lblLetra.setSize(letraWH, letraWH);
                lblLetra.setOpaque(true);
                lblLetra.setHorizontalAlignment(JLabel.CENTER);
                lblLetra.setVerticalAlignment(JLabel.CENTER);
                lblLetra.setForeground(Color.white);
                lblLetra.setLocation(letraX, letraY);
                lblLetra.setVisible(false);
                lblLetras[i][j] = lblLetra;

                if (j == 4) {
                    this.letraX = 120;
                    this.letraY += 50;

                } else {
                    letraX += 50;
                }
                add(lblLetras[i][j]);

            }

        }

        addKeyListener(keyHandler);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                if (jugando) {
                    int res = JOptionPane.showConfirmDialog(null, "Seguro que quieres finalizar", "Wordle",
                            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                    if (res == JOptionPane.YES_OPTION) {
                        dispose();
                    }

                } else {
                    dispose();
                }
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnInicio) {
            jugando = true;
            txfPalabra.setEnabled(true);

            for (int i = 0; i < intentos; i++) {

                for (int j = 0; j < lblLetras[i].length; j++) {
                    lblLetras[i][j].setVisible(true);
                    lblLetras[i][j].setBackground(Color.white);

                    try (Scanner s = new Scanner(new File(homeUsu + barraOS + "palabras.txt"))) {

                        while (s.hasNext()) {

                            palabras = s.next() + "\n";

                        }

                    } catch (IOException u) {
                    }

                }

            }
            palabrasSplit = palabras.split(";");

            numeroRandom = (int) (Math.random() * palabrasSplit.length);
            palabra = palabrasSplit[numeroRandom];

            this.setTitle(palabra);
        }

        if (e.getSource() == txfPalabra) {
            palabraUsuario = txfPalabra.getText();

            palabraUsuario = palabraUsuario.toUpperCase();

            if (palabraUsuario.length() == 5) {

                if (filaActual <= intentos) {

                    for (int i = 0; i < palabraUsuario.length(); i++) {
                        lblLetras[filaActual][i].setBackground(comprueba(palabraUsuario.charAt(i), i));
                        lblLetras[filaActual][i].setText(Character.toString(palabraUsuario.charAt(i)));
                    }
                    filaActual++;

                }

                if (filaActual >= intentos || palabraUsuario.equals(palabra)) {
                    txfPalabra.setEnabled(false);
                    Secundario secundario = new Secundario(this, palabra, filaActual);
                    secundario.setSize(300, 300);
                    secundario.setVisible(true);
                    secundario.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

                }

            }
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getSource() == rdbEstandar) {

            if (rdbEstandar.isSelected()) {

                intentos = 6;
            }
        }

        if (e.getSource() == rdbFacil) {
            if (rdbFacil.isSelected()) {
                intentos = 10;
				
            }
        }

    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            e.getComponent().setForeground(Color.yellow);

        }

        @Override
        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);
            e.getComponent().setForeground(Color.BLACK);
        }

    }

    public Color comprueba(char letra, int posicion) {

        boolean tieneLetra = false;

        for (int i = 0; i < palabra.length(); i++) {
            if (palabra.charAt(i) == letra) {
                tieneLetra = true;
            }

        }

        if (tieneLetra && palabra.charAt(posicion) == letra) {
            return Color.decode("#126a06");
        } else if (tieneLetra) {
            return Color.orange;
        } else {
            return Color.gray;
        }
    }

    private class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_F5) {
                jugando = true;
            }
        }

    }

}
