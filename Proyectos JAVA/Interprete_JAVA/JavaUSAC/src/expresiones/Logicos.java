
package expresiones;
import abstracto.Instruccion;
import simbolo.*;
import excepciones.Errores;

/**
 *
 * @author Fernando
 */
public class Logicos extends Instruccion{
    private Instruccion izq;
    private Instruccion der;
    private Instruccion unico;
    private OperadoresLogicos operador;

    public Logicos(Instruccion izq, Instruccion der, OperadoresLogicos operador, int linea, int col) {
        super(new Tipo(tipoDato.BOOLEANO), linea, col);
        this.izq = izq;
        this.der = der;
        this.operador = operador;
    }
    
    public Logicos(Instruccion unico, OperadoresLogicos operador, int linea, int col) {
        super(new Tipo(tipoDato.BOOLEANO), linea, col);
        this.unico = unico;
        this.operador = operador;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        Object Izquierda = null, Derecha = null, Unico_ = null;
        
        if (this.unico!=null){
            Unico_ = this.unico.interpretar(arbol, tabla);
            
            if (Unico_ instanceof Errores) {
                return Unico_;
            }
            
            return !(Boolean) Unico_;
        }else{
            
            Izquierda = this.izq.interpretar(arbol, tabla);
            if (Izquierda instanceof Errores) {
                return Izquierda;
            }

            Derecha = this.der.interpretar(arbol, tabla);
            if (Derecha instanceof Errores) {
                return Derecha;
            }
        }
        
        boolean a = getBooleano(Izquierda, arbol, tabla);
        boolean b = getBooleano(Derecha, arbol, tabla);
        
        return switch(operador){
            case OR -> a || b;
            case AND -> a && b;
            case XOR -> a ^ b;
            
            default -> new Errores("SEMANTICO", "Operador Logico Invalido", line, column);
        };
    }
    
    private boolean getBooleano(Object valor, Arbol arbol, tablaSimbolos tabla) {
        System.out.println("El valor es: "+valor.toString());
        if (valor instanceof Boolean) {
            return (Boolean) valor;
        }
        return false;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();

        
        arbol.addAST("n" + nodo + "[label=\"" + operador + "\"];\n");

        
        if (unico != null) {
            int h = unico.generarAST(arbol);
            arbol.addAST("n" + nodo + " -> n" + h + ";\n");
            return nodo;
        }

        
        int hIzq = izq.generarAST(arbol);
        int hDer = der.generarAST(arbol);

        arbol.addAST("n" + nodo + " -> n" + hIzq + ";\n");
        arbol.addAST("n" + nodo + " -> n" + hDer + ";\n");

        return nodo;
    }

     
}
