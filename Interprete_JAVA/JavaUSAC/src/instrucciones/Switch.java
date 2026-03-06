
package instrucciones;

import simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
/**
 *
 * @author Fernando
 */
public class Switch extends Instruccion {
    
    private Instruccion expresion;
    private LinkedList<Instruccion> list_cases;
    private Default def;
    

    public Switch(Instruccion expresion, LinkedList<Instruccion> list_cases, Default def, int linea, int columna) {
        
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
        this.list_cases = list_cases;
        this.def = def;
    }
    
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        // evaluar expresión del switch
        var condicion = this.expresion.interpretar(arbol, tabla);
        if (condicion instanceof Errores) {
            return condicion;
        }
    
        // verifica si es de tipo booleano
        /*if(!(condicion instanceof Boolean)){
            return new Errores("Semantico", "La condicion del switch tiene que devolver un valor booleano", this.line, this.column);
        }*/

        // evaluar cases
        if (list_cases != null) {
            for (Instruccion c : list_cases) {
                if (c instanceof Case) {
                    Case caseInst = (Case) c;
                    var res = caseInst.evaluar(condicion, arbol, tabla);
                    
                    if (res instanceof Break) {
                        return null; // rompe el switch
                    }

                    if (res instanceof Continue) {
                        return res; // se propaga al while / for
                    }

                    if (res instanceof Errores) {
                        return res;
                    }
                    if (res instanceof Boolean && (Boolean) res) {
                        return true; // case ejecutado
                    }
                }
            }
        }

        // ejecutar default si ningún case coincidió
        if (def != null) {
            var res = def.interpretar(arbol, tabla);

            if (res instanceof Break) {
                return null;
            }

            if (res instanceof Continue) {
                return res;
            }

            return res;
        }

        return null;
        
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();
        arbol.addAST("n" + nodo + "[label=\"SWITCH\"];\n");

        
        int hExp = expresion.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hExp + ";\n");

        
        if (list_cases != null) {
            for (Instruccion c : list_cases) {
                int hCase = c.generarAST(arbol);
                arbol.addAST("n" + nodo + " -> n" + hCase + ";\n");
            }
        }

        
        if (def != null) {
            int hDef = def.generarAST(arbol);
            arbol.addAST("n" + nodo + " -> n" + hDef + ";\n");
        }

        return nodo;
    }

}
