
package expresiones;

import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;
import abstracto.Instruccion;
import excepciones.Errores;

/**
 *
 * @author Fernando
 */
public class AccesoVar extends Instruccion {
    private String id;

    public AccesoVar(String id, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
    }

    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        var valor = tabla.getVariable(this.id);
        if (valor == null){
            return new Errores("semantico", "Variable no existente(ACCESO VAR)", this.line, this.column);
        }
        this.tipo.setTipo(valor.getTipo().getTipo());
        
        if (valor.getValorlista() != null) {
            return valor.getValorlista();
        }
        
        return valor.getValor();
    }
    
    public String getId() {
        return this.id;
    }
    
    @Override
    public int generarAST(Arbol arbol) {
        int nodo = arbol.getContador();
        arbol.addAST("n"+nodo+"[label=\"ACCESO_VAR\\n" + id + "\"];\n");
        
        return nodo;
    }


    
}
