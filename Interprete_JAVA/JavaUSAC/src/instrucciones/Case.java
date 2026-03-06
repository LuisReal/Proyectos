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
public class Case extends Instruccion{
    
    private Instruccion expresion;
    private LinkedList<Instruccion> instrucciones;

    public Case(Instruccion expresion, LinkedList<Instruccion> instrucciones, int linea, int columna) {

        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
        this.instrucciones = instrucciones;
    }
    
    public Object evaluar(Object condicion, Arbol arbol, tablaSimbolos tabla) {

        var valorCase = expresion.interpretar(arbol, tabla);
        if (valorCase instanceof Errores) {
            return valorCase;
        }

        // si el valor de la condicion del switch no es igual al del case
        if (!condicion.equals(valorCase)) { 
            return false;
        }

        // ejecutar instrucciones del case
        var nuevaTabla = new tablaSimbolos("CASE",tabla);
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
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        return null;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();
        arbol.addAST("n" + nodo + "[label=\"CASE\"];\n");

        // expresión del case
        int hExp = expresion.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hExp + ";\n");

        // instrucciones del case
        for (Instruccion inst : instrucciones) {
            int hijo = inst.generarAST(arbol);
            arbol.addAST("n" + nodo + " -> n" + hijo + ";\n");
        }

        return nodo;
    }
    
    
}
