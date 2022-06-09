
package com.game;
import javax.swing.JFrame;

public class Application {
  public static void main(String[] args) {
    Principal principal = new Principal();
    principal.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    principal.setSize(600, 600);
    principal.setVisible(true);

  }
}
