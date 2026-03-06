package expresiones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;
import java.util.List;

/**
 *
 * @author Fernando
 */

public class Length extends Instruccion {

    private Instruccion expresion;

    public Length(Instruccion expresion, int linea, int columna) {
        
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {

        Object valor = expresion.interpretar(arbol, tabla);

        if (valor instanceof Errores) {
            return valor;
        }

        /* ===== CADENA ===== */
        if (valor instanceof String) {
            return ((String) valor).length();
        }

        /* ===== LISTA o VECTOR 1D ===== */
        if (valor instanceof List) {
            return ((List<?>) valor).size();
        }

        return new Errores("Semantico", "La función length solo acepta cadenas, listas o vectores", line, column);
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();

        arbol.addAST("n" + nodo + "[label=\"LENGTH\"];\n");

        int hijo = expresion.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hijo + ";\n");

        return nodo;
    }
}
