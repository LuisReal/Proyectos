package instrucciones;

import simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;

public class DoWhile extends Instruccion {

    private LinkedList<Instruccion> instrucciones;
    private Instruccion expresion;

    public DoWhile(LinkedList<Instruccion> instrucciones, Instruccion expresion,  int linea, int columna) {
        
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.instrucciones = instrucciones;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        tablaSimbolos tablaDo = new tablaSimbolos("DoWhile", tabla);
        arbol.addTabla(tablaDo);
    
        Object condicion = null;

        do {
            //tablaSimbolos tablaDo = new tablaSimbolos("DoWhile",tabla);
            //arbol.addTabla(tablaDo);
            boolean continueDo = false;

            for (var instruccion : instrucciones) {

                Object resultado = instruccion.interpretar(arbol, tablaDo);

                if (resultado instanceof Break) {
                    return null;
                }

                if (resultado instanceof Continue) {
                    continueDo = true;
                    break;
                }
                
                if (resultado instanceof Return) {
                    return resultado;
                }

                if (resultado instanceof Errores) {
                    return resultado;
                }
            }

            condicion = this.expresion.interpretar(arbol, tablaDo);
            
            if (condicion instanceof Errores){
                return condicion;
            } 

            if (!(condicion instanceof Boolean)) {
                return new Errores(
                    "Semantico",
                    "La condicion del do-while tiene que devolver un valor booleano",
                    this.line,
                    this.column
                );
            }

            if (continueDo) {
                continue;
            }

        } while ((boolean) condicion);

        return null;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();
        arbol.addAST("n" + nodo + "[label=\"DO_WHILE\"];\n");

        // Bloque de instrucciones (N hijos)
        for (Instruccion inst : instrucciones) {
            int h = inst.generarAST(arbol);
            arbol.addAST("n" + nodo + " -> n" + h + ";\n");
        }

        // Condición (1 hijo)
        int hCond = expresion.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hCond + ";\n");

        return nodo;
    }

}
