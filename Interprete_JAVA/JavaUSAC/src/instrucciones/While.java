
package instrucciones;

import simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class While extends Instruccion {
    private Instruccion expresion;
    private LinkedList<Instruccion> instrucciones;

    public While(Instruccion expresion, LinkedList<Instruccion> instrucciones, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
        this.instrucciones = instrucciones;
    }
    
    @Override 
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        // se crear una nueva tabla
        // ejecuta condicion inicial del while
        Object condicion = this.expresion.interpretar(arbol, tabla);
       
        if (condicion instanceof Errores){
            return condicion;
        } 
        if (!(condicion instanceof Boolean))
            return new Errores("Semantico", "La condicion del while tiene que devolver un valor booleano", this.line, this.column);
        // se ejecutan instrucciones mientras condicion sea verdadero
        while ((boolean)condicion){
            tablaSimbolos tablaWhile = new tablaSimbolos(tabla); // se crea una nueva tabla de simbolos
            boolean continueWhile = false;
            
            for (var instruccion: instrucciones){
                
                Object resultado = instruccion.interpretar(arbol, tablaWhile);
                
                if (resultado  instanceof Break){
                   return null; 
                }
                
               
                if (resultado instanceof Continue) {
                    continueWhile = true; // marca para saltar la iteración
                    break; // sale del for de instrucciones
                }
                
                if (resultado instanceof Return) {
                    return resultado;
                }
                
                // valida que la instruccion no tenga errores
                if (resultado instanceof Errores){
                    return resultado;
                } 
                
            }
            if (continueWhile) {
                // vuelve a evaluar la condición y continua el while
                condicion = this.expresion.interpretar(arbol, tablaWhile);
                if (condicion instanceof Errores) return condicion;
                if (!(condicion instanceof Boolean))
                    return new Errores("Semantico", "La condicion del while tiene que devolver un valor booleano", this.line, this.column);
                continue; 
            }
            // ejecuta la condicion nuevamente despues del bloque de instrucciones
            condicion = this.expresion.interpretar(arbol, tablaWhile);
            // valida que la condicion no tenga errores
            if (condicion instanceof Errores){
                return condicion;
            } 
            // verifica que condicion sea un booleano despues del bloque de instrucciones
            if(!(condicion instanceof Boolean)){
                
                return new Errores("Semantico", "La condicion del while tiene que devolver un valor booleano", this.line, this.column);
            }
        }
        return null;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();
        arbol.addAST("n" + nodo + "[label=\"WHILE\"];\n");

        
        int hCond = expresion.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hCond + ";\n");

        
        for (Instruccion inst : instrucciones) {
            int h = inst.generarAST(arbol);
            arbol.addAST("n" + nodo + " -> n" + h + ";\n");
        }

        return nodo;
    }
}
