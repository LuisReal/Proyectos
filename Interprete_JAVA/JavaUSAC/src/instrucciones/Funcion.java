package instrucciones;

import simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
import java.util.HashMap;

/**
 *
 * @author Fernando
 */

public class Funcion extends Instruccion {

    public String id;
    public LinkedList<HashMap> parametros;
    private LinkedList<Instruccion> instrucciones;

    public Funcion(String id, LinkedList<HashMap> parametros, LinkedList<Instruccion> instrucciones, Tipo tipo, int linea, int columna) {

        super(tipo, linea, columna);
        this.id = id;
        this.parametros = parametros;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {

        for (var inst : this.instrucciones) {

            var resultado = inst.interpretar(arbol, tabla);

            // error semántico
            if (resultado instanceof Errores) {
                return resultado;
            }

            // captura del return
            if (resultado instanceof Return) {
                return resultado;
            }
        }

        return null;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();
        arbol.addAST("n" + nodo + "[label=\"FUNCION\\n" + id + "\"];\n");

        for (Instruccion inst : instrucciones) {
            int hijo = inst.generarAST(arbol);
            arbol.addAST("n" + nodo + " -> n" + hijo + ";\n");
        }

        return nodo;
    }

}
