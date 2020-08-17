/*
 * Course Name: CST8284_303
 * Student Name: Duy Pham
 * Class Name: RegLauncher
 * Date: Aug 09, 2020
 * 
 */

package cst8284.asgmt4.landRegistry;

import javax.swing.*;

/**
 * This is the main class to launch the program
 * @author Duy Pham
 * @version 1.02
 */
public class RegLauncher {
  /**
   * The main method of the program
   * Reference: From Assignment 4 Instruction by Dave Houtman
   * @param args
   */
  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater
        (new Runnable() {
          public void run() {
              new RegViewGUI();
              RegViewGUI.launchDialog();
          }
        });
  }

}