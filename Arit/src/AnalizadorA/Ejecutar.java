/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorA;

/**
 *
 * @author marvi
 */
public class Ejecutar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            String texto = "src/AnalizadorA/";
            String opcFlex[] = {texto + "Lexico.jflex", "-d", texto};
            String cupo[] = {"-destdir", texto, "-parser", "parser", texto + "Sintactico.cup"};
            JFlex.Main.generate(opcFlex);
            java_cup.Main.main(cupo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
