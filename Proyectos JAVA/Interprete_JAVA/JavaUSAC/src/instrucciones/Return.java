package instrucciones;

import simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;

/**
 *
 * @author Fernando
 */

public class Return extends Instruccion {

    public Instruccion valor;

    public Return(Instruccion valor, int linea, int columna) {
        
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.valor = valor;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        if (valor == null) {
            
            return this; 
        }
        
        Object val = valor.interpretar(arbol, tabla);
        if (val instanceof Errores) {
            return val;
        }
        return this;
    }

    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();

        arbol.addAST("n" + nodo + "[label=\"RETURN\"];\n");

        int hijo = valor.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hijo + ";\n");

        return nodo;
    }
}
