/*
 * FALLOS:        1.- CORREGIR ERROR ENTRADA ERRONEA POR TECLADO  INT/CHAR  
 * DETECTADOS     2.- ANADIR UN FINAL SCORE 
 *                3.- CAMBIAR AMPLITUD TABLERO
 *                4.-     
 *
 *  MEJORAS
 *  REALIZADAS  1.- AL CAMBIAR EL LOGO DE LA FICHA DE UN PLAYER DURANTE EL JUEGO, EL TABLERO MANTIENE LAS FICHAS YA COLOCADAS CON EL LOGO VIEJO.
 *              2.- TABLERO PERSONALIZABLE
 *              3.- MARCADOR
 */
package conecta4v6;
import java.util.*;

/**
 *
 * @author venturas
 * 
 * El array "config" y "confign" y configanterior" son arrays de 5 elementos que contienen 
 * En config[0] la puntuacion maxima para la partida
 * En config[1] el caracter para el player 1
 * En config[2] el caracter para el player 2
 * en config[3] el caracter para los espacios vacios sin ficha 
 * En config[4] el caracter para separar el tablero.
 * 
 */
public class Conecta4v6 {
    public static void main(String[] args) {  //PLAYER 1 ES X. PLAYER 2 ES 0.
        Scanner entrada = new Scanner(System.in);
        char[][] tablero = new char[6][7];
        int[][] indices= new int[4][2];                     // Donde se almacenaran los indices que quiero eliminar es una matriz de 4x2. 4 pares de puntos.
        int[] marcador= new int[2];                         //marcador[0] es player 1 y marcador[1] es Payer2
        char []config=new char[5];                          //creamos un vector de configuracion para char
        int []confign=new int[2];                           //creamos un vector de configuracion para numeros/int
        char []configanterior=new char[5];                  // vector de configuracion auxiliar para modificaciones
        char []factorysettings={' ','X','O',' ','|' };      //[1] Logo player1, indica el simbolo para la ficha del player 1.  [2] Logo player2, indica el simbolo para la ficha del player 2.    [3] Background, indica el fondo del tablero.    [4] Border, indica tipo de borde entre columnas.    [0] Reserva.
        int []factorysettingsn={2,0};                        // [0] indica la puntuacion limite para ganar la partida, [1] es de reserva.
        System.arraycopy(factorysettings,0,config,0,factorysettings.length);    //reestablecemos ajustes de fabrica
        System.arraycopy(factorysettingsn,0,confign,0,factorysettingsn.length); //reestablecemos ajustes de fabrica
        boolean newgame;
        int ganador;
        int columna;                                        //podria definirse como short
        int turno=1;                                      // Siempre empieza el jugador 1. Tiene turno el jugador 1. Valdrá 2 para el jugador dos. Podria ser un short.
    
    do{ 
    iniciar(tablero,config[3]);              //iniciamos tablero
    marcador[0]=0;                          //iniciamos marcadores
    marcador[1]=0;                          //iniciamos marcadores
    System.out.println("BIENVENIDO A CONECTA4 V6.0");
    System.out.println("Puede pulsar 9 en cualquier momento para acceder al menu de configuracion");
    System.out.println("El primero en alcanzar " + confign[0] + " puntos gana la partida");

    while(llena(tablero,config)==false && marcador[0]<confign[0] && marcador[1]<confign[0]){
        
        mostrar(marcador,config,confign);
        mostrar(tablero,config);
        
        if(turno==1)
            System.out.println("TURNO del JUGADOR 1. Elija columna donde desee introducir ficha.");
        else        
            System.out.println("TURNO del JUGADOR 2. Elija columna donde desee introducir ficha.");
                
        do{
            columna=leernumero();
        
            if (columna==9){
                menu(config,confign,configanterior,tablero,factorysettings,factorysettingsn,marcador);
                 mostrar(marcador,config,confign);
                 mostrar(tablero,config); }
        }while(columna<1 || columna>7);    
       
        echaficha(tablero,columna,turno,config);
        
        if(turno==1)
            turno=2;
        else
            turno=1;
          
        ganador=comprobar(tablero,indices,config);
          
        switch(ganador){
                case 0:    //No pasa nada, pero sigue la partida, sigue en el bucle echando fichas...
                    break;
                case 1:
                    punto(ganador);
                    marcador[0]++;
                    mostrar(tablero,config);
                    System.out.println("A continuacion se eliminara el conecta4 logrado por el jugador " + ganador);
                    System.out.println("Pulse intro para continuar la partida");
                    entrada.nextLine();
                    eliminarconecta(tablero,indices,config);
                    break;
                case 2:
                    punto(ganador);
                    marcador[1]++;
                    mostrar(tablero,config);
                    System.out.println("A continuacion se eliminara el conecta4 logrado por el jugador " + ganador);
                    System.out.println("Pulse intro para continuar la partida");
                    entrada.nextLine();
                    eliminarconecta(tablero,indices,config);
                    break;
                default:  //Este caso no se dara nunca
                    break;
            }    
    }

    mostrar(marcador,config,confign);
        
    if(marcador[0]>marcador[1])
        ganador(1);
    else if(marcador[0]==marcador[1])
        System.out.println("HA HABIDO UN EMPATE!!");
    else
        ganador(2);        
    newgame=nuevapartida();
        
    }while(newgame);
    
        System.out.println("GRACIAS POR USAR CONNECTA4 V6.0");
}

    static int comprobar(char [][]matriz,int[][] indices, char []config){     //devolvera 0 si no hay ganador, 1 si gana el player 1, y 2 si gana el player 2
    
         
    for (int i=0;i<6;i++) //comprobamos horizontales
        for(int j=0;j<=3;j++){
             if(matriz[i][j]==matriz[i][j+1] && matriz[i][j+1]==matriz[i][j+2] && matriz[i][j+2]==matriz[i][j+3] && matriz[i][j]==config[1]){
                obtenerH(indices,i,j);      //se actualiza los indices donde esta el conecta4 que hay que eliminar
                return 1;}
             if(matriz[i][j]==matriz[i][j+1] && matriz[i][j+1]==matriz[i][j+2] && matriz[i][j+2]==matriz[i][j+3] && matriz[i][j]==config[2]){
                obtenerH(indices,i,j);      //se actualiza los indices donde esta el conecta4 que hay que eliminar
                return 2;}
        }
     
    for (int i=0;i<3;i++)   // comprobamos verticales
        for (int j=0;j<=6;j++){
             if(matriz[i][j]==matriz[i+1][j] && matriz[i+1][j]==matriz[i+2][j] && matriz[i+2][j]==matriz[i+3][j] && matriz[i][j]==config[1]){
                obtenerV(indices,i,j);      //se actualiza los indices donde esta el conecta4 que hay que eliminar
                return 1;}
             if(matriz[i][j]==matriz[i+1][j] && matriz[i+1][j]==matriz[i+2][j] && matriz[i+2][j]==matriz[i+3][j] && matriz[i][j]==config[2]){
                obtenerV(indices,i,j);      //se actualiza los indices donde esta el conecta4 que hay que eliminar
                return 2;}
         }
        
    for(int i=0;i<3;i++)        //comprobamos diagonales en direccion derecha y abajo (o hacia izquierda y arriba)
        for(int j=0;j<=3;j++){
            if(matriz[i][j]==matriz[i+1][j+1] && matriz[i+1][j+1]==matriz[i+2][j+2] && matriz[i+2][j+2]==matriz[i+3][j+3] && matriz[i][j]==config[1]){
                obtenerD(indices,i,j);      //se actualiza los indices donde esta el conecta4 que hay que eliminar
                return 1;}
            if(matriz[i][j]==matriz[i+1][j+1] && matriz[i+1][j+1]==matriz[i+2][j+2] && matriz[i+2][j+2]==matriz[i+3][j+3] && matriz[i][j]==config[2]){
                obtenerD(indices,i,j);
                return 2;}
        }
    
     for(int i=0;i<3;i++)  //comprobamos diagonales en direccion derecha y arriba (o hacia abajo y izquierda)
        for(int j=6;j>=3;j--){
            if(matriz[i][j]==matriz[i+1][j-1] && matriz[i+1][j-1]==matriz[i+2][j-2] && matriz[i+2][j-2]==matriz[i+3][j-3] && matriz[i][j]==config[1]){   //hay 4 en raya del jugador 1
                obtenerI(indices,i,j);      //se actualiza los indices donde esta el conecta4 que hay que eliminar despues
                return 1;}
            if(matriz[i][j]==matriz[i+1][j-1] && matriz[i+1][j-1]==matriz[i+2][j-2] && matriz[i+2][j-2]==matriz[i+3][j-3] && matriz[i][j]==config[2]){   //hay 4 en raya del jugador 2
                obtenerI(indices,i,j);      //se actualiza los indices donde esta el conecta4 que hay que eliminar despues
                return 2;}
        }
     

     
     return 0;  // Todavia no hay ganador y se puede seguir echando fichas.

    }

    static void echaficha(char[][]matriz, int columna, int player,char []config){   //SE INDICA LA COLUMNA DONDE SE ECHA LA FICHA, DE 1 A 7, MAS INTUITIVO PARA EL JUGADOR
        columna=columna-1;                                                          //SE TRADUCE AL INDICE DE COLUMNA, QUE AL EMPEZAR EN 0 (POCO INTUITIVO PARA EL JUGADOR) SE RESTA 1.
        char ficha;
        boolean llena=false;
     
        if (matriz[0][columna]==config[1] || matriz[0][columna]==config[2])
            llena=true;
        
        while(llena==true){  
            System.out.println("Columna llena. Por favor introduzca un numero de columna valido jugador " + player);
            leercolumna();
            columna=columna-1;                                                      // volvemos a traducir la nueva columna introducida
            if (matriz[0][columna]==config[1] || matriz[0][columna]==config[2])     //volvemos a comprobar la columna
                llena=true;                                                         //si esta llena sigue true
            else
                llena=false;                                                        //corregimos valor si no esta llena
        }
     
        if (player==1)
             ficha=config[1];
        else
            ficha=config[2];
        for(int i=5;i>=0;i--)                           //EMPIEZA DESDE ABAJO Y
            if(matriz[i][columna]==config[3]){          //CUANDO ENCUENTRE HUECO, 
                matriz[i][columna]=ficha;               //PLANTA UNA X O UN 0 SEGUN PROCEDA            
                break;}                                 //Y SE SALE DEL FOR
   }    

    static void iniciar(char [][]matriz,char config){
        for (int i=0;i<matriz.length;i++) 
            for (int j = 0; j<matriz[0].length; j++) 
                matriz[i][j]=config;}
     
    static void mostrar(char [][]matriz,char []config){
        for (int i=0;i<matriz.length;i++) {
            System.out.print("          ");
            for (int j = 0; j<matriz[0].length; j++) 
                System.out.print(config[4]+""+matriz[i][j]);   //sin "" suma 156= 124+32
            System.out.println(config[4]);   
        }
        System.out.println("          ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
        System.out.println("           1 2 3 4 5 6 7");   
        System.out.println("");
    }
       
    static void mostrar(int[] marcador,char []config,int []confign){
        System.out.println("-----------------------------------");
        System.out.print("|    "+config[1]+"         SCORE        "+config[2]+"     |");
        System.out.println("   First player to reach "+confign[0]+" points wins");
        System.out.println("|  PLAYER 1    " + marcador[0] + " : " + marcador[1] + "    PLAYER 2  |");
        System.out.println("-----------------------------------");
       }
 
    static boolean nuevapartida(){
       Scanner entrada = new Scanner(System.in);
       char c;
       System.out.println("Si desea jugar otra partida pulse la tecla 'S'");
       
       c = entrada.next().charAt(0);
       
        if (c=='S' || c=='s')
               return true;
        else
               return false;
  }

    static void eliminarconecta(char[][] matriz,int[][] indices,char []config){
        for(int j=0;j<4;j++){
            for(int i=indices[j][0];i>0;i--)
                matriz[i][indices[j][1]]=matriz[i-1][indices[j][1]];  //me baja las fichas donde hay conecta4
            matriz[0][indices[j][1]]=config[3];   // Me rellena el hueco de la fila superior.
       }}  
       
    static void obtenerD(int[][] conecta4,int i, int j){
           conecta4[0][0]=i; conecta4[0][1]=j;
           conecta4[1][0]=i+1; conecta4[1][1]=j+1;
           conecta4[2][0]=i+2; conecta4[2][1]=j+2;
           conecta4[3][0]=i+3; conecta4[3][1]=j+3;
       }
    
    static void obtenerI(int[][] conecta4,int i, int j){
           conecta4[0][0]=i; conecta4[0][1]=j;
           conecta4[1][0]=i+1; conecta4[1][1]=j-1;
           conecta4[2][0]=i+2; conecta4[2][1]=j-2;
           conecta4[3][0]=i+3; conecta4[3][1]=j-3;
       }
    
    static void obtenerH(int[][] conecta4,int i, int j){
           conecta4[0][0]=i; conecta4[0][1]=j;
           conecta4[1][0]=i; conecta4[1][1]=j+1;
           conecta4[2][0]=i; conecta4[2][1]=j+2;
           conecta4[3][0]=i; conecta4[3][1]=j+3;
       }
    
    static void obtenerV(int[][] conecta4,int i, int j){
           conecta4[0][0]=i; conecta4[0][1]=j;
           conecta4[1][0]=i+1; conecta4[1][1]=j;
           conecta4[2][0]=i+2; conecta4[2][1]=j;
           conecta4[3][0]=i+3; conecta4[3][1]=j;
       }
            
    static boolean llena(char[][] matriz,char []config){
        if((matriz[0][0]==config[1] || matriz[0][0]==config[2]) && (matriz[0][1]==config[1] || matriz[0][1]==config[2]) && (matriz[0][2]==config[1] || matriz[0][2]==config[2]) && (matriz[0][3]==config[1] || matriz[0][3]=='O') && (matriz[0][4]==config[1] || matriz[0][4]==config[2]) && (matriz[0][5]==config[1] || matriz[0][5]==config[2]) && (matriz[0][6]==config[1] || matriz[0][6]==config[2]))    
            return true; // Matriz llena, no se puede seguir la partida  
        return false;
    }   
    
    static void menu(char []config,int []confign,char []configanterior,char [][]matriz, char []factorysettings, int []factorysettingsn,int []marcador){
        Scanner en = new Scanner(System.in);
        int opc;
        System.arraycopy(config,0,configanterior,0,config.length); // hago una copia de la configuracion anterior
        do{
        System.out.println("Menu de CONFIGURACION");
        System.out.println("1. Pulse 1 para cambiar el simbolo de la ficha del jugador 1. Actualmente: " +config[1]);
        System.out.println("2. Pulse 2 para cambiar el simbolo de la ficha del jugador 2. Actualmente: " +config[2]);
        System.out.println("3. Pulse 3 para cambiar la puntuacion necesaria para ganar (Max 9). Actualmente: " +confign[0]);
        System.out.println("4. Pulse 4 para cambiar el simbolo del fondo de pantalla. Actualmente: " +config[3]);
        System.out.println("5. Pulse 5 para cambiar el simbolo del borde de tablero. Actualmente: " +config[4]);
        System.out.println("6. Pulse 6 para reestablecer los valores de fabrica");
        System.out.println("7. Pulse 7 para acabar la partida.");
        System.out.println("9. Pulse 9 para volver al juego");
        
        opc=leernumero();
        
        switch(opc){
            case 1:
                  System.out.println("Introduzca nuevo simbolo de la ficha del jugador 1");
                config[1]=en.nextLine().charAt(0);actualizar(matriz,config,configanterior); break;
            case 2:
                System.out.println("Introduzca nuevo simbolo de la ficha del jugador 2");
                config[2]=en.nextLine().charAt(0);actualizar(matriz,config,configanterior); break;
            case 3:
                 System.out.println("Introduzca nueva puntuacion necesaria para ganar");
                confign[0]=leernumero(); break; 
            case 4:
                System.out.println("Introduzca nuevo fondo de tablero");
                config[3]=en.nextLine().charAt(0);actualizar(matriz,config,configanterior); break;
            case 5:
                System.out.println("Introduzca nuevo borde de tablero");
                config[4]=en.nextLine().charAt(0); break;
            case 6:
                System.arraycopy(factorysettings,0,config,0,factorysettings.length);    //reestablecemos ajustes de fabrica
                System.arraycopy(factorysettingsn,0,confign,0,factorysettingsn.length); //reestablecemos ajustes de fabrica
                actualizar(matriz,config,configanterior);
                System.out.println("Valores de fabrica reestablecidos"); break;
            case 7:                                                                     
                marcador[0]=confign[0];
                marcador[1]=confign[0];
                
            default:break;
        }
        }while(opc!=9);
           
    }
    
    static void actualizar(char [][]matriz,char []config,char []configanterior){
        for(int i=0;i<matriz.length;i++)
            for (int j=0;j<matriz[0].length;j++){
                if(matriz[i][j]==configanterior[1])
                    matriz[i][j]=config[1];
                if(matriz[i][j]==configanterior[2])
                    matriz[i][j]=config[2];
                if(matriz[i][j]==configanterior[3])
                    matriz[i][j]=config[3];}
    }
    
    static int leernumero(){
        Scanner entrada = new Scanner(System.in);
        String cadena;
        int entero;
        boolean flag=false;
        
        flag=false;
        do{
        if (flag==true)
            System.out.println("Numero no válido. Intentelo de nuevo.");
        
        cadena = entrada.nextLine();
        
        flag=true;
        }while(cadena.length()>1 || (cadena.equals("")) || cadena.matches("[^1-9]"));
        entero=Integer.parseInt(cadena);
        return entero;
}
    
    static int leercolumna(){
        int columna;
        do{
            columna=leernumero();
        }while(columna<1 || columna>7); 
        return columna;
    }
    
    static void punto(int jugador){
        System.out.println("******************************************");
        System.out.println("**        Punto para el jugador " + jugador + "!!     **");
        System.out.println("******************************************");

    }
    
    static void ganador(int jugador){
        System.out.println("");
        System.out.println("**********************************");
        System.out.println("**    HA GANADO EL JUGADOR " +jugador +"    **");
        System.out.println("**********************************");
    }
}