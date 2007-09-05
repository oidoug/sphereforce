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
import java.io.Serializable;
import labirinto.Main;

/**
 *
 * @author das
 */
public class DataGame implements Serializable {
    
    private float x;
    private float y;
    
    private boolean keyVector[];

    public DataGame() {
        keyVector = new boolean[Main.NUM_OF_KEYS];
        x = -1;
        y = -1;
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
    
    public void setKeyVector(boolean[] vector){
        keyVector = vector;
    }
    
}
