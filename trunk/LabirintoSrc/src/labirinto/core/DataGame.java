/*
 * Data.java
 * 
 * Created on Aug 26, 2007, 8:17:36 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto.core;

/**
 *
 * @author das
 */
public class DataGame {
    
    private float x;
    private float y;
    
    private boolean keyVector[];

    public DataGame() {
    }
    
    public byte[] getBytes() {
        
        //@TODO: tem q inventar um jeito de converter esses atributos em um byte[]
        // e depois voltar os valores assim como em DataChat.
        byte byteVector[] = new byte[8];
        
        return byteVector;
    }

}
