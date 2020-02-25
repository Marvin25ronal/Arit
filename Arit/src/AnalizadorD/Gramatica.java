/* Generated By:JavaCC: Do not edit this line. Gramatica.java */
package AnalizadorD;
import java.util.LinkedList;
import Reportes.Errores;
import Reportes.Errores.TipoError;
import java.util.ArrayList;
import Expresion.*;
import Expresion.TipoExp.Tipos;
import AST.*;
import Instruccion.Instruccion;
import Instruccion.Print;
import Objetos.Nulo;
import Operaciones.Aritmeticas;
import Operaciones.Operacion;
import Operaciones.Logicas;
import Operaciones.Relacional;
public class Gramatica implements GramaticaConstants {

/*

░██████╗██╗███╗░░██╗████████╗░█████╗░░█████╗░████████╗██╗░█████╗░░█████╗░
██╔�?�?�?�?�?██║████╗░██║╚�?�?██╔�?�?�?██╔�?�?██╗██╔�?�?██╗╚�?�?██╔�?�?�?██║██╔�?�?██╗██╔�?�?██╗
╚█████╗░██║██╔██╗██║░░░██║░░░███████║██║░░╚�?�?░░░██║░░░██║██║░░╚�?�?██║░░██║
░╚�?�?�?██╗██║██║╚████║░░░██║░░░██╔�?�?██║██║░░██╗░░░██║░░░██║██║░░██╗██║░░██║
██████╔�?██║██║░╚███║░░░██║░░░██║░░██║╚█████╔�?░░░██║░░░██║╚█████╔�?╚█████╔�?
╚�?�?�?�?�?�?░╚�?�?╚�?�?░░╚�?�?�?░░░╚�?�?░░░╚�?�?░░╚�?�?░╚�?�?�?�?�?░░░░╚�?�?░░░╚�?�?░╚�?�?�?�?�?░░╚�?�?�?�?�?░
*/
/**
INICIO->(CUERPO)+EOF
*/
  final public AST INICIO() throws ParseException {
AST arbol;
LinkedList<Nodo>lista=new LinkedList();
Nodo aux;
    label_1:
    while (true) {
      aux = CUERPO();
                      lista.add(aux);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PRINT:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
    }
    jj_consume_token(0);
                                               arbol=new AST(lista);{if (true) return arbol;}
    throw new Error("Missing return statement in function");
  }

  final public Nodo CUERPO() throws ParseException {
Instruccion aux;
    aux = FUNCIONES();
                        {if (true) return aux;}
    throw new Error("Missing return statement in function");
  }

  final public Instruccion FUNCIONES() throws ParseException {
        Instruccion ins;
    ins = IMPRIMIR();
                       {if (true) return ins;}
    throw new Error("Missing return statement in function");
  }

  final public Instruccion IMPRIMIR() throws ParseException {
        Expresion exp;
        Token t;
    t = jj_consume_token(PRINT);
    jj_consume_token(PAR_A);
    exp = EXP();
    jj_consume_token(PAR_C);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PYCOMA:
      jj_consume_token(PYCOMA);
      break;
    default:
      jj_la1[1] = jj_gen;
      ;
    }
                                                        {if (true) return new Print(exp,t.beginLine,t.beginColumn);}
    throw new Error("Missing return statement in function");
  }

  final public Expresion EXP() throws ParseException {
        Expresion a,b,c;
        Token t;
    a = CondicionOR();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PREGUNTA:
      t = jj_consume_token(PREGUNTA);
      b = EXP();
      jj_consume_token(DOSPUNTOS);
      c = EXP();
      break;
    default:
      jj_la1[2] = jj_gen;
      ;
    }
         {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Expresion CondicionOR() throws ParseException {
        Expresion a,b;
        Token t;
    a = CondicionAnd();
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OR:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_2;
      }
      t = jj_consume_token(OR);
      b = CondicionAnd();
                                                a=new Logicas(a,b,Operacion.Operador.OR,t.beginLine,t.beginColumn);
    }
         {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Expresion CondicionAnd() throws ParseException {
Expresion a,b;
        Token t;
    a = ExpresionIgualdad();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_3;
      }
      t = jj_consume_token(AND);
      b = ExpresionIgualdad();
                                                           a=new Logicas(a,b,Operacion.Operador.AND,t.beginLine,t.beginColumn);
    }
         {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Expresion ExpresionIgualdad() throws ParseException {
Expresion a,b;
        Token t;
    a = ExpresionRelacional();
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DISTINTO:
      case IGUAL_IGUAL:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_4;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IGUAL_IGUAL:
        t = jj_consume_token(IGUAL_IGUAL);
        b = ExpresionRelacional();
                                                                        a=new Relacional(a,b,Operacion.Operador.IGUAL_IGUAL,t.beginLine,t.beginColumn);
        break;
      case DISTINTO:
        t = jj_consume_token(DISTINTO);
        b = ExpresionRelacional();
                                               a=new Relacional(a,b,Operacion.Operador.DISTINTO,t.beginLine,t.beginColumn);
        break;
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
         {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Expresion ExpresionRelacional() throws ParseException {
        Expresion a,b;
        Token t;
    a = ExpresionAditiva();
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MAYOR_I:
      case MENOR_I:
      case MAYOR:
      case MENOR:
        ;
        break;
      default:
        jj_la1[7] = jj_gen;
        break label_5;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MAYOR:
        t = jj_consume_token(MAYOR);
        b = ExpresionAditiva();
                                               a=new Relacional(a,b,Operacion.Operador.MAYOR,t.beginLine,t.beginColumn);
        break;
      case MENOR:
        t = jj_consume_token(MENOR);
        b = ExpresionAditiva();
                                                a=new Relacional(a,b,Operacion.Operador.MENOR,t.beginLine,t.beginColumn);
        break;
      case MAYOR_I:
        t = jj_consume_token(MAYOR_I);
        b = ExpresionAditiva();
                                                 a=new Relacional(a,b,Operacion.Operador.MAYOR_IGUAL,t.beginLine,t.beginColumn);
        break;
      case MENOR_I:
        t = jj_consume_token(MENOR_I);
        b = ExpresionAditiva();
                                                 a=new Relacional(a,b,Operacion.Operador.MENOR_IGUAL,t.beginLine,t.beginColumn);
        break;
      default:
        jj_la1[8] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
         {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Expresion ExpresionAditiva() throws ParseException {
        Expresion a,b;
        Token t;
    a = ExpresionMultiplicativas();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MAS:
      case MENOS:
        ;
        break;
      default:
        jj_la1[9] = jj_gen;
        break label_6;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MAS:
        t = jj_consume_token(MAS);
        b = ExpresionMultiplicativas();
                                                    a=new Aritmeticas(a,b,Operacion.Operador.SUMA,t.beginLine,t.beginColumn);
        break;
      case MENOS:
        t = jj_consume_token(MENOS);
        b = ExpresionMultiplicativas();
                                                       a=new Aritmeticas(a,b,Operacion.Operador.RESTA,t.beginLine,t.beginColumn);
        break;
      default:
        jj_la1[10] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
         {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Expresion ExpresionMultiplicativas() throws ParseException {
Expresion a,b;
 Token t;
    a = ExpresionUnaria();
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case POR:
      case DIV:
      case POTENCIA:
      case MODULO:
        ;
        break;
      default:
        jj_la1[11] = jj_gen;
        break label_7;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case POR:
        t = jj_consume_token(POR);
        b = ExpresionUnaria();
                                            a=new Aritmeticas(a,b,Operacion.Operador.MULTIPLICACION,t.beginLine,t.beginColumn);
        break;
      case DIV:
        t = jj_consume_token(DIV);
        b = ExpresionUnaria();
                                            a=new Aritmeticas(a,b,Operacion.Operador.DIVISION,t.beginLine,t.beginColumn);
        break;
      case POTENCIA:
        t = jj_consume_token(POTENCIA);
        b = ExpresionUnaria();
                                                 a=new Aritmeticas(a,b,Operacion.Operador.POTENCIA,t.beginLine,t.beginColumn);
        break;
      case MODULO:
        t = jj_consume_token(MODULO);
        b = ExpresionUnaria();
                                               a=new Aritmeticas(a,b,Operacion.Operador.MODULO,t.beginLine,t.beginColumn);
        break;
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
         {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  final public Expresion ExpresionUnaria() throws ParseException {
        Expresion exp;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MENOS:
      jj_consume_token(MENOS);
      ExpresionUnaria();

      break;
    case NOT:
      jj_consume_token(NOT);
      ExpresionUnaria();

      break;
    case INTEGER:
    case NUMERIC:
    case PAR_A:
    case BOOLEANO:
    case NULO:
    case IDENTIFICADOR:
    case STRING:
      exp = Primitivo();
                         {if (true) return exp;}
      break;
    default:
      jj_la1[13] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Expresion Primitivo() throws ParseException {
        Expresion aux;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NUMERIC:
      jj_consume_token(NUMERIC);
                  {if (true) return new Literal(Double.parseDouble(token.image),new TipoExp(Tipos.NUMERIC),token.beginLine,token.beginColumn);}
      break;
    case BOOLEANO:
      jj_consume_token(BOOLEANO);
                    {if (true) return new Literal(Boolean.parseBoolean(token.image),new TipoExp(Tipos.BOOLEAN),token.beginLine,token.beginColumn);}
      break;
    case INTEGER:
      jj_consume_token(INTEGER);
                   {if (true) return new Literal(Integer.parseInt(token.image),new TipoExp(Tipos.INTEGER),token.beginLine,token.beginColumn);}
      break;
    case STRING:
      jj_consume_token(STRING);
                  {if (true) return new Literal(token.image.substring(1,token.image.length()-1),new TipoExp(Tipos.STRING),token.beginLine,token.beginColumn);}
      break;
    case IDENTIFICADOR:
      jj_consume_token(IDENTIFICADOR);
      break;
    case PAR_A:
      jj_consume_token(PAR_A);
      aux = EXP();
      jj_consume_token(PAR_C);
                                   {if (true) return aux;}
      break;
    case NULO:
      jj_consume_token(NULO);
                {if (true) return new  Nulo(token.beginLine,token.beginColumn);}
      break;
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public GramaticaTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[15];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x0,0x200,0x0,0x40000000,0x20000000,0x1800000,0x1800000,0x1e000000,0x1e000000,0xc00,0xc00,0xf000,0xf000,0x80010980,0x10180,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x4,0x0,0x2,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x129,0x129,};
   }

  /** Constructor with InputStream. */
  public Gramatica(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Gramatica(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new GramaticaTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Gramatica(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new GramaticaTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Gramatica(GramaticaTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(GramaticaTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List jj_expentries = new java.util.ArrayList();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[41];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 15; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 41; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
