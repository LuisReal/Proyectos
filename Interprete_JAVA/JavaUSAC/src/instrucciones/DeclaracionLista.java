
package instrucciones;

import simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fernando
 */
public class DeclaracionLista extends Instruccion {
    public String identificador;

    public DeclaracionLista(String identificador, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.identificador = identificador;
    }
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        
        List<Object> valores =new ArrayList<>();
        Simbolo s = new Simbolo(this.tipo, this.identificador, valores);
        
        boolean creacion = tabla.setVariable(s);
        if(!creacion){
            return new Errores("semantico", "variable ya existente '" + this.identificador + "'", this.line, this.column);
        }
        return null;
    }
    
    @Override
    public int generarAST(Arbol arbol) {
        int nodo = arbol.getContador();
        arbol.addAST("n"+nodo+"[label=\"DECLARACIONLISTA\"];\n");
        return nodo;
    }
}