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
        Editor editor=new Editor();
        editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        editor.show();
       
    }
    /*
    File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String texto="";
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("E:\\Datos\\Netbeans\\Proyectos\\Arit\\ArchivosPrueba\\Prueba.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                texto+=linea+"\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        
        scanner sc = new scanner(new BufferedReader(new StringReader(texto)));
        parser parser = new parser(sc);
        try {
            parser.parse();

        } catch (Exception ex) {

            Logger.getLogger(Arit.class.getName()).log(Level.SEVERE, null, ex);
        }
    */

}
