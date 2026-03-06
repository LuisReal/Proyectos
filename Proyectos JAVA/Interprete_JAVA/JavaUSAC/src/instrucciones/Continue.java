/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instrucciones;

import abstracto.Instruccion;
import simbolo.*;

public class Continue extends Instruccion {
    
    public Continue(int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
    }
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
       return this;
    }
    
    @Override
    public int generarAST(Arbol arbol) {
        int nodo = arbol.getContador();
        arbol.addAST("n"+nodo+"[label=\"CONTINUE\"];\n");
        return nodo;
    }
}
