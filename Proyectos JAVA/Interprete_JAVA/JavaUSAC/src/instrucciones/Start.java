
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
public class Start extends Instruccion {
    
    private String id;
    private LinkedList<Instruccion> parametros;
   
    public Start(String id,LinkedList<Instruccion> parametros, int linea, int columna) {
        
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.parametros = parametros;
    }
    
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        
        var busqueda  =  arbol.getFuncion(id);
        
        if(busqueda == null){
            return new Errores("SEMANTICO", "Funcion " + id + " no existe", this.line, this.column);
        }
        
        if (busqueda instanceof Metodo){
            
           Metodo metodo = (Metodo) busqueda; 
           var nuevaTabla = new tablaSimbolos("START", tabla);
           //nuevaTabla.setNombre("START");
           
           // verifica el tamaño de parametros del metodo con el start
           if(metodo.parametros.size()!=this.parametros.size()){
               return new Errores("SEMANTICO", "tamaño de parametros no coincide", this.line, this.column);
           }
           
           // recorre los parametros y ejecuta 
           for (int i=0; i<this.parametros.size(); i++){
               
               var identificador = (String) metodo.parametros.get(i).get("id");
               var tipo = (Tipo) metodo.parametros.get(i).get("tipo");
               var valor = this.parametros.get(i);
               
               
               var declaracionParametros = new Declaracion(identificador, valor, tipo, this.line, this.column);
               var resultadoDeclaracion = declaracionParametros.interpretar(arbol, nuevaTabla);
               
               if (resultadoDeclaracion instanceof Errores){
                  return resultadoDeclaracion;
                }

           }
           var resultadoFuncion = metodo.interpretar(arbol, nuevaTabla);
           if (resultadoFuncion instanceof Errores){
                return resultadoFuncion;
            }
        }
        return null;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();
        arbol.addAST("n" + nodo + "[label=\"START\"];\n");

        for (Instruccion p : parametros) {
            int h = p.generarAST(arbol);
            arbol.addAST("n" + nodo + " -> n" + h + ";\n");
        }

        return nodo;
    }

}