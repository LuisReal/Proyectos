/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Analisis;

/**
 *
 * @author Lesther
 */
public class Generador {
    public static void main(String[] args){
        try{    
          
         
           String ruta = "src/Analisis/";
          
           
           String jFlex[] = {ruta + "lexico.jflex", "-d", ruta};
           
           jflex.Main.generate(jFlex);
           
           String Cup[] = {"-destdir", ruta, "-parser", "parser", ruta + "sintactico.cup"};
           // genera parser.java y sym.java
           java_cup.Main.main(Cup);
           
           
           
          
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
