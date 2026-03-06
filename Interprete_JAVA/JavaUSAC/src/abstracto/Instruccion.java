
package abstracto;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
/**
 *
 * @author Fernando
 */
public abstract class Instruccion {
    public Tipo tipo;
    public int line;
    public int column;

    public Instruccion(Tipo tipo, int line, int column) {
        this.tipo = tipo;
        this.line = line;
        this.column = column;
    }
    
    public abstract Object interpretar(Arbol arbol, tablaSimbolos tabla);
    
    public abstract int generarAST(Arbol arbol);

}
