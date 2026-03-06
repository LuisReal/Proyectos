package expresiones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;
import java.util.List;

public class Find extends Instruccion {

    private String id;
    private Instruccion valorBuscado;

    public Find(String id, Instruccion valorBuscado, int linea, int columna) {
        super(new Tipo(tipoDato.BOOLEANO), linea, columna);
        this.id = id;
        this.valorBuscado = valorBuscado;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {

        Simbolo sim = tabla.getVariable(id);
        if (sim == null) {
            return new Errores("Semantico", "La variable '" + id + "' no existe", line, column);
        }

        Object estructura;

        if (sim.getValorlista() != null) {
            estructura = sim.getValorlista();
        } else {
            estructura = sim.getValor();
        }

        if (!(estructura instanceof List)) {
            return new Errores("Semantico", "La función Find solo puede usarse en listas o vectores", line, column);
        }

        Object buscado = valorBuscado.interpretar(arbol, tabla);
        
        if (buscado instanceof Errores) {
            return buscado;
        }

        // ===== VALIDAR TIPO ===== 
        tipoDato tipoEstructura = sim.getTipo().getTipo();
        tipoDato tipoBuscado = valorBuscado.tipo.getTipo();

        if (tipoEstructura != tipoBuscado) {
            return new Errores("Semantico", "No se puede buscar un valor de tipo " + tipoBuscado +
                " en una estructura de tipo " + tipoEstructura, line,column);
        }

        List<?> lista = (List<?>) estructura;
        
        //Busca en vectores 2D
        if (!lista.isEmpty() && lista.get(0) instanceof List) {
            for (Object filaObj : lista) {
                List<?> fila = (List<?>) filaObj;
                if (fila.contains(buscado)) {
                    return true;
                }
            }
            return false;
        }
        
        return lista.contains(buscado);
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();

        arbol.addAST("n" + nodo + "[label=\"FIND\"];\n");

        int hijo = valorBuscado.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hijo + ";\n");

        return nodo;
    }
}
