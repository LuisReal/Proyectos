
package instrucciones;

import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class For extends Instruccion {
    private Instruccion asignacion;
    private Instruccion condicion;
    private Instruccion actualizacion;
    private LinkedList<Instruccion> instrucciones;

    public For(Instruccion asignacion, Instruccion condicion, Instruccion actualizacion, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.asignacion = asignacion;
        this.condicion = condicion;
        this.actualizacion = actualizacion;
        this.instrucciones = instrucciones;
    }
    @Override
    
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        //for(i=0; i<10; i++)
        var tablaFor = new tablaSimbolos("FOR",tabla);
        arbol.addTabla(tablaFor);

        // ejecutar asignacion o declaracion
        var resAsig = this.asignacion.interpretar(arbol, tablaFor);
        if (resAsig instanceof Errores){
            return resAsig;
        } 
        // ejecucion de condicional
        var cond = this.condicion.interpretar(arbol, tablaFor);
        if (cond instanceof Errores){
            return cond;
        }
        
        if (!(cond instanceof Boolean)) {
            return new Errores("Semantico", "La condición del for debe ser booleana", this.line, this.column);
        }
        // validar que la condicion sea bool
        while ((boolean)cond){
            
            boolean continueFor = false;
            // tabla para bloque de instrucciones
            //var tablaLocal = new tablaSimbolos("FOR",tablaFor);
            //arbol.addTabla(tablaLocal);
            // ejecutan instrucciones en for
            // ejecutar instrucciones del bloque
            tablaSimbolos tablaIteracion = new tablaSimbolos("FOR_ITER", tablaFor);
            arbol.addTabla(tablaIteracion);
            
            for (Instruccion instr : instrucciones) {

                Object res = instr.interpretar(arbol, tablaIteracion);
                
                // revisar si viene break o continue
                if (res instanceof Break) {
                    return null; // rompe el for
                }

                if (res instanceof Continue) {
                    continueFor = true;
                    break; // sale del bloque, pero no del for
                }
                
                if (res instanceof Return) {
                    return res;
                }

                if (res instanceof Errores) {
                    return res;
                }
            }
            
            // 4. ejecutar actualización (i++, i = i + 1, etc.)
            Object resAct = this.actualizacion.interpretar(arbol, tablaFor);
            if (resAct instanceof Errores) {
                return resAct;
            }

            // 5. reevaluar condición
            cond = this.condicion.interpretar(arbol, tablaFor);
            if (cond instanceof Errores) {
                return cond;
            }
            if (!(cond instanceof Boolean)) {
                return new Errores("Semantico",
                        "La condición del for debe ser booleana",
                        this.line, this.column);
            }

            if (continueFor) {
                continue;
            }
                // ejecutar actualizacion con newTabla
        }
        
        return null;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();
        arbol.addAST("n" + nodo + "[label=\"FOR\"];\n");

        // inicialización
        int hAsig = asignacion.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hAsig + ";\n");

        // condición
        int hCond = condicion.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hCond + ";\n");

        // actualización
        int hAct = actualizacion.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hAct + ";\n");

        // cuerpo del for (N hijos)
        for (Instruccion inst : instrucciones) {
            int h = inst.generarAST(arbol);
            arbol.addAST("n" + nodo + " -> n" + h + ";\n");
        }

        return nodo;
    }

    
}