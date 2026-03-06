/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instrucciones;

import abstracto.Instruccion;
import simbolo.*;
import excepciones.Errores;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class Default extends Instruccion{
    
    private LinkedList<Instruccion> instrucciones;

    public Default(LinkedList<Instruccion> instrucciones, int linea, int columna) {

        super(new Tipo(tipoDato.VOID), linea, columna);
        this.instrucciones = instrucciones;
    }
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {

        var nuevaTabla = new tablaSimbolos("DEFAULT",tabla);
        arbol.addTabla(nuevaTabla);
        for (Instruccion inst : instrucciones) {
            var res = inst.interpretar(arbol, nuevaTabla);
            
            if (res instanceof Break) {
                return res;
            }

            
            if (res instanceof Continue) {
                return res;
            }
            
            if (res instanceof Errores) {
                return res;
            }
        }
        return true;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();
        arbol.addAST("n" + nodo + "[label=\"DEFAULT\"];\n");

        for (Instruccion inst : instrucciones) {
            int hijo = inst.generarAST(arbol);
            arbol.addAST("n" + nodo + " -> n" + hijo + ";\n");
        }

        return nodo;
    }

}
