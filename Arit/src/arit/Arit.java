/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arit;

import AnalizadorA.parser;
import AnalizadorA.scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author marvi
 */
public class Arit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Editor editor = new Editor();
        editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        editor.show();

    }

}
