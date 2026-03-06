/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;

import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;
import expresiones.AccesoVar;

/**
 *
 * @author Fernando
 */
public class Incremento extends Instruccion{
    
    private String id;
    

    public Incremento(String id, int linea, int columna) {
        
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        
    }
    
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        
        // Obtener el símbolo de la tabla
        var simbolo = tabla.getVariable(id);

        if (simbolo == null) {
            return new Errores("SEMANTICO", "Variable '" + id + "' no existe", this.line, this.column);
        }

        // Verificar que sea numérica
        tipoDato tipoVar = simbolo.getTipo().getTipo();
        Object valor = simbolo.getValor();

        if (tipoVar != tipoDato.ENTERO && tipoVar != tipoDato.DECIMAL) {
            return new Errores("SEMANTICO", "Solo se puede incrementar variables numéricas (int o double)", this.line, this.column);
        }

        // Incrementar según el tipo
        Object nuevoValor;
        if (tipoVar == tipoDato.ENTERO) {
            nuevoValor = (int) valor + 1;
        } else { // DECIMAL
            nuevoValor = (double) valor + 1.0;
        }

        // Actualizar directamente el valor del símbolo
        simbolo.setValor(nuevoValor);

        return null;
    }
    
    @Override
    public int generarAST(Arbol arbol) {
        int nodo = arbol.getContador();
        arbol.addAST("n"+nodo+"[label=\"INCREMENTO\"];\n");
        return nodo;
    }
}
