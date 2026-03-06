package expresiones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author Fernando
 */

public class Round extends Instruccion {

    private Instruccion expresion;

    public Round(Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {

        Object valor = expresion.interpretar(arbol, tabla);

        if (valor instanceof Errores) {
            return valor;
        }

        // solo acepta decimal
        if (valor instanceof Double) {
            double d = (Double) valor;

            // redondeo
            if (d >= 0) {
                return (int) Math.floor(d + 0.5);
            } else {
                return (int) Math.ceil(d - 0.5);
            }
        }

        return new Errores("Semantico", "La función round solo acepta valores decimales", line, column);
    }

    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();

        arbol.addAST(
            "n" + nodo + "[label=\"ROUND\"];\n"
        );

        int h = expresion.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + h + ";\n");

        return nodo;
    }
}