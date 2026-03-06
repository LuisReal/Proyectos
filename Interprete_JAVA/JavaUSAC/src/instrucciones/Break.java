
package instrucciones;
import abstracto.Instruccion;
import simbolo.*;
/**
 *
 * @author Fernando
 */
public class Break extends Instruccion {

    public Break(int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
    }
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
       return this;
    }
    
    @Override
    public int generarAST(Arbol arbol) {
        int nodo = arbol.getContador();
        arbol.addAST("n"+nodo+"[label=\"BREAK\"];\n");
        return nodo;
    }
    
}
