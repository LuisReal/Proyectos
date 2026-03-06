
package simbolo;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
/**
 *
 * @author Fernando
 */
public class tablaSimbolos {
    private tablaSimbolos tablaAnterior;
    private HashMap<String, Object> tablaActual;
    private String nombre;
    
    /*public tablaSimbolos(){
        this.tablaActual = new HashMap<>();
        this.nombre = "";
    }*/
    
    public tablaSimbolos(String nombre, tablaSimbolos tablaAnterior){
        this.tablaActual = new HashMap<>();
        this.tablaAnterior = tablaAnterior;
        this.nombre = nombre;
    }

    public tablaSimbolos getTablaAnterior() {
        return tablaAnterior;
    }

    public void setTablaAnterior(tablaSimbolos tablaAnterior) {
        this.tablaAnterior = tablaAnterior;
    }

    public HashMap<String, Object> getTablaActual() {
        return tablaActual;
    }

    public void setTablaActual(HashMap<String, Object> tablaActual) {
        this.tablaActual = tablaActual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public tablaSimbolos(tablaSimbolos tablaAnterior){
        this.tablaAnterior = tablaAnterior;
        this.tablaActual = new HashMap<>();       
        this.nombre = "";
    }
    
    public boolean setVariable(Simbolo simbolo){
        Simbolo busqueda = (Simbolo) this.tablaActual.get(simbolo.getId().toLowerCase());
        if (busqueda == null){
            this.tablaActual.put(simbolo.getId().toLowerCase(), simbolo);
            return true;
        }
        return false;
    }
    
    
    
    public Simbolo getVariable(String id) {
        for (tablaSimbolos i = this; i != null; i = i.getTablaAnterior()) {
            Simbolo busqueda = (Simbolo) i.tablaActual.
                    get(id.toLowerCase());
            if (busqueda != null) {
                return busqueda;
            }
        }
        return null;
    }
    
    public boolean updateVariable(String id, Object nuevoValor) { //actualiza la variable en la tabla de simbolos
        
        for (tablaSimbolos i = this; i != null; i = i.getTablaAnterior()) {
            Simbolo simbolo = (Simbolo) i.tablaActual.get(id.toLowerCase());
            if (simbolo != null) {
                simbolo.setValor(nuevoValor); 
                return true;
            }
        }
        return false; // Variable no encontrada
    }
    
    public LinkedList<Simbolo> getAllSimbolos() {
        LinkedList<Simbolo> lista = new LinkedList<>();

        for (tablaSimbolos t = this; t != null; t = t.getTablaAnterior()) {
            for (Map.Entry<String, Object> entry : t.tablaActual.entrySet()) {
                lista.add((Simbolo) entry.getValue());
            }
        }

        return lista;
    }
    
    public LinkedList<String[]> getAllSimbolosConAmbiente() {
        LinkedList<String[]> lista = new LinkedList<>();
        
        for (tablaSimbolos actual = this; actual != null; actual = actual.getTablaAnterior()) {
            String nombreAmbiente = actual.getNombre().isEmpty() ? "ANONIMO" : actual.getNombre();
            for (Object obj : actual.getTablaActual().values()) {
                Simbolo sim = (Simbolo) obj;
                lista.add(new String[]{
                    nombreAmbiente,
                    sim.getId(),
                    sim.getTipo().getTipo().toString(),
                    String.valueOf(sim.getValor())
                });
            }
        }
        return lista;
    }



    
}
