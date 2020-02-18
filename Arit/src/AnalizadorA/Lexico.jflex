package AnalizadorA;
import java_cup.runtime.*;
%%
%cup
%class scanner
%public
%line
%char
%column
%full
%ignorecase
%{
//public ArrayList<Errores>listaerrores=new ArrayList<>();;
%}
/*
░██████╗██╗███╗░░░███╗██████╗░░█████╗░██╗░░░░░░█████╗░░██████╗
██╔════╝██║████╗░████║██╔══██╗██╔══██╗██║░░░░░██╔══██╗██╔════╝
╚█████╗░██║██╔████╔██║██████╦╝██║░░██║██║░░░░░██║░░██║╚█████╗░
░╚═══██╗██║██║╚██╔╝██║██╔══██╗██║░░██║██║░░░░░██║░░██║░╚═══██╗
██████╔╝██║██║░╚═╝░██║██████╦╝╚█████╔╝███████╗╚█████╔╝██████╔╝
╚═════╝░╚═╝╚═╝░░░░░╚═╝╚═════╝░░╚════╝░╚══════╝░╚════╝░╚═════╝░
*/
PAR_A="("
PAR_C=")"
COR_A="["
COR_C="]"
LLAV_A="{"
LLAV_C="}"
IGUAL="="
PYCOMA=";"
MAS="+"
MENOS="-"
POR="*"
DIV="/"
POTENCIA="^"
MODULO="%%"
IGUAL_IGUAL="=="
DISTINTO="!="
MAYOR_I=">="
MENOR_I="<="
MAYOR=">"
MENOR="<"
AND="&"
OR="|"
NOT="!"

/*
████████╗██╗██████╗░░█████╗░░██████╗
╚══██╔══╝██║██╔══██╗██╔══██╗██╔════╝
░░░██║░░░██║██████╔╝██║░░██║╚█████╗░
░░░██║░░░██║██╔═══╝░██║░░██║░╚═══██╗
░░░██║░░░██║██║░░░░░╚█████╔╝██████╔╝
░░░╚═╝░░░╚═╝╚═╝░░░░░░╚════╝░╚═════╝░
*/
NUMERIC=[0-9]+ "." [0-9]+
INTEGER=[0-9]+
TRUE="true"
FALSE="false"
STRING=\"([^\"])*\"
/*
░█████╗░░█████╗░███╗░░░███╗███████╗███╗░░██╗████████╗░█████╗░██████╗░██╗░█████╗░░██████╗
██╔══██╗██╔══██╗████╗░████║██╔════╝████╗░██║╚══██╔══╝██╔══██╗██╔══██╗██║██╔══██╗██╔════╝
██║░░╚═╝██║░░██║██╔████╔██║█████╗░░██╔██╗██║░░░██║░░░███████║██████╔╝██║██║░░██║╚█████╗░
██║░░██╗██║░░██║██║╚██╔╝██║██╔══╝░░██║╚████║░░░██║░░░██╔══██║██╔══██╗██║██║░░██║░╚═══██╗
╚█████╔╝╚█████╔╝██║░╚═╝░██║███████╗██║░╚███║░░░██║░░░██║░░██║██║░░██║██║╚█████╔╝██████╔╝
░╚════╝░░╚════╝░╚═╝░░░░░╚═╝╚══════╝╚═╝░░╚══╝░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚═╝╚═╝░╚════╝░╚═════╝░
*/


COMENTARIO1= "#*" ([^*])* ~"*#"
COMENTARIO2= "#"[^\r\n]* (\r|\n|\r\n)?
ID=[A-Za-zñÑ_][_0-9A-Za-zñÑ]* | "." [A-Za-zñÑ][_.0-9A-Za-zñÑ]*
SPACE=[\ \r\t\f\t]
ENTER=[\ \n]

%%
<YYINITIAL>{PAR_A}         		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.PAR_A,yyline,yycolumn,yytext());}
<YYINITIAL>{PAR_C}         		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.PAR_C,yyline,yycolumn,yytext());}
<YYINITIAL>{COR_A}         		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.COR_A,yyline,yycolumn,yytext());}
<YYINITIAL>{COR_C}         		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.COR_C,yyline,yycolumn,yytext());}
<YYINITIAL>{LLAV_A}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.LLAV_A,yyline,yycolumn,yytext());}
<YYINITIAL>{LLAV_C}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.LLAV_C,yyline,yycolumn,yytext());}
<YYINITIAL>{IGUAL}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.IGUAL,yyline,yycolumn,yytext());}
<YYINITIAL>{IGUAL_IGUAL}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.IGUAL_IGUAL,yyline,yycolumn,yytext());}
<YYINITIAL>{MENOR}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.MENOR,yyline,yycolumn,yytext());}
<YYINITIAL>{MAYOR_I}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.MAYOR_I,yyline,yycolumn,yytext());}
<YYINITIAL>{MENOR_I}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.MENOR_I,yyline,yycolumn,yytext());}
<YYINITIAL>{MAS}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.MAS,yyline,yycolumn,yytext());}
<YYINITIAL>{MENOS}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.MENOS,yyline,yycolumn,yytext());}
<YYINITIAL>{POR}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.POR,yyline,yycolumn,yytext());}
<YYINITIAL>{DIV}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.DIV,yyline,yycolumn,yytext());}
<YYINITIAL>{POTENCIA}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.POTENCIA,yyline,yycolumn,yytext());}
<YYINITIAL>{MODULO}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.MODULO,yyline,yycolumn,yytext());}
<YYINITIAL>{DISTINTO}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.DISTINTO,yyline,yycolumn,yytext());}
<YYINITIAL>{NOT}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.NOT,yyline,yycolumn,yytext());}
<YYINITIAL>{AND}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.AND,yyline,yycolumn,yytext());}
<YYINITIAL>{OR}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.OR,yyline,yycolumn,yytext());}

<YYINITIAL>{PYCOMA}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.PYCOMA,yyline,yycolumn,yytext());}
<YYINITIAL>{NUMERIC}        	{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.NUMERIC,yyline,yycolumn,yytext());}
<YYINITIAL>{INTEGER}        	{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.INTEGER,yyline,yycolumn,yytext());}
<YYINITIAL>{TRUE}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.TRUE,yyline,yycolumn,yytext());}
<YYINITIAL>{FALSE}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.FALSE,yyline,yycolumn,yytext());}
<YYINITIAL>{STRING}        		{System.out.println("Token "+yytext()+" reconocido"); return new Symbol(sym.STRING,yyline,yycolumn,yytext());}
<YYINITIAL>{ID}        			{System.out.println("Token ID "+yytext()+" reconocido"); return new Symbol(sym.ID,yyline,yycolumn,yytext());}
<YYINITIAL>{COMENTARIO1}        {/*ignorado*/}
<YYINITIAL>{COMENTARIO2}        {/*ignorado*/}
<YYINITIAL>{SPACE}         		{/*ignorado*/}
<YYINITIAL>{ENTER}         		{/*ignorado*/}
<YYINITIAL> . {
        String errLex = "Error léxico : '"+yytext()+"' en la línea: "+(yyline+1)+" y columna: "+(yycolumn+1);
        System.out.println(errLex);
}