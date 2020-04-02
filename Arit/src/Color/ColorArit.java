/* The following code was generated by JFlex 1.4.1 on 2/25/20 6:19 PM */


package Color;

import java.io.*;   
import javax.swing.text.Segment;   
   
import org.fife.ui.rsyntaxtextarea.*;   
   

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.1
 * on 2/25/20 6:19 PM from the specification file
 * <tt>ColorArit.jflex</tt>
 */
public class ColorArit extends AbstractJFlexCTokenMaker {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int MLC = 1;

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\6\1\7\1\0\1\6\23\0\1\6\1\43\1\4\1\10"+
    "\1\0\1\44\1\43\1\3\2\13\1\11\1\43\1\0\1\43\1\12"+
    "\1\43\12\2\1\43\1\0\1\13\1\43\1\13\1\43\1\0\1\21"+
    "\1\30\1\26\1\32\1\17\1\20\1\41\1\27\1\24\1\1\1\31"+
    "\1\22\1\35\1\33\1\34\1\40\1\1\1\15\1\23\1\14\1\16"+
    "\1\42\1\25\1\36\1\37\1\1\1\13\1\5\1\13\1\43\1\1"+
    "\1\0\1\21\1\30\1\26\1\32\1\17\1\20\1\41\1\27\1\24"+
    "\1\1\1\31\1\22\1\35\1\33\1\34\1\40\1\1\1\15\1\23"+
    "\1\14\1\16\1\42\1\25\1\36\1\37\1\1\1\13\1\43\1\13"+
    "\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\1\1\0\2\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\11\2\1\13\7\2\1\14\1\15"+
    "\1\14\1\0\1\4\1\16\1\4\1\0\1\17\2\5"+
    "\1\10\1\20\15\2\1\1\16\2\1\21\1\3\1\22"+
    "\1\23\32\2\1\13\12\2\1\24\33\2";

  private static int [] zzUnpackAction() {
    int [] result = new int[139];
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
    "\0\0\0\45\0\112\0\157\0\224\0\271\0\336\0\u0103"+
    "\0\112\0\u0128\0\112\0\112\0\u014d\0\u0172\0\u0197\0\u01bc"+
    "\0\u01e1\0\u0206\0\u022b\0\u0250\0\u0275\0\u029a\0\u02bf\0\u02e4"+
    "\0\u0309\0\u032e\0\u0353\0\u0378\0\u039d\0\u03c2\0\112\0\u03e7"+
    "\0\u040c\0\u0431\0\112\0\u0456\0\u047b\0\112\0\u04a0\0\u04c5"+
    "\0\u04ea\0\u04ea\0\u050f\0\u0534\0\u0559\0\u057e\0\u05a3\0\u05c8"+
    "\0\u05ed\0\u0612\0\u0637\0\u065c\0\u0681\0\u06a6\0\u06cb\0\157"+
    "\0\u06f0\0\u0715\0\u073a\0\u075f\0\u0784\0\u07a9\0\u07ce\0\u07f3"+
    "\0\u0818\0\u083d\0\u0862\0\u0887\0\u08ac\0\u08d1\0\112\0\u040c"+
    "\0\112\0\112\0\u08f6\0\u091b\0\u0940\0\u0965\0\u098a\0\u09af"+
    "\0\u09d4\0\u09f9\0\u0a1e\0\u0a43\0\u0a68\0\u0a8d\0\u0ab2\0\u0ad7"+
    "\0\u0afc\0\u0b21\0\u0b46\0\u0b6b\0\u0b90\0\u0bb5\0\u0bda\0\u0bff"+
    "\0\u0c24\0\u0c49\0\u0c6e\0\u0c93\0\157\0\u0cb8\0\u0cdd\0\u0d02"+
    "\0\u0d27\0\u0d4c\0\u0d71\0\u0d96\0\u0dbb\0\u0de0\0\u0e05\0\157"+
    "\0\u0e2a\0\u0e4f\0\u0e74\0\u0e99\0\u0ebe\0\u0ee3\0\u0f08\0\u0f2d"+
    "\0\u0f52\0\u0f77\0\u0f9c\0\u0fc1\0\u0fe6\0\u100b\0\u1030\0\u1055"+
    "\0\u107a\0\u109f\0\u10c4\0\u10e9\0\u110e\0\u1133\0\u1158\0\u117d"+
    "\0\u11a2\0\u11c7\0\u11ec";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[139];
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
    "\1\3\1\4\1\5\1\6\1\7\1\3\1\10\1\11"+
    "\1\12\1\13\1\3\1\14\1\15\1\16\1\4\1\17"+
    "\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27"+
    "\1\30\1\4\1\31\1\32\1\4\1\33\2\4\1\34"+
    "\2\4\1\13\1\35\7\36\1\37\1\36\1\40\33\36"+
    "\46\0\2\4\11\0\27\4\4\0\1\5\7\0\1\41"+
    "\32\0\3\42\1\43\1\42\1\44\1\42\1\45\35\42"+
    "\4\7\1\46\1\47\1\7\1\50\35\7\6\0\1\10"+
    "\36\0\7\51\1\0\1\51\1\52\33\51\1\0\2\4"+
    "\11\0\1\4\1\53\16\4\1\54\2\4\1\55\3\4"+
    "\3\0\2\4\11\0\3\4\1\56\14\4\1\57\6\4"+
    "\3\0\2\4\11\0\6\4\1\60\20\4\3\0\2\4"+
    "\11\0\2\4\1\61\2\4\1\17\12\4\1\62\6\4"+
    "\3\0\2\4\11\0\1\4\1\63\25\4\3\0\2\4"+
    "\11\0\3\4\1\64\4\4\1\65\16\4\3\0\2\4"+
    "\11\0\1\66\10\4\1\67\15\4\3\0\2\4\11\0"+
    "\4\4\1\70\22\4\3\0\2\4\11\0\13\4\1\71"+
    "\13\4\3\0\2\4\11\0\5\4\1\60\12\4\1\72"+
    "\6\4\3\0\2\4\11\0\10\4\1\73\16\4\3\0"+
    "\2\4\11\0\1\4\1\74\3\4\1\75\21\4\3\0"+
    "\2\4\11\0\3\4\1\76\14\4\1\70\6\4\3\0"+
    "\2\4\11\0\1\4\1\77\10\4\1\100\14\4\3\0"+
    "\2\4\11\0\3\4\1\101\1\4\1\102\12\4\1\103"+
    "\6\4\3\0\2\4\11\0\1\4\1\104\4\4\1\105"+
    "\1\4\1\106\16\4\46\0\1\13\7\36\1\0\1\36"+
    "\1\0\33\36\10\0\1\107\36\0\1\110\42\0\3\44"+
    "\1\111\3\44\1\0\40\44\1\43\3\44\1\0\35\44"+
    "\3\0\1\111\41\0\7\50\1\0\41\50\1\112\1\47"+
    "\37\50\7\51\1\0\35\51\1\0\2\4\11\0\2\4"+
    "\1\113\24\4\3\0\2\4\11\0\2\4\1\114\3\4"+
    "\1\115\20\4\3\0\2\4\11\0\24\4\1\116\2\4"+
    "\3\0\2\4\11\0\1\117\20\4\1\120\5\4\3\0"+
    "\2\4\11\0\2\4\1\121\24\4\3\0\2\4\11\0"+
    "\7\4\1\122\17\4\3\0\2\4\11\0\17\4\1\123"+
    "\7\4\3\0\2\4\11\0\1\4\1\70\25\4\3\0"+
    "\2\4\11\0\1\4\1\124\25\4\3\0\2\4\11\0"+
    "\17\4\1\125\7\4\3\0\2\4\11\0\7\4\1\126"+
    "\17\4\3\0\2\4\11\0\1\4\1\127\25\4\3\0"+
    "\2\4\11\0\10\4\1\130\16\4\3\0\2\4\11\0"+
    "\10\4\1\131\16\4\3\0\2\4\11\0\17\4\1\132"+
    "\7\4\3\0\2\4\11\0\7\4\1\133\17\4\3\0"+
    "\2\4\11\0\3\4\1\134\23\4\3\0\2\4\11\0"+
    "\1\4\1\135\25\4\3\0\2\4\11\0\4\4\1\136"+
    "\22\4\3\0\2\4\11\0\20\4\1\137\6\4\3\0"+
    "\2\4\11\0\20\4\1\140\6\4\3\0\2\4\11\0"+
    "\5\4\1\141\10\4\1\142\10\4\3\0\2\4\11\0"+
    "\1\143\26\4\3\0\2\4\11\0\16\4\1\106\10\4"+
    "\3\0\2\4\11\0\10\4\1\144\16\4\3\0\2\4"+
    "\11\0\20\4\1\133\6\4\3\0\2\4\11\0\3\4"+
    "\1\145\23\4\3\0\2\4\11\0\3\4\1\70\13\4"+
    "\1\146\7\4\3\0\2\4\11\0\24\4\1\147\2\4"+
    "\3\0\2\4\11\0\20\4\1\150\6\4\3\0\2\4"+
    "\11\0\3\4\1\151\23\4\3\0\2\4\11\0\2\4"+
    "\1\152\24\4\3\0\2\4\11\0\20\4\1\153\6\4"+
    "\3\0\2\4\11\0\17\4\1\154\7\4\3\0\2\4"+
    "\11\0\3\4\1\70\23\4\3\0\2\4\11\0\12\4"+
    "\1\155\14\4\3\0\2\4\11\0\5\4\1\156\21\4"+
    "\3\0\2\4\11\0\25\4\1\157\1\4\3\0\2\4"+
    "\11\0\1\160\26\4\3\0\2\4\11\0\10\4\1\161"+
    "\16\4\3\0\2\4\11\0\1\162\26\4\3\0\2\4"+
    "\11\0\6\4\1\122\20\4\3\0\2\4\11\0\1\163"+
    "\26\4\3\0\2\4\11\0\1\145\26\4\3\0\2\4"+
    "\11\0\5\4\1\164\21\4\3\0\2\4\11\0\24\4"+
    "\1\165\2\4\3\0\2\4\11\0\5\4\1\166\21\4"+
    "\3\0\2\4\11\0\11\4\1\145\15\4\3\0\2\4"+
    "\11\0\6\4\1\145\20\4\3\0\2\4\11\0\17\4"+
    "\1\145\7\4\3\0\2\4\11\0\10\4\1\167\16\4"+
    "\3\0\2\4\11\0\1\4\1\170\25\4\3\0\2\4"+
    "\11\0\17\4\1\133\7\4\3\0\2\4\11\0\15\4"+
    "\1\145\11\4\3\0\2\4\11\0\24\4\1\171\2\4"+
    "\3\0\2\4\11\0\11\4\1\171\15\4\3\0\2\4"+
    "\11\0\20\4\1\172\6\4\3\0\2\4\11\0\1\4"+
    "\1\173\25\4\3\0\2\4\11\0\26\4\1\106\3\0"+
    "\2\4\11\0\16\4\1\145\10\4\3\0\2\4\11\0"+
    "\1\174\26\4\3\0\2\4\11\0\23\4\1\160\3\4"+
    "\3\0\2\4\11\0\1\175\26\4\3\0\2\4\11\0"+
    "\17\4\1\176\7\4\3\0\2\4\11\0\12\4\1\177"+
    "\14\4\3\0\2\4\11\0\10\4\1\200\16\4\3\0"+
    "\2\4\11\0\15\4\1\70\11\4\3\0\2\4\11\0"+
    "\6\4\1\105\20\4\3\0\2\4\11\0\2\4\1\201"+
    "\24\4\3\0\2\4\11\0\5\4\1\141\21\4\3\0"+
    "\2\4\11\0\10\4\1\202\16\4\3\0\2\4\11\0"+
    "\3\4\1\203\23\4\3\0\2\4\11\0\4\4\1\145"+
    "\22\4\3\0\2\4\11\0\17\4\1\70\7\4\3\0"+
    "\2\4\11\0\10\4\1\204\16\4\3\0\2\4\11\0"+
    "\13\4\1\145\13\4\3\0\2\4\11\0\25\4\1\205"+
    "\1\4\3\0\2\4\11\0\13\4\1\70\13\4\3\0"+
    "\2\4\11\0\17\4\1\206\7\4\3\0\2\4\11\0"+
    "\6\4\1\207\20\4\3\0\2\4\11\0\22\4\1\160"+
    "\4\4\3\0\2\4\11\0\1\4\1\210\25\4\3\0"+
    "\2\4\11\0\20\4\1\173\6\4\3\0\2\4\11\0"+
    "\6\4\1\211\20\4\3\0\2\4\11\0\2\4\1\122"+
    "\24\4\3\0\2\4\11\0\1\70\26\4\3\0\2\4"+
    "\11\0\12\4\1\212\14\4\3\0\2\4\11\0\3\4"+
    "\1\64\23\4\3\0\2\4\11\0\5\4\1\213\21\4"+
    "\3\0\2\4\11\0\7\4\1\106\17\4\2\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4625];
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
    "\1\1\1\0\1\11\5\1\1\11\1\1\2\11\22\1"+
    "\1\11\1\1\1\0\1\1\1\11\1\1\1\0\1\11"+
    "\40\1\1\11\1\1\2\11\101\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[139];
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

  /* user code: */
   
   /**   
    * Constructor.  This must be here because JFlex does not generate a   
    * no-parameter constructor.   
    */   
   public ColorArit() {  }
 
      
   
   /**   
    * Adds the token specified to the current linked list of tokens.   
    *   
    * @param tokenType The token's type.   
    * @see #addToken(int, int, int)   
    */   
   private void addHyperlinkToken(int start, int end, int tokenType) {   
      int so = start + offsetShift;   
      addToken(zzBuffer, start,end, tokenType, so, true);   
   }   
   
   /**   
    * Adds the token specified to the current linked list of tokens.   
    *   
    * @param tokenType The token's type.   
    */   
   private void addToken(int tokenType) {   
      addToken(zzStartRead, zzMarkedPos-1, tokenType);   
   }   
   
   /**   
    * Adds the token specified to the current linked list of tokens.   
    *   
    * @param tokenType The token's type.   
    * @see #addHyperlinkToken(int, int, int)   
    */   
   private void addToken(int start, int end, int tokenType) {   
      int so = start + offsetShift;   
      addToken(zzBuffer, start,end, tokenType, so, false);   
   }   
   
   /**   
    * Adds the token specified to the current linked list of tokens.   
    *   
    * @param array The character array.   
    * @param start The starting offset in the array.   
    * @param end The ending offset in the array.   
    * @param tokenType The token's type.   
    * @param startOffset The offset in the document at which this token   
    *        occurs.   
    * @param hyperlink Whether this token is a hyperlink.   
    */   
   public void addToken(char[] array, int start, int end, int tokenType,   
                  int startOffset, boolean hyperlink) {   
      super.addToken(array, start,end, tokenType, startOffset, hyperlink);   
      zzStartRead = zzMarkedPos;   
   }   
   
   /**   
    * Returns the text to place at the beginning and end of a   
    * line to "comment" it in a this programming language.   
    *   
    * @return The start and end strings to add to a line to "comment"   
    *         it out.   
    */   
   public String[] getLineCommentStartAndEnd() {   
      return new String[] { "//", null };   
   }   
   
   /**   
    * Returns the first token in the linked list of tokens generated   
    * from <code>text</code>.  This method must be implemented by   
    * subclasses so they can correctly implement syntax highlighting.   
    *   
    * @param text The text from which to get tokens.   
    * @param initialTokenType The token type we should start with.   
    * @param startOffset The offset into the document at which   
    *        <code>text</code> starts.   
    * @return The first <code>Token</code> in a linked list representing   
    *         the syntax highlighted text.   
    */   
   public Token getTokenList(Segment text, int initialTokenType, int startOffset) {   
   
      resetTokenList();   
      this.offsetShift = -text.offset + startOffset;   
   
      // Start off in the proper state.   
      int state = Token.NULL;   
      switch (initialTokenType) {   
                  case Token.COMMENT_MULTILINE:   
            state = MLC;   
            start = text.offset;   
            break;   
   
         /* No documentation comments */   
         default:   
            state = Token.NULL;   
      }   
   
      s = text;   
      try {   
         yyreset(zzReader);   
         yybegin(state);   
         return yylex();   
      } catch (IOException ioe) {   
         ioe.printStackTrace();   
         return new TokenImpl();   
      }   
   
   }   
   
   /**   
    * Refills the input buffer.   
    *   
    * @return      <code>true</code> if EOF was reached, otherwise   
    *              <code>false</code>.   
    */   
   private boolean zzRefill() {   
//este se queda
      return zzCurrentPos>=s.offset+s.count;   
   }   
   

   
   /**   
    * Resets the scanner to read from a new input stream.   
    * Does not close the old reader.   
    *   
    * All internal variables are reset, the old input stream    
    * <b>cannot</b> be reused (internal buffer is discarded and lost).   
    * Lexical state is set to <tt>YY_INITIAL</tt>.   
    *   
    * @param reader   the new input stream    
    */   
   public final void yyreset(Reader reader) {  



//este se queda 
      // 's' has been updated.   
      zzBuffer = s.array;   
      /*   
       * We replaced the line below with the two below it because zzRefill   
       * no longer "refills" the buffer (since the way we do it, it's always   
       * "full" the first time through, since it points to the segment's   
       * array).  So, we assign zzEndRead here.   
       */   
      //zzStartRead = zzEndRead = s.offset;   
      zzStartRead = s.offset;   
      zzEndRead = zzStartRead + s.count - 1;   
      zzCurrentPos = zzMarkedPos = zzPushbackPos = s.offset;   
      zzLexicalState = YYINITIAL;   
      zzReader = reader;   
      zzAtBOL  = true;   
      zzAtEOF  = false;   
   }   
   


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public ColorArit(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public ColorArit(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 182) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  

    
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
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public org.fife.ui.rsyntaxtextarea.Token yylex() throws java.io.IOException {
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
        case 7: 
          { addNullToken(); return firstToken;
          }
        case 21: break;
        case 18: 
          { addToken(Token.LITERAL_CHAR);
          }
        case 22: break;
        case 16: 
          { start = zzMarkedPos-2; yybegin(MLC);
          }
        case 23: break;
        case 6: 
          { addToken(Token.WHITESPACE);
          }
        case 24: break;
        case 19: 
          { addToken(Token.ERROR_STRING_DOUBLE);
          }
        case 25: break;
        case 1: 
          { addToken(Token.RESERVED_WORD);
          }
        case 26: break;
        case 10: 
          { addToken(Token.SEPARATOR);
          }
        case 27: break;
        case 2: 
          { addToken(Token.IDENTIFIER);
          }
        case 28: break;
        case 4: 
          { addToken(Token.ERROR_CHAR); addNullToken(); return firstToken;
          }
        case 29: break;
        case 11: 
          { addToken(Token.FUNCTION);
          }
        case 30: break;
        case 5: 
          { addToken(Token.ERROR_STRING_DOUBLE); addNullToken(); return firstToken;
          }
        case 31: break;
        case 20: 
          { addToken(Token.DATA_TYPE);
          }
        case 32: break;
        case 17: 
          { yybegin(YYINITIAL); addToken(start,zzStartRead+2-1, Token.COMMENT_MULTILINE);
          }
        case 33: break;
        case 14: 
          { addToken(Token.ERROR_CHAR);
          }
        case 34: break;
        case 15: 
          { addToken(Token.LITERAL_STRING_DOUBLE_QUOTE);
          }
        case 35: break;
        case 8: 
          { addToken(Token.COMMENT_EOL); addNullToken(); return firstToken;
          }
        case 36: break;
        case 3: 
          { addToken(Token.LITERAL_NUMBER_DECIMAL_INT);
          }
        case 37: break;
        case 9: 
          { addToken(Token.OPERATOR);
          }
        case 38: break;
        case 12: 
          { 
          }
        case 39: break;
        case 13: 
          { addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken;
          }
        case 40: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            switch (zzLexicalState) {
            case YYINITIAL: {
              addNullToken(); return firstToken;
            }
            case 140: break;
            case MLC: {
              addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken;
            }
            case 141: break;
            default:
            return null;
            }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
