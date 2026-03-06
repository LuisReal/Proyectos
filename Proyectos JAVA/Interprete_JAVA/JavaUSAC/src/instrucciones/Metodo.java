
package instrucciones;

import simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class Metodo extends Instruccion {
    
    public String id;
    public LinkedList<HashMap> parametros;
    private LinkedList<Instruccion> instrucciones;
   
    public Metodo(String id, LinkedList<HashMap> parametros, LinkedList<Instruccion> instrucciones, Tipo tipo, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
       
        this.id = id; //nombre del metodo(identificador)
        this.parametros = parametros;
        this.instrucciones = instrucciones;
    }
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        for (var inst : this.instrucciones){
            var resultado = inst.interpretar(arbol, tabla);
            if (resultado instanceof Errores){
                return resultado;
            }
            
        }
        return null;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();
        arbol.addAST(
            "n" + nodo + "[label=\"METODO\\n" + id + "\"];\n"
        );

        for (Instruccion inst : instrucciones) {
            int h = inst.generarAST(arbol);
            arbol.addAST("n" + nodo + " -> n" + h + ";\n");
        }

        return nodo;
    }

}