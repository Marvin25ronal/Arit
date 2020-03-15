/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arit;

import AST.AST;
import AnalizadorA.parser;
import AnalizadorA.scanner;
import AnalizadorD.Gramatica;
import AnalizadorD.ParseException;
import AnalizadorD.TokenMgrError;
import Color.CampoTexto;
import Color.TextLineNumber;
import Reportes.Errores;
import Reportes.ReporteAST;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *
 * @author marvi
 */
public class Editor extends javax.swing.JFrame {

    /**
     * Creates new form Editor
     */
    TextLineNumber salto1;
    TextLineNumber salto2;
    List<RSyntaxTextArea> Lista;
    List<String> rutas;
    int id = 0;
    String directorioArchivos = "C:\\Users\\User\\Desktop";

    public Editor() {
        initComponents();
        Lista = new ArrayList<RSyntaxTextArea>();
        rutas = new ArrayList<String>();
        agregarpest();
    }

    private void agregarpest() {
        CampoTexto cam = new CampoTexto(jTextField2, jTextField3);
        //cam.setTexto("");
        //cam.setPath("");

        this.jTabbedPane1.add("Pestana" + id, cam);

        // this.jTabbedPane1.add("Pestana " + id, area);
        Lista.add(cam.getAreaTexto());

        rutas.add("");
        id++;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton1.setText("Limpiar");

        jMenu1.setText("Archivo");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Pestaña");

        jMenuItem1.setText("Agregar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Ejecutar");

        jMenuItem2.setText("Ascendente");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuItem4.setText("Descendente");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Reportes");

        jMenuItem3.setText("Reporte AST");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem3);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(661, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2)
                    .addComponent(jTextField3)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        agregarpest();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        Globales.VarGlobales.getInstance().LimpiarLista();
        int indice = jTabbedPane1.getSelectedIndex();
        String texto = Lista.get(indice).getText();
        scanner sc = new scanner(new BufferedReader(new StringReader(texto)));
        jTextArea1.setText("");
        Globales.VarGlobales.getInstance().setConsola(jTextArea1);
        parser parser = new parser(sc);
        try {
            parser.parse();
            if (sc.listaerrores.size() != 0) {
                for (Errores e : sc.listaerrores) {
                    jTextArea1.append(e.toString() + "\n");
                }
            }
            if (parser.listaerrores.size() != 0) {
                for (Errores e : parser.listaerrores) {
                    jTextArea1.append(e.toString() + "\n");
                }
            }
            AST ast = parser.ast;
            if (ast != null) {
                ast.ejecutar();
            }
        } catch (Exception e) {
            jTextArea1.append(e.getMessage());
            Logger.getLogger(Arit.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        Globales.VarGlobales.getInstance().LimpiarLista();
        int indice = jTabbedPane1.getSelectedIndex();
        String texto = Lista.get(indice).getText();
        scanner sc = new scanner(new BufferedReader(new StringReader(texto)));
        jTextArea1.setText("");
        Globales.VarGlobales.getInstance().setConsola(jTextArea1);
        parser parser = new parser(sc);
        try {
            parser.parse();
            if (sc.listaerrores.size() != 0) {
                for (Errores e : sc.listaerrores) {
                    jTextArea1.append(e.toString() + "\n");
                }
            }
            if (parser.listaerrores.size() != 0) {
                for (Errores e : parser.listaerrores) {
                    jTextArea1.append(e.toString() + "\n");
                }
            }
            AST ast = parser.ast;
            if (ast != null) {
                String reporte=ast.HacerDot();
                String codigo = reporte;
                String dotpath = "E:\\Program Files (x86)\\Graphviz\\bin\\dot.exe";
                String fileoutput = "./Arit.txt";
                File archivo = new File(fileoutput);
                BufferedWriter nuevo = new BufferedWriter(new FileWriter(archivo));
                nuevo.write(codigo);
                nuevo.close();
                String[] cmd = new String[5];
                cmd[0] = dotpath;
                cmd[1] = "-Tjpg";
                cmd[2] = "./Arit.txt";
                cmd[3] = "-o";
                cmd[4] = "Arbol.png";
                Runtime rt = Runtime.getRuntime();
                rt.exec(cmd);
                System.out.println(reporte);
            }
        } catch (Exception e) {
            jTextArea1.append(e.getMessage());
            Logger.getLogger(Arit.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        Globales.VarGlobales.getInstance().LimpiarLista();
        int indice = jTabbedPane1.getSelectedIndex();
        String texto = Lista.get(indice).getText();
        jTextArea1.setText("");
        Globales.VarGlobales.getInstance().setConsola(jTextArea1);
        if (!texto.isEmpty()) {
            try {
                Gramatica parser = new Gramatica(new BufferedReader(new StringReader((texto))));
                AST arbol = parser.INICIO();
                arbol.ejecutar();
            } catch (ParseException e) {
                jTextArea1.append(e.getMessage() + "\n");
            } catch (TokenMgrError e) {
                jTextArea1.append(e.getMessage() + "\n");
            }
        }

    }//GEN-LAST:event_jMenuItem4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Editor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
