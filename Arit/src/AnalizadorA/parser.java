
//----------------------------------------------------
// The following code was generated by CUP v0.11b 20160615 (GIT 4ac7450)
//----------------------------------------------------

package AnalizadorA;

import java_cup.runtime.Symbol;
import java.util.LinkedList;
import Reportes.Errores;
import Reportes.Errores.TipoError;
import java.util.ArrayList;
import AST.*;
import java_cup.runtime.XMLElement;

/** CUP v0.11b 20160615 (GIT 4ac7450) generated parser.
  */
@SuppressWarnings({"rawtypes"})
public class parser extends java_cup.runtime.lr_parser {

 public final Class getSymbolContainer() {
    return sym.class;
}

  /** Default constructor. */
  @Deprecated
  public parser() {super();}

  /** Constructor which sets the default scanner. */
  @Deprecated
  public parser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\047\000\002\002\004\000\002\002\003\000\002\005" +
    "\003\000\002\005\004\000\002\005\003\000\002\005\004" +
    "\000\002\005\003\000\002\005\004\000\002\005\003\000" +
    "\002\003\005\000\002\003\006\000\002\004\004\000\002" +
    "\004\005\000\002\004\005\000\002\004\005\000\002\004" +
    "\005\000\002\004\005\000\002\004\005\000\002\004\004" +
    "\000\002\004\005\000\002\004\005\000\002\004\005\000" +
    "\002\004\005\000\002\004\005\000\002\004\005\000\002" +
    "\004\005\000\002\004\005\000\002\004\007\000\002\004" +
    "\005\000\002\004\003\000\002\004\003\000\002\004\003" +
    "\000\002\004\003\000\002\004\003\000\002\004\003\000" +
    "\002\011\003\000\002\011\004\000\002\010\006\000\002" +
    "\010\005" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\103\000\006\033\010\044\012\001\002\000\006\002" +
    "\ufffd\033\ufffd\001\002\000\006\002\ufff9\033\ufff9\001\002" +
    "\000\004\002\105\001\002\000\006\002\000\033\010\001" +
    "\002\000\004\042\077\001\002\000\006\002\uffff\033\uffff" +
    "\001\002\000\004\010\016\001\002\000\010\002\uffde\033" +
    "\uffde\043\015\001\002\000\006\002\ufffb\033\ufffb\001\002" +
    "\000\006\002\uffdd\033\uffdd\001\002\000\026\010\017\011" +
    "\031\013\027\024\021\033\026\034\022\035\024\036\020" +
    "\037\023\040\025\001\002\000\024\010\017\013\027\024" +
    "\021\033\026\034\022\035\024\036\020\037\023\040\025" +
    "\001\002\000\052\002\uffe1\011\uffe1\012\uffe1\014\uffe1\015" +
    "\uffe1\016\uffe1\017\uffe1\020\uffe1\021\uffe1\022\uffe1\023\uffe1" +
    "\024\uffe1\025\uffe1\026\uffe1\027\uffe1\030\uffe1\031\uffe1\032" +
    "\uffe1\033\uffe1\043\uffe1\001\002\000\024\010\017\013\027" +
    "\024\021\033\026\034\022\035\024\036\020\037\023\040" +
    "\025\001\002\000\052\002\uffe3\011\uffe3\012\uffe3\014\uffe3" +
    "\015\uffe3\016\uffe3\017\uffe3\020\uffe3\021\uffe3\022\uffe3\023" +
    "\uffe3\024\uffe3\025\uffe3\026\uffe3\027\uffe3\030\uffe3\031\uffe3" +
    "\032\uffe3\033\uffe3\043\uffe3\001\002\000\052\002\uffe0\011" +
    "\uffe0\012\uffe0\014\uffe0\015\uffe0\016\uffe0\017\uffe0\020\uffe0" +
    "\021\uffe0\022\uffe0\023\uffe0\024\uffe0\025\uffe0\026\uffe0\027" +
    "\uffe0\030\uffe0\031\uffe0\032\uffe0\033\uffe0\043\uffe0\001\002" +
    "\000\052\002\uffe2\011\uffe2\012\uffe2\014\uffe2\015\uffe2\016" +
    "\uffe2\017\uffe2\020\uffe2\021\uffe2\022\uffe2\023\uffe2\024\uffe2" +
    "\025\uffe2\026\uffe2\027\uffe2\030\uffe2\031\uffe2\032\uffe2\033" +
    "\uffe2\043\uffe2\001\002\000\052\002\uffdf\011\uffdf\012\uffdf" +
    "\014\uffdf\015\uffdf\016\uffdf\017\uffdf\020\uffdf\021\uffdf\022" +
    "\uffdf\023\uffdf\024\uffdf\025\uffdf\026\uffdf\027\uffdf\030\uffdf" +
    "\031\uffdf\032\uffdf\033\uffdf\043\uffdf\001\002\000\052\002" +
    "\uffe4\011\uffe4\012\uffe4\014\uffe4\015\uffe4\016\uffe4\017\uffe4" +
    "\020\uffe4\021\uffe4\022\uffe4\023\uffe4\024\uffe4\025\uffe4\026" +
    "\uffe4\027\uffe4\030\uffe4\031\uffe4\032\uffe4\033\uffe4\043\uffe4" +
    "\001\002\000\024\010\017\013\027\024\021\033\026\034" +
    "\022\035\024\036\020\037\023\040\025\001\002\000\042" +
    "\011\050\012\051\014\032\015\043\016\047\017\036\020" +
    "\033\021\046\022\037\023\041\024\034\025\042\026\040" +
    "\027\044\030\035\031\045\001\002\000\010\002\uffdb\033" +
    "\uffdb\043\uffdb\001\002\000\024\010\017\013\027\024\021" +
    "\033\026\034\022\035\024\036\020\037\023\040\025\001" +
    "\002\000\024\010\017\013\027\024\021\033\026\034\022" +
    "\035\024\036\020\037\023\040\025\001\002\000\024\010" +
    "\017\013\027\024\021\033\026\034\022\035\024\036\020" +
    "\037\023\040\025\001\002\000\024\010\017\013\027\024" +
    "\021\033\026\034\022\035\024\036\020\037\023\040\025" +
    "\001\002\000\024\010\017\013\027\024\021\033\026\034" +
    "\022\035\024\036\020\037\023\040\025\001\002\000\024" +
    "\010\017\013\027\024\021\033\026\034\022\035\024\036" +
    "\020\037\023\040\025\001\002\000\024\010\017\013\027" +
    "\024\021\033\026\034\022\035\024\036\020\037\023\040" +
    "\025\001\002\000\024\010\017\013\027\024\021\033\026" +
    "\034\022\035\024\036\020\037\023\040\025\001\002\000" +
    "\024\010\017\013\027\024\021\033\026\034\022\035\024" +
    "\036\020\037\023\040\025\001\002\000\024\010\017\013" +
    "\027\024\021\033\026\034\022\035\024\036\020\037\023" +
    "\040\025\001\002\000\024\010\017\013\027\024\021\033" +
    "\026\034\022\035\024\036\020\037\023\040\025\001\002" +
    "\000\024\010\017\013\027\024\021\033\026\034\022\035" +
    "\024\036\020\037\023\040\025\001\002\000\024\010\017" +
    "\013\027\024\021\033\026\034\022\035\024\036\020\037" +
    "\023\040\025\001\002\000\024\010\017\013\027\024\021" +
    "\033\026\034\022\035\024\036\020\037\023\040\025\001" +
    "\002\000\010\002\uffdc\033\uffdc\043\uffdc\001\002\000\024" +
    "\010\017\013\027\024\021\033\026\034\022\035\024\036" +
    "\020\037\023\040\025\001\002\000\052\002\uffee\011\uffee" +
    "\012\uffee\014\032\015\043\016\047\017\036\020\033\021" +
    "\046\022\037\023\041\024\034\025\042\026\040\027\044" +
    "\030\035\031\uffee\032\uffee\033\uffee\043\uffee\001\002\000" +
    "\042\002\uffe9\011\uffe9\012\uffe9\014\uffe9\021\uffe9\022\uffe9" +
    "\023\041\024\034\025\042\026\040\027\044\030\035\031" +
    "\uffe9\032\uffe9\033\uffe9\043\uffe9\001\002\000\052\002\uffe7" +
    "\011\uffe7\012\uffe7\014\uffe7\015\043\016\047\017\036\020" +
    "\033\021\uffe7\022\uffe7\023\041\024\034\025\042\026\040" +
    "\027\044\030\035\031\uffe7\032\uffe7\033\uffe7\043\uffe7\001" +
    "\002\000\042\012\051\014\032\015\043\016\047\017\036" +
    "\020\033\021\046\022\037\023\041\024\034\025\042\026" +
    "\040\027\044\030\035\031\045\032\056\001\002\000\024" +
    "\010\017\013\027\024\021\033\026\034\022\035\024\036" +
    "\020\037\023\040\025\001\002\000\052\002\uffe6\011\uffe6" +
    "\012\051\014\032\015\043\016\047\017\036\020\033\021" +
    "\046\022\037\023\041\024\034\025\042\026\040\027\044" +
    "\030\035\031\045\032\uffe6\033\uffe6\043\uffe6\001\002\000" +
    "\052\002\ufff0\011\ufff0\012\ufff0\014\ufff0\015\ufff0\016\ufff0" +
    "\017\ufff0\020\ufff0\021\ufff0\022\ufff0\023\ufff0\024\ufff0\025" +
    "\ufff0\026\ufff0\027\ufff0\030\ufff0\031\ufff0\032\ufff0\033\ufff0" +
    "\043\ufff0\001\002\000\042\002\uffeb\011\uffeb\012\uffeb\014" +
    "\uffeb\021\uffeb\022\uffeb\023\041\024\034\025\042\026\040" +
    "\027\044\030\035\031\uffeb\032\uffeb\033\uffeb\043\uffeb\001" +
    "\002\000\052\002\ufff2\011\ufff2\012\ufff2\014\ufff2\015\ufff2" +
    "\016\ufff2\017\ufff2\020\ufff2\021\ufff2\022\ufff2\023\ufff2\024" +
    "\ufff2\025\ufff2\026\ufff2\027\ufff2\030\ufff2\031\ufff2\032\ufff2" +
    "\033\ufff2\043\ufff2\001\002\000\052\002\ufff5\011\ufff5\012" +
    "\ufff5\014\ufff5\015\ufff5\016\ufff5\017\ufff5\020\ufff5\021\ufff5" +
    "\022\ufff5\023\ufff5\024\ufff5\025\042\026\040\027\044\030" +
    "\035\031\ufff5\032\ufff5\033\ufff5\043\ufff5\001\002\000\052" +
    "\002\ufff3\011\ufff3\012\ufff3\014\ufff3\015\ufff3\016\ufff3\017" +
    "\ufff3\020\ufff3\021\ufff3\022\ufff3\023\ufff3\024\ufff3\025\ufff3" +
    "\026\ufff3\027\ufff3\030\ufff3\031\ufff3\032\ufff3\033\ufff3\043" +
    "\ufff3\001\002\000\052\002\uffe8\011\uffe8\012\uffe8\014\uffe8" +
    "\015\043\016\047\017\036\020\033\021\uffe8\022\uffe8\023" +
    "\041\024\034\025\042\026\040\027\044\030\035\031\uffe8" +
    "\032\uffe8\033\uffe8\043\uffe8\001\002\000\042\002\uffec\011" +
    "\uffec\012\uffec\014\uffec\021\uffec\022\uffec\023\041\024\034" +
    "\025\042\026\040\027\044\030\035\031\uffec\032\uffec\033" +
    "\uffec\043\uffec\001\002\000\052\002\ufff1\011\ufff1\012\ufff1" +
    "\014\ufff1\015\ufff1\016\ufff1\017\ufff1\020\ufff1\021\ufff1\022" +
    "\ufff1\023\ufff1\024\ufff1\025\ufff1\026\ufff1\027\ufff1\030\ufff1" +
    "\031\ufff1\032\ufff1\033\ufff1\043\ufff1\001\002\000\052\002" +
    "\ufff4\011\ufff4\012\ufff4\014\ufff4\015\ufff4\016\ufff4\017\ufff4" +
    "\020\ufff4\021\ufff4\022\ufff4\023\ufff4\024\ufff4\025\042\026" +
    "\040\027\044\030\035\031\ufff4\032\ufff4\033\ufff4\043\ufff4" +
    "\001\002\000\042\002\uffea\011\uffea\012\uffea\014\uffea\021" +
    "\uffea\022\uffea\023\041\024\034\025\042\026\040\027\044" +
    "\030\035\031\uffea\032\uffea\033\uffea\043\uffea\001\002\000" +
    "\052\002\uffed\011\uffed\012\uffed\014\uffed\015\043\016\047" +
    "\017\036\020\033\021\046\022\037\023\041\024\034\025" +
    "\042\026\040\027\044\030\035\031\uffed\032\uffed\033\uffed" +
    "\043\uffed\001\002\000\052\002\uffef\011\uffef\012\uffef\014" +
    "\uffef\015\uffef\016\uffef\017\uffef\020\uffef\021\uffef\022\uffef" +
    "\023\uffef\024\uffef\025\uffef\026\uffef\027\uffef\030\uffef\031" +
    "\uffef\032\uffef\033\uffef\043\uffef\001\002\000\052\002\ufff6" +
    "\011\ufff6\012\ufff6\014\ufff6\015\ufff6\016\ufff6\017\ufff6\020" +
    "\ufff6\021\ufff6\022\ufff6\023\ufff6\024\ufff6\025\ufff6\026\ufff6" +
    "\027\ufff6\030\ufff6\031\ufff6\032\ufff6\033\ufff6\043\ufff6\001" +
    "\002\000\042\011\076\012\051\014\032\015\043\016\047" +
    "\017\036\020\033\021\046\022\037\023\041\024\034\025" +
    "\042\026\040\027\044\030\035\031\045\001\002\000\052" +
    "\002\uffe5\011\uffe5\012\uffe5\014\uffe5\015\uffe5\016\uffe5\017" +
    "\uffe5\020\uffe5\021\uffe5\022\uffe5\023\uffe5\024\uffe5\025\uffe5" +
    "\026\uffe5\027\uffe5\030\uffe5\031\uffe5\032\uffe5\033\uffe5\043" +
    "\uffe5\001\002\000\024\010\017\013\027\024\021\033\026" +
    "\034\022\035\024\036\020\037\023\040\025\001\002\000" +
    "\046\002\ufff8\012\051\014\032\015\043\016\047\017\036" +
    "\020\033\021\046\022\037\023\041\024\034\025\042\026" +
    "\040\027\044\030\035\031\045\033\ufff8\043\101\001\002" +
    "\000\006\002\ufff7\033\ufff7\001\002\000\006\002\ufffe\033" +
    "\ufffe\001\002\000\006\002\ufffc\033\ufffc\001\002\000\006" +
    "\002\ufffa\033\ufffa\001\002\000\004\002\001\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\103\000\020\002\005\003\010\005\006\006\003\007" +
    "\013\010\012\011\004\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\010\003\101\006\102\007" +
    "\103\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\004\004\027\001\001\000\004\004\074\001\001" +
    "\000\002\001\001\000\004\004\073\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\004\004\072\001\001\000\002\001" +
    "\001\000\002\001\001\000\004\004\071\001\001\000\004" +
    "\004\070\001\001\000\004\004\067\001\001\000\004\004" +
    "\066\001\001\000\004\004\065\001\001\000\004\004\064" +
    "\001\001\000\004\004\063\001\001\000\004\004\062\001" +
    "\001\000\004\004\061\001\001\000\004\004\060\001\001" +
    "\000\004\004\057\001\001\000\004\004\054\001\001\000" +
    "\004\004\053\001\001\000\004\004\052\001\001\000\002" +
    "\001\001\000\004\004\051\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\004" +
    "\004\056\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\004\004\077\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$parser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$parser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$parser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 0;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}


//Codigo visible
    
    public AST ast;
   
    public ArrayList<Errores>listaerrores=new ArrayList<>();
    public void syntax_error(Symbol s){
        Errores nuevo=new Errores(TipoError.SINTACTICO,"Error R de sintaxis "+s.value,s.left+1,s.right+1);
        listaerrores.add(nuevo);
        System.out.println("Error R de sintaxis: "+ s.value +" Linea "+(s.left+1)+" columna "+(s.right+1) );
       
    }
    public void unrecovered_syntax_error(Symbol s) throws java.lang.Exception{ 
        Errores nuevo=new Errores(TipoError.SINTACTICO,"Error NR de sintaxis "+s.value,s.left+1,s.right+1);
        //Errores nuevo=new Errores(s.left+1,s.right+1,"Error NR de sintaxis "+s.value,Errores.Terror.SINTACTICO);
        listaerrores.add(nuevo);
        System.out.println("Error NR de sintaxis: "+ s.value +" Linea "+(s.left+1)+" columna "+(s.right+1) );
    }


/** Cup generated class to encapsulate user supplied action code.*/
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
class CUP$parser$actions {

//Codigo de usuario


  private final parser parser;

  /** Constructor */
  CUP$parser$actions(parser parser) {
    this.parser = parser;
  }

  /** Method 0 with the actual generated action code for actions 0 to 300. */
  public final java_cup.runtime.Symbol CUP$parser$do_action_part00000000(
    int                        CUP$parser$act_num,
    java_cup.runtime.lr_parser CUP$parser$parser,
    java.util.Stack            CUP$parser$stack,
    int                        CUP$parser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$parser$result;

      /* select the action based on the action number */
      switch (CUP$parser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // $START ::= INICIO EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		String start_val = (String)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		RESULT = start_val;
              CUP$parser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$parser$parser.done_parsing();
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // INICIO ::= CUERPO 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("INICIO",0, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // CUERPO ::= ASIGNACION 
            {
              LinkedList<Nodo> RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("CUERPO",3, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // CUERPO ::= CUERPO ASIGNACION 
            {
              LinkedList<Nodo> RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("CUERPO",3, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // CUERPO ::= CONTROLES 
            {
              LinkedList<Nodo> RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("CUERPO",3, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // CUERPO ::= CUERPO CONTROLES 
            {
              LinkedList<Nodo> RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("CUERPO",3, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // CUERPO ::= BLOQUE 
            {
              LinkedList<Nodo> RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("CUERPO",3, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // CUERPO ::= CUERPO BLOQUE 
            {
              LinkedList<Nodo> RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("CUERPO",3, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // CUERPO ::= FUNCIONES 
            {
              LinkedList<Nodo> RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("CUERPO",3, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // ASIGNACION ::= ID IGUAL EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("ASIGNACION",1, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // ASIGNACION ::= ID IGUAL EXP PYCOMA 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("ASIGNACION",1, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // EXP ::= MENOS EXP 
            {
              String RESULT =null;
		 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // EXP ::= EXP MAS EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // EXP ::= EXP MENOS EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // EXP ::= EXP POR EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // EXP ::= EXP DIV EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // EXP ::= EXP POTENCIA EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // EXP ::= EXP MODULO EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // EXP ::= NOT EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // EXP ::= EXP OR EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // EXP ::= EXP AND EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // EXP ::= EXP MENOR EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // EXP ::= EXP MAYOR EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // EXP ::= EXP MENOR_I EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // EXP ::= EXP MAYOR_I EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 25: // EXP ::= EXP IGUAL_IGUAL EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 26: // EXP ::= EXP DISTINTO EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 27: // EXP ::= EXP PREGUNTA EXP DOSPUNTOS EXP 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-4)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 28: // EXP ::= PAR_A EXP PAR_C 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 29: // EXP ::= ID 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 30: // EXP ::= NUMERIC 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 31: // EXP ::= STRING 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 32: // EXP ::= INTEGER 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 33: // EXP ::= TRUE 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 34: // EXP ::= FALSE 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("EXP",2, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 35: // FUNCIONES ::= IMPRIMIR 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("FUNCIONES",7, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 36: // FUNCIONES ::= IMPRIMIR PYCOMA 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("FUNCIONES",7, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 37: // IMPRIMIR ::= PRINT PAR_A EXP PAR_C 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("IMPRIMIR",6, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 38: // IMPRIMIR ::= PRINT PAR_A PAR_C 
            {
              String RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("IMPRIMIR",6, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number "+CUP$parser$act_num+"found in internal parse table");

        }
    } /* end of method */

  /** Method splitting the generated action code into several parts. */
  public final java_cup.runtime.Symbol CUP$parser$do_action(
    int                        CUP$parser$act_num,
    java_cup.runtime.lr_parser CUP$parser$parser,
    java.util.Stack            CUP$parser$stack,
    int                        CUP$parser$top)
    throws java.lang.Exception
    {
              return CUP$parser$do_action_part00000000(
                               CUP$parser$act_num,
                               CUP$parser$parser,
                               CUP$parser$stack,
                               CUP$parser$top);
    }
}

}
