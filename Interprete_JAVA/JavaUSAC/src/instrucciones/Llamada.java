
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



public class Llamada extends Instruccion {   //llamada a funciones y metodos
    private String id;
    private LinkedList<Instruccion> parametros;
    

    public Llamada(String id, LinkedList<Instruccion> parametros, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
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
            
            var newTabla =new tablaSimbolos(tabla);
            newTabla.setNombre("Llamada de metodo "+this.id);
            
            // verificar tamaño de parametros
            if(metodo.parametros.size()!=this.parametros.size()){
                return new Errores("SEMANTICO", "tamaño de parametros erroneo", this.line, this.column);
            }


            for (int i=0; i<this.parametros.size(); i++){

                var identificador = (String) metodo.parametros.get(i).get("id");
                var tipo = (Tipo) metodo.parametros.get(i).get("tipo");
                var valor = this.parametros.get(i);


                var declaracionParametros = new Declaracion(identificador, tipo, this.line, this.column);
                var resultadoDeclaracion = declaracionParametros.interpretar(arbol, newTabla);

                if (resultadoDeclaracion instanceof Errores){
                   return resultadoDeclaracion;
                }

                var valorInterpretad = valor.interpretar(arbol, tabla);
                if (valorInterpretad instanceof Errores){
                   return valorInterpretad;
                }

                var variable = newTabla.getVariable(identificador);
                if(variable == null){
                    return new Errores("SEMANTICO", "Error en declaracion de parametros", this.line, this.column);
                }

                if (variable.getTipo().getTipo()!= valor.tipo.getTipo()){
                    return new Errores("SEMANTICO", "Error en el tipo de los parametros", this.line, this.column);
                }

                variable.setValor(valorInterpretad);
            }
            var resultadoMetodo = metodo.interpretar(arbol, newTabla);
            if (resultadoMetodo instanceof Errores){
                 return resultadoMetodo;
            }
        }else if (busqueda instanceof Funcion){
            
            Funcion funcion = (Funcion) busqueda;
            this.tipo = funcion.tipo;
            
            var newTabla =new tablaSimbolos(tabla);
            newTabla.setNombre("Llamada de funcion "+this.id);
            
            // verificar tamaño de parametros
            if(funcion.parametros.size()!=this.parametros.size()){
                return new Errores("SEMANTICO", "tamaño de parametros erroneo", this.line, this.column);
            }


            for (int i=0; i<this.parametros.size(); i++){

                var identificador = (String) funcion.parametros.get(i).get("id");
                var tipo = (Tipo) funcion.parametros.get(i).get("tipo");
                var valor = this.parametros.get(i);


                var declaracionParametros = new Declaracion(identificador, tipo, this.line, this.column);
                var resultadoDeclaracion = declaracionParametros.interpretar(arbol, newTabla);

                if (resultadoDeclaracion instanceof Errores){
                   return resultadoDeclaracion;
                }

                var valorInterpretado = valor.interpretar(arbol, tabla);
                if (valorInterpretado instanceof Errores){
                   return valorInterpretado;
                }

                var variable = newTabla.getVariable(identificador);
                if(variable == null){
                    return new Errores("SEMANTICO", "Error en declaracion de parametros", this.line, this.column);
                }

                if (variable.getTipo().getTipo()!= valor.tipo.getTipo()){
                    return new Errores("SEMANTICO", "Error en el tipo de los parametros", this.line, this.column);
                }

                variable.setValor(valorInterpretado);
            }
            var resultadoFuncion = funcion.interpretar(arbol, newTabla);
            if (resultadoFuncion instanceof Errores){
                 return resultadoFuncion;
            }
            
            if (resultadoFuncion instanceof Return){
                Return ret = (Return) resultadoFuncion;
                
                if (ret.valor != null){
                    return ret.valor.interpretar(arbol, newTabla);
                }
                return null; // para funciones que solo tienen return sin valor;
            }
            
            return resultadoFuncion; // caso general (null)
        }
        return null;
        
        
        
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();
        arbol.addAST(
            "n" + nodo + "[label=\"LLAMADA\\n" + id + "\"];\n"
        );

        for (Instruccion p : parametros) {
            int h = p.generarAST(arbol);
            arbol.addAST("n" + nodo + " -> n" + h + ";\n");
        }

        return nodo;
    }

    
}