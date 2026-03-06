
package instrucciones;

import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import abstracto.Instruccion;
import excepciones.Errores;

/**
 *
 * @author Fernando
 */
public class Declaracion extends Instruccion {
    public String identificador;
    public Instruccion valor;

    public Declaracion(String identificador, Instruccion valor, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.identificador = identificador;
        this.valor = valor;
    }
    
    public Declaracion(String identificador, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.identificador = identificador;
        this.valor = null;
    }
    
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        
        
        
        Object valorInterpretado = null;
       
        // Si la declaración tiene una asignación
        if (this.valor != null) {
            valorInterpretado = this.valor.interpretar(arbol, tabla);
            if (valorInterpretado instanceof Errores){
                return valorInterpretado;
            }

            // Si no coincide el tipo de dato declarado con el tipo de dato del valor asignado
            if (this.valor.tipo.getTipo() != this.tipo.getTipo()){
                return new Errores("semantico",
                        //"Tipos erroneos en la declaración de '" + this.identificador + "'",
                        "Tipo incompatible en la declaración de '" + this.identificador + 
                        "'. Esperado: " + this.tipo.getTipo() + ", Obtenido: " + this.valor.tipo.getTipo(),
                        this.line, this.column);
            }
        }

        // Si NO trae asignación → asignar valor por defecto
        else {
            // valor por defecto según el tipo
            switch(this.tipo.getTipo()){
                case ENTERO: valorInterpretado = 0; break;
                case DECIMAL: valorInterpretado = 0.0; break;
                case BOOLEANO: valorInterpretado = false; break;
                case CADENA: valorInterpretado = ""; break;
                //case CARACTER: valorInterpretado = ‘\u0000’; break;
                default: valorInterpretado = null; break;
            }
        }
        Simbolo s = new Simbolo(this.tipo, this.identificador, valorInterpretado);
        boolean creacion = tabla.setVariable(s);
        
        /*if(!creacion){
            return new Errores("semantico", "Variable ya existente", this.line, this.column);
        }*/
        return null;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();

        arbol.addAST("n" + nodo + "[label=\"DECLARACION\\n" + identificador + "\"];\n");

        if (valor != null) {
            int hijo = valor.generarAST(arbol);
            arbol.addAST("n" + nodo + " -> n" + hijo + ";\n");
        }

        return nodo;
    }
}
