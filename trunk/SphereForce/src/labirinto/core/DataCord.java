/*
 * DataCord.java
 *
 * Created on September 8, 2007, 3:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package labirinto.core;

import java.io.Serializable;
/**
 *
 * @author r
 */
public class DataCord implements Serializable {
    
    /** Creates a new instance of DataCord */
    
    private float x;
    private float y;
    
    public DataCord() {
    }
    
    public void setXY(float valorx, float valory){
        x = valorx;
        y = valory;
    }
    
    public float getX(){
        return x;
    }
    
    public float getY() {
        return y;
    }
}
