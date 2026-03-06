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
public class Casteo extends Instruccion {
    
    private Tipo tipo;
    private Instruccion expresion;
    
    public Casteo(Tipo tipo, Instruccion expresion, int linea, int columna) {
        
        super(tipo, linea, columna);
        this.tipo = tipo;
        this.expresion = expresion;
    }
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        
        var exp = this.expresion.interpretar(arbol, tabla);
        if (exp instanceof Errores){
            return exp;
        }
        
        tipoDato tipoOrigen = this.expresion.tipo.getTipo();
        Object valorOrigen = exp;
        Object valorConvertido = null;
        
        switch (tipo.getTipo()){
            case ENTERO ->{//entero, entero
                // Conversiones a INT
                if (tipoOrigen == tipoDato.DECIMAL) {
                    // double a int
                    //this.tipo = new Tipo(tipoDato.ENTERO);
                    valorConvertido = (int) ((double) valorOrigen);
                    
                } else if (tipoOrigen == tipoDato.CARACTER) {
                    // char a int (valor ASCII)
                    //this.tipo = new Tipo(tipoDato.ENTERO);
                    valorConvertido = (int) ((char) valorOrigen);
                    
                } else if (tipoOrigen == tipoDato.ENTERO) {
                    // int a int (sin conversión)
                    //this.tipo = new Tipo(tipoDato.ENTERO);
                    valorConvertido = valorOrigen;
                    
                } else {
                    return new Errores("SEMANTICO", "No se puede convertir " + tipoOrigen + " a int", this.line, this.column);
                }
                
            }
            case DECIMAL -> {//entero, decimal
                // Conversiones a DOUBLE
                if (tipoOrigen == tipoDato.ENTERO) {
                    // int a double
                    //this.tipo = new Tipo(tipoDato.DECIMAL);
                    valorConvertido = (double) ((int) valorOrigen);
                    
                } else if (tipoOrigen == tipoDato.CARACTER) {
                    // char a double
                    //this.tipo = new Tipo(tipoDato.DECIMAL);
                    valorConvertido = (double) ((char) valorOrigen);
                    
                } else if (tipoOrigen == tipoDato.DECIMAL) {
                    // double a double (sin conversión)
                    //this.tipo = new Tipo(tipoDato.DECIMAL);
                    valorConvertido = valorOrigen;
                    
                } else {
                    return new Errores("SEMANTICO", "No se puede convertir " + tipoOrigen + " a double", this.line, this.column);
                }
            }
            case CARACTER -> {//entero, caracter
                // Conversiones a CHAR
                if (tipoOrigen == tipoDato.ENTERO) {
                    // int a char
                    int valor = (int) valorOrigen;
                    if (valor < 0 || valor > 65535) {
                        return new Errores("SEMANTICO", "Valor fuera de rango para char (0-65535)", this.line, this.column);
                    }
                    //this.tipo = new Tipo(tipoDato.CARACTER);
                    valorConvertido = (char) valor;
                    
                } else if (tipoOrigen == tipoDato.CARACTER) {
                    // char a char (sin conversión)
                    //this.tipo = new Tipo(tipoDato.CARACTER);
                    valorConvertido = valorOrigen;
                    
                } else {
                    return new Errores("SEMANTICO", "No se puede convertir " + tipoOrigen + " a char", this.line, this.column);
                }
            }
            case CADENA ->{ //entero, cadena
                // Conversiones a STRING
                if (tipoOrigen == tipoDato.ENTERO) {
                    // int a string
                    //this.tipo = new Tipo(tipoDato.CADENA);
                    valorConvertido = String.valueOf((int) valorOrigen);
                    
                } else if (tipoOrigen == tipoDato.DECIMAL) {
                    // double a string
                    //this.tipo = new Tipo(tipoDato.CADENA);
                    valorConvertido = String.valueOf((double) valorOrigen);
                    
                } else if (tipoOrigen == tipoDato.CADENA) {
                    // string a string (sin conversión)
                    //this.tipo = new Tipo(tipoDato.CADENA);
                    valorConvertido = valorOrigen;
                    
                } else {
                    return new Errores("SEMANTICO", "No se puede convertir " + tipoOrigen + " a string", this.line, this.column);
                }

            }
            default -> {
                return new Errores("ERROR semantico", "tipo de casteo no valido", this.line, this.column);
            }
        }
        
       

        this.tipo = tipo; // actualizar tipo del casteo
        return valorConvertido;
    }
    
    @Override
    public int generarAST(Arbol arbol) {

        int nodo = arbol.getContador();

        arbol.addAST("n" + nodo + "[label=\"CASTEO\"];\n");

        int hijo = expresion.generarAST(arbol);
        arbol.addAST("n" + nodo + " -> n" + hijo + ";\n");

        return nodo;
    }
}
