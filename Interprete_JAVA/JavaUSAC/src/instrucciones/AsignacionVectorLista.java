/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instrucciones;
import simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
import expresiones.AccesoVectorLista;
import java.util.List;
/**
 *
 * @author Fernando
 */
public class AsignacionVectorLista extends Instruccion {
    
    private AccesoVectorLista acceso;
    private Instruccion valor;

    public AsignacionVectorLista(AccesoVectorLista acceso, Instruccion valor, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.acceso = acceso;
        this.valor = valor;
    }
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {

        
        String id = acceso.getId();
        Instruccion indice1 = acceso.getIndice1();
        Instruccion indice2 = acceso.getIndice2();

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
            return new Errores("Semantico", "El índice debe ser entero",line, column);
        }

        int idx1 = (int) i1;

        Object nuevoValor = valor.interpretar(arbol, tabla);
        
        if (nuevoValor instanceof Errores) {
            return nuevoValor;
        }
        
        // Valida que el tipo del nuevoValor coincida con el tipo de la lista o vector
        tipoDato tipoEsperado = sim.getTipo().getTipo();
        tipoDato tipoValor = valor.tipo.getTipo();
        
        if (tipoValor != tipoEsperado) {
            return new Errores("Semantico", "No se puede asignar un valor de tipo " + tipoValor +
                " a una estructura de tipo " + tipoEsperado, line, column);
        }

        /* ===== LISTA o VECTOR 1D ===== */
        if (indice2 == null) {
            List<Object> lista = (List<Object>) estructura;

            if (idx1 < 0 || idx1 >= lista.size()) {
                return new Errores("Semantico", "Índice fuera de rango", line, column);
            }

            lista.set(idx1, nuevoValor);
            
            return null;
        }

        /* ===== VECTOR 2D ===== */
        Object i2 = indice2.interpretar(arbol, tabla);
        if (!(i2 instanceof Integer)) {
            return new Errores("Semantico", "El segundo índice debe ser entero", line, column);
        }

        int idx2 = (int) i2;

        List<?> matriz = (List<?>) estructura;

        if (idx1 < 0 || idx1 >= matriz.size()) {
            return new Errores("Semantico", "Índice de fila fuera de rango", line, column);
        }

        Object filaObj = matriz.get(idx1);
        if (!(filaObj instanceof List)) {
            return new Errores("Semantico", "La estructura no es 2D", line, column);
        }

        List<Object> fila = (List<Object>) filaObj;

        if (idx2 < 0 || idx2 >= fila.size()) {
            return new Errores("Semantico", "Índice de columna fuera de rango", line, column);
        }

        fila.set(idx2, nuevoValor);
        return null;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();
        arbol.addAST("n" + nodo + "[label=\"ASIGNACIONVL\"];\n");

        int hAcceso = acceso.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hAcceso + ";\n");

        int hValor = valor.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hValor + ";\n");

        return nodo;
    }
}
