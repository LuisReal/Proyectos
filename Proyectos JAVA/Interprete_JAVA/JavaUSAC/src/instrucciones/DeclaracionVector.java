
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author Fernando
 */
public class DeclaracionVector extends Instruccion{
    
    public String id;
    public int dimension; // 1 o 2
    private Object listaValores;
   
    public DeclaracionVector(String id, Object valores, Tipo tipo, int dimension, int linea, int columna) {
        super(tipo, linea, columna);
        this.id = id;
        this.listaValores = valores;
        this.dimension = dimension;
    }
    
     

    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        // 1. Verificar si ya existe
        if (tabla.getVariable(id) != null) {
            return new Errores("Semantico", "El vector '" + id + "' ya existe", line, column);
        }

        tipoDato tipoVector = this.tipo.getTipo();

        /* ===================== VECTOR 1D ===================== */
        if (dimension == 1) {

            @SuppressWarnings("unchecked")
            LinkedList<Instruccion> valoresAST = (LinkedList<Instruccion>) listaValores;

            List<Object> valoresFinales = new ArrayList<>();

            for (Instruccion inst : valoresAST) {

                Object valor = inst.interpretar(arbol, tabla);
                if (valor instanceof Errores) {
                    return valor;
                }

                if (inst.tipo.getTipo() != tipoVector) {
                    return new Errores(
                            "Semantico",
                            "No se puede insertar un valor de tipo " + inst.tipo.getTipo()
                                    + " en un vector de tipo " + tipoVector,
                            inst.line,
                            inst.column
                    );
                }

                valoresFinales.add(valor);
            }

            tabla.setVariable(new Simbolo(this.tipo, id, valoresFinales));
            return null;
        }

        /* ===================== VECTOR 2D ===================== */
        if (dimension == 2) {

            @SuppressWarnings("unchecked")
            LinkedList<LinkedList<Instruccion>> filasAST =
                    (LinkedList<LinkedList<Instruccion>>) listaValores;

            List<List<Object>> matriz = new ArrayList<>();
            int columnasEsperadas = -1;

            for (LinkedList<Instruccion> fila : filasAST) {

                List<Object> filaFinal = new ArrayList<>();

                if (columnasEsperadas == -1) {
                    columnasEsperadas = fila.size();
                } else if (fila.size() != columnasEsperadas) {
                    return new Errores(
                            "Semantico",
                            "Todas las filas del vector 2D deben tener el mismo tamaño",
                            line,
                            column
                    );
                }

                for (Instruccion inst : fila) {

                    Object valor = inst.interpretar(arbol, tabla);
                    if (valor instanceof Errores) {
                        return valor;
                    }

                    if (inst.tipo.getTipo() != tipoVector) {
                        return new Errores(
                                "Semantico",
                                "No se puede insertar un valor de tipo " + inst.tipo.getTipo()
                                        + " en un vector de tipo " + tipoVector,
                                inst.line,
                                inst.column
                        );
                    }

                    filaFinal.add(valor);
                }

                matriz.add(filaFinal);
            }

            tabla.setVariable(new Simbolo(this.tipo, id, matriz));
            return null;
        }

        /* ===================== ERROR ===================== */
        return new Errores("Semantico", "Dimensión de vector no válida", line, column);
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();
        arbol.addAST(
            "n" + nodo + "[label=\"DECLARACION_VECTOR\"];\n"
        );

        /* ===== VECTOR 1D ===== */
        if (dimension == 1) {
            @SuppressWarnings("unchecked")
            LinkedList<Instruccion> lista =
                (LinkedList<Instruccion>) listaValores;

            for (Instruccion inst : lista) {
                int h = inst.generarAST(arbol);
                arbol.addAST("n" + nodo + " -> n" + h + ";\n");
            }
        }

        /* ===== VECTOR 2D ===== */
        if (dimension == 2) {
            @SuppressWarnings("unchecked")
            LinkedList<LinkedList<Instruccion>> matriz =
                (LinkedList<LinkedList<Instruccion>>) listaValores;

            for (LinkedList<Instruccion> fila : matriz) {

                int nFila = arbol.getContador();
                arbol.addAST("n" + nFila + "[label=\"FILA\"];\n");
                arbol.addAST("n" + nodo + " -> n" + nFila + ";\n");

                for (Instruccion inst : fila) {
                    int h = inst.generarAST(arbol);
                    arbol.addAST("n" + nFila + " -> n" + h + ";\n");
                }
            }
        }

        return nodo;
    }

}
