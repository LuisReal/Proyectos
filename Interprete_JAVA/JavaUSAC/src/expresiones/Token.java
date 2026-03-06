
package expresiones;
import simbolo.Tipo;
import abstracto.Instruccion;
import simbolo.Arbol;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;
/**
 *
 * @author Fernando
 */
public class Token extends Instruccion {
    
    public Object valor;

    public Token(Object valor, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.valor = valor;
    }
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        if(this.tipo.getTipo() == tipoDato.CADENA){
            String texto = valor.toString();
            texto = texto.replace("\\n", "\n")
                     .replace("\\t", "\t")   // tabulación
                     .replace("\\\"", "\"")  // comilla doble
                     .replace("\\'", "'")    // comilla simple
                     .replace("\\\\", "\\"); // barra invertida
            return texto;
        }
        return this.valor;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();

        arbol.addAST(
            "n" + nodo + "[label=\"" + valor.toString() + "\"];\n"
        );

        return nodo;
    }
}
