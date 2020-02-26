/* The following code was generated by JFlex 1.4.1 on 2/25/20 10:41 PM */

package AnalizadorA;
import Reportes.Errores;
import java_cup.runtime.*;
import java.util.ArrayList;
import Reportes.Errores.TipoError;

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.1
 * on 2/25/20 10:41 PM from the specification file
 * <tt>src/AnalizadorA/Lexico.jflex</tt>
 */
public class scanner implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = {
     0,  0,  0,  0,  0,  0,  0,  0,  0, 47, 43,  0, 47, 44,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
    47, 15, 41, 42,  0, 14, 18,  0,  1,  2, 11,  9,  0, 10, 40, 12, 
    39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 21,  8, 17,  7, 16, 20, 
     0, 30, 36, 34, 38, 28, 29, 46, 35, 24, 46, 37, 31, 46, 25, 46, 
    22, 46, 23, 32, 26, 27, 46, 33, 46, 46, 46,  3,  0,  4, 13, 45, 
     0, 30, 36, 34, 38, 28, 29, 46, 35, 24, 46, 37, 31, 46, 25, 46, 
    22, 46, 23, 32, 26, 27, 46, 33, 46, 46, 46,  5, 19,  6,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0, 46,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0, 46,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0
  };

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\1"+
    "\1\17\1\20\1\21\1\22\1\23\1\24\1\25\13\26"+
    "\1\27\2\1\2\30\1\31\1\32\1\33\1\34\1\35"+
    "\1\26\1\36\10\26\2\0\1\37\3\30\11\26\1\40"+
    "\3\30\1\26\1\41\1\42\1\43\2\26\1\44\2\26"+
    "\2\0\1\45\1\46\1\26\1\47\1\26\1\50\1\26"+
    "\1\51";

  private static int [] zzUnpackAction() {
    int [] result = new int[92];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\60\0\60\0\60\0\60\0\60\0\60\0\60"+
    "\0\140\0\60\0\60\0\60\0\60\0\60\0\60\0\220"+
    "\0\300\0\360\0\u0120\0\60\0\60\0\60\0\60\0\u0150"+
    "\0\u0180\0\u01b0\0\u01e0\0\u0210\0\u0240\0\u0270\0\u02a0\0\u02d0"+
    "\0\u0300\0\u0330\0\u0360\0\u0390\0\u03c0\0\u03f0\0\60\0\60"+
    "\0\60\0\60\0\60\0\60\0\u0420\0\u0180\0\u0450\0\u0480"+
    "\0\u04b0\0\u04e0\0\u0510\0\u0540\0\u0570\0\u05a0\0\u05d0\0\u03c0"+
    "\0\60\0\u0600\0\u0630\0\u0660\0\u0690\0\u06c0\0\u06f0\0\u0720"+
    "\0\u0750\0\u0780\0\u07b0\0\u07e0\0\u0810\0\u05d0\0\u0840\0\u0870"+
    "\0\u08a0\0\u08d0\0\u0180\0\u0180\0\u0180\0\u0900\0\u0930\0\u0180"+
    "\0\u0960\0\u0990\0\u0870\0\u09c0\0\u0180\0\u0180\0\u09f0\0\u0180"+
    "\0\u0a20\0\u0180\0\u0a50\0\u0180";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[92];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11"+
    "\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21"+
    "\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31"+
    "\1\32\1\33\1\34\1\31\1\35\1\36\2\31\1\37"+
    "\1\31\1\40\1\31\1\41\1\31\1\42\1\43\1\44"+
    "\1\45\1\46\2\47\2\31\1\47\67\0\1\50\66\0"+
    "\1\51\50\0\1\52\57\0\1\53\57\0\1\54\76\0"+
    "\1\31\1\55\21\31\4\0\2\31\27\0\23\31\4\0"+
    "\2\31\27\0\7\31\1\56\13\31\4\0\2\31\27\0"+
    "\5\31\1\57\15\31\4\0\2\31\27\0\1\31\1\60"+
    "\21\31\4\0\2\31\27\0\11\31\1\61\11\31\4\0"+
    "\2\31\27\0\10\31\1\62\12\31\4\0\2\31\27\0"+
    "\13\31\1\63\7\31\4\0\2\31\27\0\10\31\1\64"+
    "\12\31\4\0\2\31\27\0\1\31\1\65\21\31\4\0"+
    "\2\31\27\0\6\31\1\66\14\31\4\0\2\31\50\0"+
    "\1\43\1\67\35\0\21\31\7\0\1\31\1\0\51\70"+
    "\1\71\6\70\13\72\1\73\37\72\1\47\1\74\3\72"+
    "\26\0\2\31\1\75\20\31\4\0\2\31\27\0\11\31"+
    "\1\76\11\31\4\0\2\31\27\0\5\31\1\77\15\31"+
    "\4\0\2\31\27\0\12\31\1\100\10\31\4\0\2\31"+
    "\27\0\11\31\1\101\11\31\4\0\2\31\27\0\2\31"+
    "\1\102\20\31\4\0\2\31\27\0\12\31\1\103\10\31"+
    "\4\0\2\31\27\0\6\31\1\104\14\31\4\0\2\31"+
    "\27\0\7\31\1\105\13\31\4\0\2\31\50\0\1\106"+
    "\10\0\53\72\1\47\1\74\3\72\13\73\1\107\37\73"+
    "\1\110\1\111\3\73\53\0\1\47\32\0\3\31\1\112"+
    "\17\31\4\0\2\31\27\0\11\31\1\113\11\31\4\0"+
    "\2\31\27\0\6\31\1\114\14\31\4\0\2\31\27\0"+
    "\6\31\1\115\14\31\4\0\2\31\27\0\12\31\1\116"+
    "\10\31\4\0\2\31\27\0\4\31\1\117\16\31\4\0"+
    "\2\31\27\0\6\31\1\120\14\31\4\0\2\31\27\0"+
    "\10\31\1\121\12\31\4\0\2\31\27\0\10\31\1\122"+
    "\12\31\4\0\2\31\1\0\13\73\1\107\36\73\1\72"+
    "\1\110\1\111\3\73\13\123\1\124\57\123\1\124\37\123"+
    "\1\110\4\123\26\0\4\31\1\125\16\31\4\0\2\31"+
    "\27\0\6\31\1\126\14\31\4\0\2\31\27\0\14\31"+
    "\1\127\6\31\4\0\2\31\27\0\17\31\1\130\3\31"+
    "\4\0\2\31\27\0\5\31\1\131\15\31\4\0\2\31"+
    "\1\0\13\123\1\124\36\123\1\47\5\123\26\0\15\31"+
    "\1\132\5\31\4\0\2\31\27\0\11\31\1\133\11\31"+
    "\4\0\2\31\27\0\4\31\1\134\16\31\4\0\2\31"+
    "\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[2688];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\7\11\1\1\6\11\4\1\4\11\17\1\6\11"+
    "\12\1\2\0\1\11\31\1\2\0\10\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[92];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
	public ArrayList<Errores>listaerrores=new ArrayList<>();;


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public scanner(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public scanner(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzPushbackPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead < 0) {
      return true;
    }
    else {
      zzEndRead+= numRead;
      return false;
    }
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = zzPushbackPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = zzLexicalState;


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 27: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.DISTINTO,yyline,yycolumn,yytext());
          }
        case 42: break;
        case 21: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.DOSPUNTOS,yyline,yycolumn,yytext());
          }
        case 43: break;
        case 41: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.DEFAULT,yyline,yycolumn,yytext());
          }
        case 44: break;
        case 26: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.MODULO,yyline,yycolumn,yytext());
          }
        case 45: break;
        case 22: 
          { System.out.println("Token ID "+yytext()+" reconocido"); return new Symbol(sym.ID,yyline,yycolumn,yytext());
          }
        case 46: break;
        case 3: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.PAR_C,yyline,yycolumn,yytext());
          }
        case 47: break;
        case 1: 
          { Errores nuevo=new Errores(TipoError.LEXICO,"Error con el token: "+yytext(),yyline+1,yycolumn+1);
        listaerrores.add(nuevo);
        String errLex = "Error léxico : '"+yytext()+"' en la línea: "+(yyline+1)+" y columna: "+(yycolumn+1);
        System.out.println(errLex);
          }
        case 48: break;
        case 25: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.IGUAL_IGUAL,yyline,yycolumn,yytext());
          }
        case 49: break;
        case 13: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.DIV,yyline,yycolumn,yytext());
          }
        case 50: break;
        case 14: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.POTENCIA,yyline,yycolumn,yytext());
          }
        case 51: break;
        case 5: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.COR_C,yyline,yycolumn,yytext());
          }
        case 52: break;
        case 9: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.PYCOMA,yyline,yycolumn,yytext());
          }
        case 53: break;
        case 34: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.TRUE,yyline,yycolumn,yytext());
          }
        case 54: break;
        case 20: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.PREGUNTA,yyline,yycolumn,yytext());
          }
        case 55: break;
        case 18: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.AND,yyline,yycolumn,yytext());
          }
        case 56: break;
        case 15: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.NOT,yyline,yycolumn,yytext());
          }
        case 57: break;
        case 35: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.ELSE,yyline,yycolumn,yytext());
          }
        case 58: break;
        case 24: 
          { /*ignorado*/
          }
        case 59: break;
        case 7: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.LLAV_C,yyline,yycolumn,yytext());
          }
        case 60: break;
        case 2: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.PAR_A,yyline,yycolumn,yytext());
          }
        case 61: break;
        case 8: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.IGUAL,yyline,yycolumn,yytext());
          }
        case 62: break;
        case 23: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.INTEGER,yyline,yycolumn,yytext());
          }
        case 63: break;
        case 33: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.NULO,yyline,yycolumn,yytext());
          }
        case 64: break;
        case 17: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.MENOR,yyline,yycolumn,yytext());
          }
        case 65: break;
        case 12: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.POR,yyline,yycolumn,yytext());
          }
        case 66: break;
        case 37: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.PRINT,yyline,yycolumn,yytext());
          }
        case 67: break;
        case 19: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.OR,yyline,yycolumn,yytext());
          }
        case 68: break;
        case 28: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.MAYOR_I,yyline,yycolumn,yytext());
          }
        case 69: break;
        case 36: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.CASE,yyline,yycolumn,yytext());
          }
        case 70: break;
        case 29: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.MENOR_I,yyline,yycolumn,yytext());
          }
        case 71: break;
        case 38: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.FALSE,yyline,yycolumn,yytext());
          }
        case 72: break;
        case 4: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.COR_A,yyline,yycolumn,yytext());
          }
        case 73: break;
        case 39: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.BREAK,yyline,yycolumn,yytext());
          }
        case 74: break;
        case 31: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.STRING,yyline,yycolumn,yytext());
          }
        case 75: break;
        case 40: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.SWITCH,yyline,yycolumn,yytext());
          }
        case 76: break;
        case 10: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.MAS,yyline,yycolumn,yytext());
          }
        case 77: break;
        case 16: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.MAYOR,yyline,yycolumn,yytext());
          }
        case 78: break;
        case 30: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.IF,yyline,yycolumn,yytext());
          }
        case 79: break;
        case 6: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.LLAV_A,yyline,yycolumn,yytext());
          }
        case 80: break;
        case 11: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.MENOS,yyline,yycolumn,yytext());
          }
        case 81: break;
        case 32: 
          { System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.NUMERIC,yyline,yycolumn,yytext());
          }
        case 82: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return new java_cup.runtime.Symbol(sym.EOF); }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
