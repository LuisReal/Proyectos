package instrucciones;

import simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;

/**
 *
 * @author Fernando
 */
public class Remove extends Instruccion {

    private String id;
    private Instruccion indice;

    public Remove(String id, Instruccion indice, int linea, int columna) {
        super(null, linea, columna);
        this.id = id;
        this.indice = indice;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {

        // Busca la lista
        Simbolo simbolo = tabla.getVariable(this.id);
        if (simbolo == null) {
            return new Errores("Semantico", "La lista '" + id + "' no existe", line, column);
        }

        // Verifica que sea lista
        if (simbolo.getValorlista() == null) {
            return new Errores("Semantico", "La variable '" + id + "' no es una lista", line, column);
        }

        // Evalua el índice
        Object idx = indice.interpretar(arbol, tabla);
        if (idx instanceof Errores) {
            return idx;
        }

        if (!(idx instanceof Integer)) {
            return new Errores("Semantico", "El índice debe ser entero", line, column);
        }

        int index = (int) idx;

        // Valida el rango
        if (index < 0 || index >= simbolo.getValorlista().size()) {
            return new Errores("Semantico", "Índice fuera de rango", line, column);
        }

        // Elimina el elemento
        Object elemento = simbolo.getValorlista().remove(index);

        this.tipo = simbolo.getTipo(); // tipo del elemento

        return elemento;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();

        arbol.addAST("n" + nodo + "[label=\"REMOVE\"];\n");

        int hijo = indice.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hijo + ";\n");

        return nodo;
    }
}
