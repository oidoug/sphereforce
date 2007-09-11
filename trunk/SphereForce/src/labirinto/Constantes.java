/*
 * Constantes.java
 * 
 * Created on Sep 6, 2007, 4:27:34 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto;

/**
 *
 * @author Douglas Schmidt
 */
public class Constantes {
    
    public static final int TAMANHO_BLOCO = 20;
    
    public static final int TAMANHO_RAIO_ESFERAS = 15;
    
    public static final int BACKGROUND_W = 50;
    public static final int BACKGROUND_H = 50;
    
    
    /** ponto de inicio das esferas, depende de onde coloca-se o inicio */
    public static final int SPHERE_INIT_POINT_X = 50;
    public static final int SPHERE_INIT_POINT_Y = 25;
    
    /** usado para atualizar posicao e velocidade de uma esfera remota com seus
     * dados reais remotos
     */
    public static final int REFRESH_SPHERE_TIME = 5;
    
    public static final int DELAY = 33;
    public static final int GET_SET_TIME = 20;
    public static final int DIVISOES_CANVAS = 2;
    
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    
    
    /** CONSTANTES PARA TRATAR TAMANHO DE IMAGENS */
    public static final int MARCA_W = 100;
    public static final int MARCA_H = 50;
    
    public static final int BOTTON_W = 200;
    public static final int BOTTON_H = 40;
    
    /* CONSTANTE PARA CHECAGEM DE TIPO DE CONEXAO */
    public static final int TCP_CON = 0;
    public static final int UDP_CON = 1;
    
    public static final int MAX_INPUT_CHAR = 30;
    public static final int MAX_OUTPUT_LIST_SIZE = 8;
    
    public static final int CHAT_STRING_INIT_X = 250;
    public static final int CHAT_STRING_OUTPUT_INIT_Y = 200;
    public static final int CHAT_STRING_INPUT_INIT_Y = 360;
    
    public static final int CHAT_STRING_OUTPUT_SPACELINE = 15;
    
    public static final int CHAT_START = 0;
    public static final int CHAT_STOP = 1;
    public static final int CHAT_CLIENT = 2;
    public static final int CHAT_SERVER = 3;
    
    public Constantes() {
    }
    

}
