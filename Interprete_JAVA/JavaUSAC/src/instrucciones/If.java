
package instrucciones;

import simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class If extends Instruccion {
    private Instruccion expresion;
    private LinkedList<Instruccion> instrucciones;
    private LinkedList<Instruccion> instrucciones_elseif;
    private LinkedList<Instruccion> instrucciones_else;
    
    //constructor para manejar if
    public If(Instruccion expresion, LinkedList<Instruccion> instrucciones,  int linea, int columna) {
        
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
        this.instrucciones = instrucciones;
    }
    
    //constructor para manejar else
    public If(Instruccion expresion, LinkedList<Instruccion> instrucciones, LinkedList<Instruccion> instruccioneselse,int linea, int columna){
        
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
        this.instrucciones = instrucciones;
        this.instrucciones_else = instruccioneselse;  
    }
    
    //constructor para manejar else if
    public If(Instruccion expresion, LinkedList<Instruccion> instrucciones, LinkedList<Instruccion> instruccioneselseif,LinkedList<Instruccion> instruccioneselse,int linea, int columna){
        
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
        this.instrucciones = instrucciones;
        this.instrucciones_elseif = instruccioneselseif;
        this.instrucciones_else = instruccioneselse;  
    }
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        
        
        
        var condicion = this.expresion.interpretar(arbol, tabla);
        if (condicion instanceof Errores){
            //arbol.getErrors().add((Errores) condicion);
            return condicion;
        }

        if (condicion == null) {
            Errores err = new Errores("Semantico", "La condicion es nula", this.line, this.column);
            arbol.getErrors().add(err); // Agregamos al árbol
            return err;
        }
        
       
        // verifica si es de tipo booleano
        if(!(condicion instanceof Boolean)){
            Errores error = new Errores("Semantico", "La condicion del if tiene que devolver un valor booleano", this.line, this.column);
            //arbol.getErrors().add(error);
            arbol.getErrors().add(error);
            return error;
        }
        
        
        boolean ejecutarIf = (Boolean) condicion;
        // manejar if
        // ejecutar el bloque de instrucciones if
        if(ejecutarIf){
            //se va ejecutar el bloque de instrucciones if
            var nuevaTabla = new tablaSimbolos("IF",tabla);
            arbol.addTabla(nuevaTabla);
            for (var instruccion: instrucciones){
                
                var res = instruccion.interpretar(arbol, nuevaTabla);
                
                if (res instanceof Break){
                    return res;
                }
                
                if (res instanceof Continue){
                    return res; 
                }
                
                if (res instanceof Return) {
                    return res;
                }
                
                if (res instanceof Errores){
                    
                    return res;
                }
            }
            return true; // ejecuta if
        }
        
        // manejar elseif
        if (instrucciones_elseif != null){
            for (Instruccion elseif: instrucciones_elseif){
                var res  = elseif.interpretar(arbol, tabla);
                
                if (res instanceof Break){
                    return res;
                }
                
                if (res instanceof Continue){
                    return res; 
                }
                
                if (res instanceof Return) {
                    return res;
                }
                
                if (res instanceof Errores){
                    return res;
                }
                if (res instanceof Boolean && (Boolean) res){
                    return true;
                }
            }
        }
        
        // manejar else
        if (instrucciones_else != null){
            var nuevaTabla = new tablaSimbolos("ELSE",tabla);
            arbol.addTabla(nuevaTabla);
            for(var instElse: instrucciones_else){
                var res = instElse.interpretar(arbol, nuevaTabla);
                
                if (res instanceof Break){
                    return res;
                }
                
                if (res instanceof Continue){
                    return res; 
                }
                
                if (res instanceof Return) {
                    return res;
                }
                
                if (res instanceof Errores){
                    return res;
                }
            }
            return true;
        }
        return false;
        
        
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();
        arbol.addAST("n" + nodo + "[label=\"IF\"];\n");

        
        int hCond = expresion.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hCond + ";\n");

        // IF 
        for (Instruccion inst : instrucciones) {
            int h = inst.generarAST(arbol);
            arbol.addAST("n" + nodo + " -> n" + h + ";\n");
        }

        // ELSE IF 
        if (instrucciones_elseif != null) {
            int nElseIf = arbol.getContador();
            arbol.addAST("n" + nElseIf + "[label=\"ELSEIF\"];\n");
            arbol.addAST("n" + nodo + " -> n" + nElseIf + ";\n");

            for (Instruccion inst : instrucciones_elseif) {
                int h = inst.generarAST(arbol);
                arbol.addAST("n" + nElseIf + " -> n" + h + ";\n");
            }
        }

        // ELSE
        if (instrucciones_else != null) {
            int nElse = arbol.getContador();
            arbol.addAST("n" + nElse + "[label=\"ELSE\"];\n");
            arbol.addAST("n" + nodo + " -> n" + nElse + ";\n");

            for (Instruccion inst : instrucciones_else) {
                int h = inst.generarAST(arbol);
                arbol.addAST("n" + nElse + " -> n" + h + ";\n");
            }
        }

        return nodo;
    }

}
