package Color;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.fife.ui.rtextarea.RTextScrollPane;

public class CampoTexto extends JPanel {

    private RSyntaxTextArea areaTexto;
    private RTextScrollPane scrollTexto;
    JTextField fila;
    JTextField columna;

    public CampoTexto(JTextField fila, JTextField columna) {
        super(new GridLayout());
        iniciarAjustes();
        iniciarColores();
        this.fila = fila;
        this.columna = columna;
    }

    private void iniciarColores() {
        SyntaxScheme scheme = areaTexto.getSyntaxScheme();
        scheme.getStyle(Token.RESERVED_WORD).foreground = new Color(Integer.parseInt("093B70", 16));
        scheme.getStyle(Token.FUNCTION).foreground = new Color(Integer.parseInt("4A7BB0", 16));
        scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = new Color(Integer.parseInt("E7AC10", 16));;
        scheme.getStyle(Token.IDENTIFIER).foreground = Color.BLACK;
        scheme.getStyle(Token.DATA_TYPE).foreground = new Color(Integer.parseInt("086A87", 16));//#086A87
        scheme.getStyle(Token.COMMENT_EOL).foreground = Color.GRAY;
        scheme.getStyle(Token.LITERAL_CHAR).foreground = new Color(Integer.parseInt("E7AC10", 16));
        scheme.getStyle(Token.COMMENT_MULTILINE).foreground = Color.GRAY;
        scheme.getStyle(Token.SEPARATOR).foreground = Color.RED;
        scheme.getStyle(Token.OPERATOR).foreground = Color.RED;
        scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = new Color(Integer.parseInt("750BC6", 16));

        // scheme.getStyle(Token.FUNCTION).foreground = Color.black;
    }

    private void iniciarAjustes() {
        AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();

        atmf.putMapping("text", "Color.ColorArit");

        areaTexto = new RSyntaxTextArea(20, 60);
        //areaTexto.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        areaTexto.setSyntaxEditingStyle("text");
        areaTexto.setCodeFoldingEnabled(true);
        areaTexto.setCurrentLineHighlightColor(new Color(227, 242, 253));
        areaTexto.setFadeCurrentLineHighlight(true);
        areaTexto.setBorder(BorderFactory.createEmptyBorder());
        //nuevos

        areaTexto.setBracketMatchingEnabled(true);
        areaTexto.setAnimateBracketMatching(true);
        areaTexto.setAntiAliasingEnabled(true);
        areaTexto.setAutoIndentEnabled(true);
        areaTexto.setFadeCurrentLineHighlight(true);
        areaTexto.setMarkOccurrences(true);
        areaTexto.setFont(new Font("Consolas", 0, 15));
        scrollTexto = new RTextScrollPane(areaTexto);
        scrollTexto.setViewportBorder(BorderFactory.createEmptyBorder());
        scrollTexto.setIconRowHeaderEnabled(true);

        //scrollTexto.getGutter();
        //scrollTexto.getGutter().setLineNumberColor(Color.BLUE);
        // scrollTexto.get
        //scrollTexto.setFoldIndicatorEnabled(true);
        //scrollTexto.setEnabled(true);
        areaTexto.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                RSyntaxTextArea editarea = (RSyntaxTextArea) e.getSource();
                int linea = 1;
                int columna = 1;
                try {
                    int caretpos = editarea.getCaretPosition();

                    linea = editarea.getLineOfOffset(caretpos);

                    columna = caretpos - editarea.getLineStartOffset(linea);

                    // Ya que las líneas las cuenta desde la 0
                    linea += 1;
                } catch (Exception ex) {
                }

                // Actualizamos el estado
                actualizarEstado2(linea, columna);

            }
        });

        this.add(scrollTexto);
    }

    private void actualizarEstado2(int linea, int columna) {
        fila.setText("Linea " + linea);
        this.columna.setText("Columna " + columna);
    }

    public String getTexto() {
        return areaTexto.getText();
    }

    public void setTexto(String texto) {
        areaTexto.setText(texto);
    }

    /**
     * @return the guardado
     */
   

    /**
     * @return the areaTexto
     */
    public RSyntaxTextArea getAreaTexto() {
        return areaTexto;
    }

    /**
     * @param areaTexto the areaTexto to set
     */
    public void setAreaTexto(RSyntaxTextArea areaTexto) {
        this.areaTexto = areaTexto;
    }

}
