package Analisis;
import java_cup.runtime.Symbol;
import java.util.LinkedList;
import excepciones.Errores;
%% //INICIO SECCION

%class scanner //Nombre de la clase que genera JFlex
%type java_cup.runtime.Symbol
%public 
%line // Para registrar el numero de línea
%column // Para registrar el numero de columna
%char // Llevar un conteo de caracteres
%cup // Habilita la integración con Cup
%full
%ignorecase

//%unicode  Reconocimiento de caracteres unicode

%state COMMENT

%{
   public LinkedList<Errores> listaErrores = new LinkedList<>();
%}

%init{ //Constructor del analizador
    yyline = 1; //empieza el conteo de lineas en 1
    yycolumn=1; //empieza el conteo de columnas en 1
    listaErrores = new LinkedList<>();
%init}

// Expresiones regulares (esto en JFLEX son MACROS)



WHITE = [ \r\t\n]+ //esto ignorara comentarios de una linea que se configura con ignore mas abajo
//L = [a-zA-Z_]+
ENTERO = [0-9]+ //esto acepta enteros negativos y positivos.
DECIMAL = [0-9]+"."[0-9]+
CHAR = \'([^\'\\]|\\.)\'
CADENA = \"(\\.|[^\"\\])*\" 
ID=[a-zA-Z][a-zA-Z0-9]*

%{//Inicio (aqui solo puede ir codigo de java)
    
    StringBuilder string;
%}//Fin

%% //FIN DE SECCION

//Lo siguiente Ignora comentarios de multiples lineas
<YYINITIAL> "/*"  { yybegin(COMMENT); }

<COMMENT> {
  "*/"              { yybegin(YYINITIAL); }
  [^*]+             { /* consume cualquier cosa que no sea * */ }
  "*"               { /* consume * que no esté seguido de / */ }
  <<EOF>>           { return new Symbol(sym.ERROR, yyline, yycolumn, "Comentario sin cerrar"); }
}


"//".*{WHITE} {/*ignore*/} //Ignora comentarios de una linea y .* significa cualquier caracter sin incluir saltos de linea

"//".* {/*ignore*/} //tambien ignora si despues del comentario se termina el archivo



//CADENAS

<YYINITIAL> {CADENA} {
    String cadena = yytext();
    cadena = cadena.substring(1, cadena.length()-1);
    return new Symbol(sym.CADENA, yyline, yycolumn,cadena);
    }

<YYINITIAL> {CHAR} {
    String texto = yytext();       
    char caracter = texto.charAt(1);  
    return new Symbol(sym.CHAR, yyline, yycolumn, caracter);
}


//NOTA: el orden en que esta colocado cada linea de los siguientes tokens es importante

{WHITE}   {}

"print"     {return new Symbol(sym.PRINT,yyline,yycolumn, yytext());}
"var"       {return new Symbol(sym.VAR, yyline, yycolumn,yytext());}
"int"       {return new Symbol(sym.INT, yyline, yycolumn,yytext());}
"double"    {return new Symbol(sym.DOUBLE,yyline,yycolumn, yytext());}
"bool"      {return new Symbol(sym.BOOL,yyline,yycolumn, yytext());}
"char"      {return new Symbol(sym._CHAR,yyline,yycolumn, yytext());}
"string"    {return new Symbol(sym.STRING,yyline,yycolumn, yytext());}
"true"      {return new Symbol(sym.TRUE,yyline,yycolumn, yytext());}
"false"     {return new Symbol(sym.FALSE,yyline,yycolumn, yytext());}
"if"        {return new Symbol(sym._if,yyline,yycolumn, yytext());}
"else"      {return new Symbol(sym._else,yyline,yycolumn, yytext());}
"switch"    {return new Symbol(sym._switch,yyline,yycolumn, yytext());}
"case"      {return new Symbol(sym._case,yyline,yycolumn, yytext());}
"default"   {return new Symbol(sym._default,yyline,yycolumn, yytext());}
"while"     {return new Symbol(sym._while,yyline,yycolumn, yytext());}
"do"        {return new Symbol(sym._do,yyline,yycolumn, yytext());}
"for"       {return new Symbol(sym._for,yyline,yycolumn, yytext());}
"break"     {return new Symbol(sym._break,yyline,yycolumn, yytext());}
"continue"  {return new Symbol(sym._continue,yyline,yycolumn, yytext());}
"return"    {return new Symbol(sym._return,yyline,yycolumn, yytext());}
"List"      {return new Symbol(sym._list, yyline, yycolumn,yytext());}
"new"       {return new Symbol(sym._new, yyline, yycolumn,yytext());}
"start"     {return new Symbol(sym.START, yyline, yycolumn,yytext());}
"void"      {return new Symbol(sym.VOID, yyline, yycolumn,yytext());}
"append"    {return new Symbol(sym._append, yyline, yycolumn,yytext());}
"remove"    {return new Symbol(sym._remove, yyline, yycolumn,yytext());}
"length"    { return new Symbol(sym.LENGTH, yyline, yycolumn); }
"toString"    { return new Symbol(sym.TOSTRING, yyline, yycolumn); }
"round"    { return new Symbol(sym.ROUND, yyline, yycolumn); }
"Find"    { return new Symbol(sym.FIND, yyline, yycolumn); }
"^"         {return new Symbol(sym.XOR,yyline,yycolumn, yytext());}    //nota: es importante que esto este antes de ID
"["         {return new Symbol(sym.corcheteA,yyline,yycolumn, yytext());}
"]"         {return new Symbol(sym.corcheteC,yyline,yycolumn, yytext());}
{ID}        {return new Symbol(sym.ID, yyline, yycolumn,yytext());}
{ENTERO}    {return new Symbol(sym.ENTERO,yyline,yycolumn, yytext());}
{DECIMAL}   {return new Symbol(sym.DECIMAL,yyline,yycolumn, yytext());}

"="  {return new Symbol(sym.IGUAL,yyline,yycolumn, yytext());}
"=="  {return new Symbol(sym.IGUALACION,yyline,yycolumn, yytext());}
"!"  {return new Symbol(sym.EXCLAMACION,yyline,yycolumn, yytext());}
"+"  {return new Symbol(sym.MAS,yyline,yycolumn, yytext());}
"-"  {return new Symbol(sym.MENOS,yyline,yycolumn, yytext());}
"**"  {return new Symbol(sym.POTENCIA,yyline,yycolumn, yytext());}
"*"  {return new Symbol(sym.POR,yyline,yycolumn, yytext());}
"/"  {return new Symbol(sym.DIV,yyline,yycolumn, yytext());}
"%"  {return new Symbol(sym.PORCENTAJE,yyline,yycolumn, yytext());}

"<"  {return new Symbol(sym.MENOR,yyline,yycolumn, yytext());}
">"  {return new Symbol(sym.MAYOR,yyline,yycolumn, yytext());}

"||" {return new Symbol(sym.OR,yyline,yycolumn, yytext());}
"&&" {return new Symbol(sym.AND,yyline,yycolumn, yytext());}


";"  {return new Symbol(sym.PUNTOCOMA,yyline,yycolumn, yytext());}
","  {return new Symbol(sym.COMA,yyline,yycolumn, yytext());}
"("  {return new Symbol(sym.PARABRE,yyline,yycolumn, yytext());}
")"  {return new Symbol(sym.PARCIERRA,yyline,yycolumn, yytext());}


":"  {return new Symbol(sym.DOSPUNTOS,yyline,yycolumn, yytext());}
"."  {return new Symbol(sym.PUNTO,yyline,yycolumn, yytext());}
"{"  {return new Symbol(sym.llaveizq,yyline,yycolumn, yytext());}
"}"  {return new Symbol(sym.llaveder,yyline,yycolumn, yytext());}



//Lo siguiente indica: cualquier caracter que no haya coincidido con las reglas anteriores se ejecuta lo que esta dentro {}
. {
    //System.out.println("Lexical error: "+yytext()+" linea: "+yyline+" columna: "+yycolumn);
    listaErrores.add(new Errores("LEXICO", "El caracter "+yytext()+" No pertenece al lenguaje", yyline, yycolumn));
    return new Symbol(sym.ERROR,yyline,yycolumn, yytext());
}

<<EOF>> {
    return new Symbol(sym.EOF, yyline, yycolumn);
}