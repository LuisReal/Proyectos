
package expresiones;


import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

import java.util.List;

/**
 *
 * @author Fernando
 */

public class AccesoVectorLista extends Instruccion {

    private String id;
    private Instruccion indice1;
    private Instruccion indice2; // null si es 1D o lista

    public AccesoVectorLista(String id, Instruccion indice1, Instruccion indice2, int linea, int columna) {
        
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.indice1 = indice1;
        this.indice2 = indice2;
    }
    
    

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {

        Simbolo sim = tabla.getVariable(id);
        if (sim == null) {
            return new Errores("Semantico", "La variable '" + id + "' no existe", line, column);
        }

        Object estructura;

        if (sim.getValorlista() != null) {
            estructura = sim.getValorlista();
        } else {
            estructura = sim.getValor();
        }

        if (!(estructura instanceof List)) {
            return new Errores("Semantico", "La variable no es indexable", line, column);
        }

        Object i1 = indice1.interpretar(arbol, tabla);
        
        if (!(i1 instanceof Integer)) {
            return new Errores("Semantico", "El índice debe ser entero", line, column);
        }

        int idx1 = (int) i1;
        List<?> lista = (List<?>) estructura;

        if (idx1 < 0 || idx1 >= lista.size()) {
            return new Errores("Semantico", "Índice fuera de rango", line, column);
        }
        
        
        
        /* ===== LISTA o VECTOR 1D ===== */
        if (indice2 == null) {
            this.tipo = sim.getTipo();
            return lista.get(idx1);
        }

        /* ===== VECTOR 2D ===== */
        Object filaObj = lista.get(idx1);
        if (!(filaObj instanceof List)) {
            return new Errores("Semantico", "No es un vector 2D", line, column);
        }

        Object i2 = indice2.interpretar(arbol, tabla);
        if (!(i2 instanceof Integer)) {
            return new Errores("Semantico", "El segundo índice debe ser entero", line, column);
        }

        int idx2 = (int) i2;
        List<?> fila = (List<?>) filaObj;

        if (idx2 < 0 || idx2 >= fila.size()) {
            return new Errores("Semantico", "Índice fuera de rango", line, column);
        }
        
        this.tipo = sim.getTipo();
        return fila.get(idx2);
    }
    
    public String getId() {
        return id;
    }

    public Instruccion getIndice1() {
        return indice1;
    }
    public Instruccion getIndice2() {
        return indice2;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();
        arbol.addAST("n" + nodo + "[label=\"ACCESO_VECTORLISTA\\n" + id + "\"];\n");

        
        int h1 = indice1.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + h1 + ";\n");

        
        if (indice2 != null) {
            int h2 = indice2.generarAST(arbol);
            arbol.addAST("n" + nodo + " -> n" + h2 + ";\n");
        }

        return nodo;
    }

}

