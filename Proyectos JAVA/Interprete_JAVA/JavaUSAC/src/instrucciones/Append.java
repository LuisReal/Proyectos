
package instrucciones;

import simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;

/**
 *
 * @author Fernando
 */
public class Append extends Instruccion {

    private String id;
    private Instruccion valor;

    public Append(String id, Instruccion valor, int linea, int columna) {
        
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.valor = valor;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {

        // Busca la variable en la tabla de simbolos
        Simbolo simbolo = tabla.getVariable(this.id);
        if (simbolo == null) {
            return new Errores("Semantico", "La lista '" + id + "' no existe", line, column);
        }

        // Verifica que sea lista
        if (simbolo.getValorlista() == null) {
            return new Errores("Semantico", "La variable '" + id + "' no es una lista", line, column);
        }
        
        tipoDato tipoLista = simbolo.getTipo().getTipo();
        
        tipoDato tipoValor = valor.tipo.getTipo();

        if (tipoLista != tipoValor) {
            return new Errores("Semantico", "No se puede insertar un valor de tipo " + tipoValor +
                " en una lista de tipo " + tipoLista,
                line,
                column
            );
        }

        // Evalua el valor a agregar
        Object val = valor.interpretar(arbol, tabla);
        
        if (val instanceof Errores) {
            return val;
        }

        // Agrega a la lista
        simbolo.getValorlista().add(val);

        return null;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();

        arbol.addAST("n" + nodo + "[label=\"APPEND\"];\n");

        int hijo = valor.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hijo + ";\n");

        return nodo;
    }
}
