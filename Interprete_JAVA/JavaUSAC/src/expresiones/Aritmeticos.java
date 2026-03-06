
package expresiones;
import abstracto.Instruccion;
import simbolo.*;
import excepciones.Errores;
/**
 *
 * @author Fernando
 */
public class Aritmeticos extends Instruccion{
    
    private Instruccion operando1;
    private Instruccion operando2;
    private OperadoresAritmeticos operaciones;
    private Instruccion operandoUnico;
    
    // constructor para la NEGACION
    public Aritmeticos(OperadoresAritmeticos operaciones, Instruccion operandoUnico, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.operaciones = operaciones;
        this.operandoUnico = operandoUnico;
    }
    
    //constructor para operaciones aritmeticas 
    public Aritmeticos(Instruccion operando1, Instruccion operando2, OperadoresAritmeticos operaciones, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operaciones = operaciones;
    }
    
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        Object opIzq = null, opDer = null, Unico = null;
        if (this.operandoUnico!=null){
            Unico = this.operandoUnico.interpretar(arbol, tabla);
            if (Unico instanceof Errores) {
                return Unico;
            }
        }else{
            opIzq = this.operando1.interpretar(arbol, tabla);
            if (opIzq instanceof Errores){
                return opIzq;
            }
            opDer = this.operando2.interpretar(arbol, tabla);
            if (opDer instanceof Errores){
                return opDer;
            }
        }
        return switch (operaciones){
            case SUMA ->
                this.suma(opIzq, opDer);
            case RESTA ->
                this.resta(opIzq, opDer);
            case MULTIPLICACION ->
                this.multiplicacion(opIzq, opDer);
            case DIVISION ->
                this.division(opIzq, opDer);
            case POTENCIA ->
                this.potenciacion(opIzq, opDer);
            case MODULO ->
                this.modulo(opIzq, opDer);
            case NEGACION ->
                this.negacion(Unico);
                
            default -> 
                new Errores("ERROR semantico", "operando inexistente", this.line, this.column);
                 
        };
        
        
    }
    
    public Object suma(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch(tipo1) {
            case ENTERO -> {
                switch (tipo2){
                    case ENTERO ->{//entero, entero
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 + (int) op2;
                    }
                    case DECIMAL -> {//entero, decimal
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 + (double) op2; //regresa un double
                    }
                    case CARACTER -> {//entero, caracter
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 + (int)(char) op2; //devuelve un entero.
                    }
                    case CADENA ->{ //entero, cadena
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                      
                    }
                    default -> {
                        return new Errores("ERROR semantico", "suma erronea", this.line, this.column);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2){
                    case ENTERO ->{
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 + (int) op2; //regresa un double
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 + (double) op2;
                    }
                    case CARACTER -> {//entero, caracter
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 + (double)(char) op2; //devuelve un double.
                    }
                    case CADENA ->{
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                      
                    }
                    default -> {
                        return new Errores("ERROR semantico", "suma erronea", this.line, this.column);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int)(char) op1 + (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)(char) op1 + (double) op2;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString(); //devuelve una cadena
                    }
                    case CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new Errores("ERROR semantico", "suma erronea", this.line, this.column);
                    }
                }
            }
            case CADENA ->{
                this.tipo.setTipo(tipoDato.CADENA);
                return op1.toString() + op2.toString();
            }
            
            default -> {
                return new Errores("ERROR semantico", "suma erronea", this.line, this.column);
            }
        }
        
        
    }
    
    public Object resta(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch(tipo1) {
            case ENTERO -> {
                switch (tipo2){
                    case ENTERO ->{
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 - (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int)op1 - (double) op2; //regresa un double
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int)op1 - (int)(char)op2; //devuelve un entero
                    }
                    
                    default -> {
                        return new Errores("ERROR semantico", "resta erronea", this.line, this.column);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2){
                    case ENTERO ->{
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 - (int) op2; //devuelve un double
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 - (double) op2;
                    }
                    
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 - (double)(char)op2; //devuelve un double
                    }
                    
                    default -> {
                        return new Errores("ERROR semantico", "resta erronea", this.line, this.column);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int)(char) op1 + (int) op2; //devuelve un entero
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)(char) op1 + (double) op2;
                    }
                    
                    
                    default -> {
                        return new Errores("ERROR semantico", "resta erronea", this.line, this.column);
                    }
                }
            }
            
            default -> {
                return new Errores("ERROR semantico", "resta erronea", this.line, this.column);
            }
        }
        
        
    }
    
    public Object multiplicacion(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch(tipo1) {
            case ENTERO -> {
                switch (tipo2){
                    case ENTERO ->{
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 * (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int)op1 * (double) op2; //regresa un double
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int)op1 * (int)(char)op2; //devuelve un entero
                    }
                    
                    default -> {
                        return new Errores("ERROR semantico", "multiplicacion erronea", this.line, this.column);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2){
                    case ENTERO ->{
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 * (int) op2; //devuelve un double
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 * (double) op2;
                    }
                    
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 * (double)(char)op2; //devuelve un double
                    }
                    
                    default -> {
                        return new Errores("ERROR semantico", "multiplicacion erronea", this.line, this.column);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int)(char) op1 * (int) op2; //devuelve un entero
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)(char) op1 * (double) op2;
                    }
                    
                    
                    default -> {
                        return new Errores("ERROR semantico", "multiplicacion erronea", this.line, this.column);
                    }
                }
            }
            
            default -> {
                return new Errores("ERROR semantico", "multiplicacion erronea", this.line, this.column);
            }
        }
        
        
    }
    
    public Object division(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch(tipo1) {
            case ENTERO -> {
                switch (tipo2){
                    case ENTERO ->{
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 / (double) (int) op2; //regresa un double(decimal)
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int)op1 / (double) op2; //regresa un double(decimal)
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int)op1 / (double)(char)op2; //devuelve un double
                    }
                    
                    default -> {
                        return new Errores("ERROR semantico", "division erronea", this.line, this.column);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2){
                    case ENTERO ->{
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 / (int) op2; //devuelve un double
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 / (double) op2;
                    }
                    
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 / (double)(char)op2; //devuelve un double
                    }
                    
                    default -> {
                        return new Errores("ERROR semantico", "division erronea", this.line, this.column);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)(char) op1 / (int) op2; //devuelve un double
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)(char) op1 / (double) op2;
                    }
                    
                    
                    default -> {
                        return new Errores("ERROR semantico", "division erronea", this.line, this.column);
                    }
                }
            }
            
            default -> {
                return new Errores("ERROR semantico", "division erronea", this.line, this.column);
            }
        }
        
        
    }
    
    public Object potenciacion(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch(tipo1) {
            case ENTERO -> {
                switch (tipo2){
                    case ENTERO ->{
                        this.tipo.setTipo(tipoDato.ENTERO);
                        /*int resultado=1;
                        for(int i=1; i<=(int)op2; i++){
                            resultado = resultado * (int)op1;
                        }*/
                        return (int)Math.pow((int)op1,(int)op2); //regresa un int
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((int)op1, (double)op2); //regresa un double(decimal)
                    }
                    
                    
                    default -> {
                        return new Errores("ERROR semantico", "potenciacion erronea", this.line, this.column);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2){
                    case ENTERO ->{
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((double)op1,(int)op2); //devuelve un double
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((double)op1,(double)op2); // devuelve un double
                    }
                    
                    
                    
                    default -> {
                        return new Errores("ERROR semantico", "potenciacion erronea", this.line, this.column);
                    }
                }
            }
            
            
            default -> {
                return new Errores("ERROR semantico", "potenciacion erronea", this.line, this.column);
            }
        }
        
        
    }
    
    public Object modulo(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch(tipo1) {
            case ENTERO -> {
                switch (tipo2){
                    case ENTERO ->{
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        /*int resultado=1;
                        for(int i=1; i<=(int)op2; i++){
                            resultado = resultado * (int)op1;
                        }*/
                        return (double)((int)op1 % (int)op2); //regresa un int
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return  (int)op1 % (double)op2; //regresa un double(decimal)
                    }
                    
                    
                    default -> {
                        return new Errores("ERROR semantico", "operacion modulo erronea", this.line, this.column);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2){
                    case ENTERO ->{
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 % (int)op2; //devuelve un double
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 % (double)op2; // devuelve un double
                    }
                    
                    
                    
                    default -> {
                        return new Errores("ERROR semantico", "operacion modulo erronea", this.line, this.column);
                    }
                }
            }
            
            
            default -> {
                return new Errores("ERROR semantico", "operacion modulo erronea", this.line, this.column);
            }
        }
        
        
    }
    
    public Object negacion(Object op1){
        var opU = this.operandoUnico.tipo.getTipo();
        switch (opU){
            case ENTERO->{
                this.tipo.setTipo(tipoDato.ENTERO);
                int resultado = (int) op1 * -1;

                return resultado;
            }
            case DECIMAL ->{
                this.tipo.setTipo(tipoDato.DECIMAL);
                return (double) op1 * -1;
            }
            default ->{
               return new Errores("ERROR semantico", "negacion erronea", this.line, this.column);
            }
        }
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();

        
        arbol.addAST("n" + nodo + "[label=\"" + operaciones + "\"];\n");

        
        if (operandoUnico != null) {
            int h = operandoUnico.generarAST(arbol);
            arbol.addAST("n" + nodo + " -> n" + h + ";\n");
            return nodo;
        }

        
        int h1 = operando1.generarAST(arbol);
        int h2 = operando2.generarAST(arbol);

        arbol.addAST("n" + nodo + " -> n" + h1 + ";\n");
        arbol.addAST("n" + nodo + " -> n" + h2 + ";\n");

        return nodo;
    }

}
