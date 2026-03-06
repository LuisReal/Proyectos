
package expresiones;
import abstracto.Instruccion;
import simbolo.*;
import excepciones.Errores;

public class Relacionales extends Instruccion {
    private Instruccion cond1;
    private Instruccion cond2;
    private OperadoresRelacionales relacional;

    public Relacionales(Instruccion cond1, Instruccion cond2, OperadoresRelacionales relacional, int linea, int columna) {
        super(new Tipo(tipoDato.BOOLEANO), linea, columna);
        this.cond1 = cond1;
        this.cond2 = cond2;
        this.relacional = relacional;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        //condicion izquierda
        var condIzq = this.cond1.interpretar(arbol, tabla);
        if (condIzq instanceof Errores) {
            return condIzq;
        }
        
        //condicion derecha
        var condDer = this.cond2.interpretar(arbol, tabla);
        if (condDer instanceof Errores) {
            return condDer;
        }

        return switch (relacional) {
            case IGUALACION ->
                this.igualacion(condIzq, condDer);
            case DIFERENTE ->
                this.diferenciacion(condIzq, condDer);
            case MENORQUE ->
                this.menorque(condIzq, condDer);
            case MAYORQUE ->
                this.mayorque(condIzq, condDer);
            case MENORIGUAL ->
                this.menorIgual(condIzq, condDer);
            case MAYORIGUAL ->
                this.mayorIgual(condIzq, condDer);
            default ->
                new Errores("SEMANTICO", "Relacional Invalido", this.line, this.column);
        };
    }

    public Object igualacion(Object comp1, Object comp2) {
        var tipo1 = this.cond1.tipo.getTipo();
        var tipo2 = this.cond2.tipo.getTipo();

        switch (tipo1) { // retorna true o false en minusculas
            case ENTERO ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (int) comp1 == (int) comp2;
                    }case DECIMAL ->{
                        return (int) comp1 == (double) comp2;
                    }case CARACTER ->{
                        return (int) comp1 == (int)(char) comp2;
                    }default ->{
                        return new Errores("SEMANTICO", "Igualacion Invalido", this.line, this.column);}
                }
            }
            case DECIMAL ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (double) comp1 == (int) comp2;
                    }case DECIMAL ->{
                        return (double) comp1 == (double) comp2;
                    }case CARACTER ->{
                        return (double) comp1 == (double)(char) comp2;
                    }default ->{
                        return  new Errores("SEMANTICO", "Igualacion Invalido", this.line, this.column);}
                }
            }
            case CARACTER ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (int)(char) comp1 == (int) comp2;
                    }case DECIMAL ->{
                        return (double)(char) comp1 == (double) comp2;
                    }
                    case CARACTER ->{
                        return (char)comp1 == (char)comp2;
                    }default ->{
                        return new Errores("SEMANTICO", "Igualacion Invalido", this.line, this.column);}
                }
            }
            case CADENA ->{
                switch (tipo2) {
                    case CADENA ->{
                        return comp1.toString().equalsIgnoreCase(comp2.toString());
                    }default ->{
                        return new Errores("SEMANTICO", "Relacional Invalido", this.line, this.column);
                    }
                }
            }
            case BOOLEANO ->{
                switch (tipo2) {
                    case BOOLEANO ->{
                        return (boolean)comp1 == (boolean)comp2;
                    }default ->{
                        return new Errores("SEMANTICO", "Relacional Invalido", this.line, this.column);
                    }
                }
            }
            default ->{
                return new Errores("SEMANTICO", "Igualacion Invalido", this.line, this.column);}
        }
    }
    
    public Object diferenciacion(Object comp1, Object comp2) {
        var tipo1 = this.cond1.tipo.getTipo();
        var tipo2 = this.cond2.tipo.getTipo();

        switch (tipo1) {
            case ENTERO ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (int) comp1 != (int) comp2;
                    }case DECIMAL ->{
                        return (int) comp1 != (double) comp2;
                    }case CARACTER ->{
                        return (int) comp1 != (int)(char) comp2;
                    }default ->{
                        return new Errores("SEMANTICO", "Diferenciacion Invalido", this.line, this.column);}
                }
            }
            case DECIMAL ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (double) comp1 != (int) comp2;
                    }case DECIMAL ->{
                        return (double) comp1 != (double) comp2;
                    }case CARACTER ->{
                        return (double) comp1 != (double)(char) comp2;
                    }default ->{
                        return  new Errores("SEMANTICO", "Diferenciacion Invalido", this.line, this.column);}
                }
            }
            case CARACTER ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (int)(char) comp1 != (int) comp2;
                    }case DECIMAL ->{
                        return (double)(char) comp1 != (double) comp2;
                    }
                    case CARACTER ->{
                        return (char)comp1 != (char)comp2;
                    }default ->{
                        return new Errores("SEMANTICO", "Diferenciacion Invalido", this.line, this.column);}
                }
            }
            default ->{
                return new Errores("SEMANTICO", "Diferenciacion Invalido", this.line, this.column);}
        }
    }

    public Object menorque(Object comp1, Object comp2) {
        var tipo1 = this.cond1.tipo.getTipo();
        var tipo2 = this.cond2.tipo.getTipo();

        switch (tipo1) {
            case ENTERO ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (int) comp1 < (int) comp2;
                    }case DECIMAL ->{
                        return (int) comp1 < (double) comp2;
                    }case CARACTER ->{
                        return (int) comp1 < (int)(char) comp2;
                    }default ->{
                        return new Errores("SEMANTICO", "Menorque Invalido(ENTERO)", this.line, this.column);}
                }
            }
            case DECIMAL ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (double) comp1 < (int) comp2;
                    }case DECIMAL ->{
                        return (double) comp1 < (double) comp2;
                    }case CARACTER ->{
                        return (double) comp1 < (double)(char) comp2;
                    }default ->{
                        return  new Errores("SEMANTICO", "Menorque Invalido(DECIMAL)", this.line, this.column);}
                }
            }
            case CARACTER ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (int)(char) comp1 < (int) comp2;
                    }case DECIMAL ->{
                        return (double)(char) comp1 < (double) comp2;
                    }
                    case CARACTER ->{
                        return (char)comp1 < (char)comp2;
                    }default ->{
                        return new Errores("SEMANTICO", "Menorque Invalido(CARACTER)", this.line, this.column);}
                }
            }
            default ->{
                return new Errores("SEMANTICO", "Menorque invalido(DEFAULT)", this.line, this.column);}
        }
    }
    
    public Object mayorque(Object comp1, Object comp2) {
        var tipo1 = this.cond1.tipo.getTipo();
        var tipo2 = this.cond2.tipo.getTipo();

        switch (tipo1) {
            case ENTERO ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (int) comp1 > (int) comp2;
                    }case DECIMAL ->{
                        return (int) comp1 > (double) comp2;
                    }case CARACTER ->{
                        return (int) comp1 > (int)(char) comp2;
                    }default ->{
                        return new Errores("SEMANTICO", "Mayorque Invalido", this.line, this.column);}
                }
            }
            case DECIMAL ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (double) comp1 > (int) comp2;
                    }case DECIMAL ->{
                        return (double) comp1 > (double) comp2;
                    }case CARACTER ->{
                        return (double) comp1 > (double)(char) comp2;
                    }default ->{
                        return  new Errores("SEMANTICO", "Mayorque Invalido", this.line, this.column);}
                }
            }
            case CARACTER ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (int)(char) comp1 > (int) comp2;
                    }case DECIMAL ->{
                        return (double)(char) comp1 > (double) comp2;
                    }
                    case CARACTER ->{
                        return (char)comp1 > (char)comp2;
                    }default ->{
                        return new Errores("SEMANTICO", "Mayorque Invalido", this.line, this.column);}
                }
            }
            default ->{
                return new Errores("SEMANTICO", "Mayorque invaldo", this.line, this.column);}
        }
    }
    
    public Object menorIgual(Object comp1, Object comp2) {
        var tipo1 = this.cond1.tipo.getTipo();
        var tipo2 = this.cond2.tipo.getTipo();

        switch (tipo1) {
            case ENTERO ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (int) comp1 <= (int) comp2;
                    }case DECIMAL ->{
                        return (int) comp1 <= (double) comp2;
                    }case CARACTER ->{
                        return (int) comp1 <= (int)(char) comp2;
                    }default ->{
                        return new Errores("SEMANTICO", "MenorIgual Invalido", this.line, this.column);}
                }
            }
            case DECIMAL ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (double) comp1 <= (int) comp2;
                    }case DECIMAL ->{
                        return (double) comp1 <= (double) comp2;
                    }case CARACTER ->{
                        return (double) comp1 <= (double)(char) comp2;
                    }default ->{
                        return  new Errores("SEMANTICO", "MenorIgual Invalido", this.line, this.column);}
                }
            }
            case CARACTER ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (int)(char) comp1 <= (int) comp2;
                    }case DECIMAL ->{
                        return (double)(char) comp1 <= (double) comp2;
                    }
                    case CARACTER ->{
                        return (int)(char)comp1 <= (int)(char)comp2;
                    }default ->{
                        return new Errores("SEMANTICO", "MenorIgual Invalido", this.line, this.column);}
                }
            }
            default ->{
                return new Errores("SEMANTICO", "MenorIgual invaldo", this.line, this.column);}
        }
    }
    
    public Object mayorIgual(Object comp1, Object comp2) {
        var tipo1 = this.cond1.tipo.getTipo();
        var tipo2 = this.cond2.tipo.getTipo();

        switch (tipo1) {
            case ENTERO ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (int) comp1 >= (int) comp2;
                    }case DECIMAL ->{
                        return (int) comp1 >= (double) comp2;
                    }case CARACTER ->{
                        return (int) comp1 >= (int)(char) comp2;
                    }default ->{
                        return new Errores("SEMANTICO", "MayorIgual Invalido", this.line, this.column);}
                }
            }
            case DECIMAL ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (double) comp1 >= (int) comp2;
                    }case DECIMAL ->{
                        return (double) comp1 >= (double) comp2;
                    }case CARACTER ->{
                        return (double) comp1 >= (double)(char) comp2;
                    }default ->{
                        return  new Errores("SEMANTICO", "MayorIgual Invalido", this.line, this.column);}
                }
            }
            case CARACTER ->{
                switch (tipo2) {
                    case ENTERO ->{
                        return (int)(char) comp1 >= (int) comp2;
                    }case DECIMAL ->{
                        return (double)(char) comp1 >= (double) comp2;
                    }
                    case CARACTER ->{
                        return (int)(char)comp1 >= (int)(char)comp2;
                    }default ->{
                        return new Errores("SEMANTICO", "MayorIgual Invalido", this.line, this.column);}
                }
            }
            default ->{
                return new Errores("SEMANTICO", "MayorIgual invaldo", this.line, this.column);}
        }
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();

        arbol.addAST("n" + nodo + "[label=\"" + relacional + "\"];\n");

        int h1 = cond1.generarAST(arbol);
        int h2 = cond2.generarAST(arbol);

        arbol.addAST("n" + nodo + " -> n" + h1 + ";\n");
        arbol.addAST("n" + nodo + " -> n" + h2 + ";\n");

        return nodo;
    }
}
