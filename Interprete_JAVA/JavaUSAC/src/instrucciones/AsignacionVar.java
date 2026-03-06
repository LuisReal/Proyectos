
package instrucciones;
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
public class AsignacionVar extends Instruccion{
    private String id;
    public Instruccion valor;

    public AsignacionVar(String id, Instruccion valor, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.valor = valor;
    }
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        var variable = tabla.getVariable(id);
        if (variable == null){
            return new Errores("ERROR: semantico", "Variable no existente(asignacion)", this.line, this.column);
        }
        var newValor = this.valor.interpretar(arbol, tabla);
        if (newValor instanceof Errores){
            return newValor;
        }
        if(variable.getTipo().getTipo() != this.valor.tipo.getTipo()){
            return new Errores("ERROR: semantico", "Tipos erroneos", this.line, this.column);
            
        }
        variable.setValor(newValor);
        return null;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();

        arbol.addAST("n" + nodo + "[label=\"ASIGNACIONVAR\"];\n");

        int hijo = valor.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hijo + ";\n");

        return nodo;
    }
}
