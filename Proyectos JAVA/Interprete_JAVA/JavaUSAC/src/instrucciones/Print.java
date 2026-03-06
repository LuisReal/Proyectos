
package instrucciones;
import abstracto.Instruccion;
import simbolo.*;
/**
 *
 * @author Fernando
 */
public class Print extends Instruccion {
    private Instruccion expresion;

    public Print(Instruccion expresion, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.expresion = expresion;
    }
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        var resultado = this.expresion.interpretar(arbol, tabla);
        arbol.Print(resultado.toString());
        return null;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();

        arbol.addAST("n" + nodo + "[label=\"PRINT\"];\n");

        int exp = expresion.generarAST(arbol);

        arbol.addAST("n" + nodo + " -> n" + exp + ";\n");

        return nodo;
    }

}
