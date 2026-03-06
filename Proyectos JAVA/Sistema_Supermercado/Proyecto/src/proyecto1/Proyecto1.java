package proyecto1;

import javax.swing.JFrame;

public class Proyecto1 {

    public static ArregloUsuarios[] usuarios_nuevos = new ArregloUsuarios[3];

    public static void main(String[] args) {

        Login login = new Login();
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setVisible(true);
       
       /*double variable = 12.00000;
       
        System.out.println(variable);*/
        
           
        
        
       /* int[] edad = new int[15];
        edad[0] = 20;
        edad[1] = 18;
        edad[2] = 20;
        edad[3] = 18;
        edad[4] = 20;
        edad[5] = 32;
        edad[6] = 25;
        edad[7] = 32;
        edad[8] = 32;
        edad[9] = 25;
        edad[10] = 32;
        edad[11] = 25;
        edad[12] = 20;
        edad[13] = 80;
        edad[14] = 80;
        // 20 hay 4 ,  18 hay 2,  32 hay 4, 25 hay 3
        int[] contador = new int[101];
        int x = 0;

        for (int i = 0; i <edad.length; i++) {

            contador[edad[i]] += 1;

            //System.out.println("En la posicion "+i+" "+edad[i]);
        }

        for(int j=0; j<contador.length; j++){
            
            if((contador[j])!= 0){
            
            System.out.println("La edad "+j+" se repite: "+contador[j]+" veces");
            
            }
        }*/
    } // FIN DEL MAIN

}
