package expresiones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;
import java.util.List;

public class ToString extends Instruccion {

    private Instruccion expresion;

    public ToString(Instruccion expresion, int linea, int columna) {
        
        super(new Tipo(tipoDato.CADENA), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {

        Object valor = expresion.interpretar(arbol, tabla);

        if (valor instanceof Errores) {
            return valor;
        }

        // ===== ENTERO ===== 
        if (valor instanceof Integer) {
            return String.valueOf(valor);
        }

        // ===== BOOLEANO ===== 
        if (valor instanceof Boolean) {
            return String.valueOf(valor);
        }

        // ===== CARACTER ===== 
        if (valor instanceof Character) {
            return String.valueOf(valor);
        }
        
        // ===== DECIMAL ===== 
        if (valor instanceof Double) {
            return String.valueOf(valor);
        }

        return new Errores("Semantico", "La función toString solo acepta enteros, booleanos, caracteres o decimales", line, column);
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();

        arbol.addAST("n" + nodo + "[label=\"TOSTRING\"];\n");

        int hijo = expresion.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hijo + ";\n");

        return nodo;
    }
}
