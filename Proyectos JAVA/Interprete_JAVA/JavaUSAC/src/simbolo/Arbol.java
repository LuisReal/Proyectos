
package simbolo;
import abstracto.Instruccion;
import java.util.LinkedList;
import excepciones.Errores;
import instrucciones.Funcion;
import instrucciones.Metodo;
/**
 *
 * @author Fernando
 */
public class Arbol {
    
    
    private LinkedList<Errores> errores;
    private tablaSimbolos tablaGlobal;
    private String consolas;
    private LinkedList<Instruccion> instrucciones;
    private LinkedList<tablaSimbolos> todasTablas;
    private LinkedList<Instruccion> funciones;
    private LinkedList<Instruccion> vectores;
    private int contador;
    private String astDot = "";

    public Arbol(LinkedList<Instruccion> instrucciones) {
        
        this.instrucciones = instrucciones;
        //this.tablaGlobal = new tablaSimbolos();
        this.todasTablas = new LinkedList<>();
        this.errores = new LinkedList<>();
        this.consolas = "";
        this.funciones = new LinkedList<>();
        this.vectores =  new LinkedList<>();
    }
    
    public void addTabla(tablaSimbolos tabla){
        todasTablas.add(tabla);
    }

    public LinkedList<tablaSimbolos> getTablas(){
        return todasTablas;
    }
    
    public LinkedList<Instruccion> getInstructions() {
        return instrucciones;
    }

    public void setInstructions(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public tablaSimbolos getGlobalTable() {
        return tablaGlobal;
    }

    public void setGlobalTable(tablaSimbolos tablaGlobal) {
        this.tablaGlobal = tablaGlobal;
    }

    public LinkedList<Errores> getErrors() {
        return errores;
    }

    public void setErrors(LinkedList<Errores> errores) {
        this.errores = errores;
    }

    public String getConsoles() {
        return consolas;
    }

    public void setConsoles(String consolas) {
        this.consolas = consolas;
    }
    
    public void Print(String valor){
        this.consolas += valor + "\n";
    }
    
    public LinkedList<Instruccion> getFunciones() {
        return funciones;
    }

    public void setFunciones(LinkedList<Instruccion> funciones) {
        this.funciones = funciones;
    }
    public void addFunciones(Instruccion funcion){
        this.funciones.add(funcion);
    }
    public Instruccion getFuncion(String id){
        for(var i:this.funciones){
            if (i instanceof Metodo){
                Metodo metodo = (Metodo) i;
                if (metodo.id.equalsIgnoreCase(id)){
                    return i;
                }
            }else if(i instanceof Funcion){
                Funcion funcion = (Funcion) i;
                if (funcion.id.equalsIgnoreCase(id)){
                    return i;
                }
            }
        }
        return null;
    }
    
    public int getContador() { //se usa para generar el AST
        return contador++;
    }
    
    public String getAST() {

        StringBuilder dot = new StringBuilder();
        dot.append("digraph AST {\n");
        dot.append("node [shape=box];\n");
        dot.append(astDot);
        dot.append("}");

        return dot.toString();
    }
    
    public void addAST(String txt) {
        astDot += txt;
    }

    

}
