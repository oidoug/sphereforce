/*
 * Data.java
 * 
 * Created on Aug 26, 2007, 8:17:36 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto.core;

import java.nio.ByteBuffer;
import labirinto.Main;

/**
 *
 * @author das
 */
public class DataGame {
    
    private float x;
    private float y;
    
    private boolean keyVector[];

    public DataGame() {
        keyVector = new boolean[Main.NUM_OF_KEYS];
        x = -1;
        y = -1;
    }
    
    public byte[] getBytes() {
        
        //@TODO: tem q inventar um jeito de converter esses atributos em um byte[]
        // e depois voltar os valores assim como em DataChat.
        byte byteVector[] = new byte[8 + Main.NUM_OF_KEYS];
        
        byteVector[0] = new Float(x).byteValue();
        
        return byteVector;
    }
    
    /* setData recupera as informações do vetor de bytes para a estrutura de
     * dados DataGame instanciada
     */
    public void setData(byte byteVector[]) {
        
    }
    
    /* getX retorna o valor float de x no momento */
    public float getX() {
        return x;
    }
    
    /* getY retorna o valor float de y no momento */
    public float getY() {
        return y;
    }
    
    /* getKeyVector retorna o valor bool[] de keyVector no momento */
    public boolean[] getKeyVector() {
        return keyVector;
    }
    
    /**
     * Converte o vetor byte[] para um int a partir do valor offset dado
     *
     * @param b The byte array
     * @param offset The array offset
     * @return The integer
     */
    private static int byteArrayToInt(byte[] b, int offset) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }
}
