/*
 * Esfera.java
 *
 * Created on April 22, 2007, 11:55 AM
 */

package labirinto;

import java.awt.*;
import java.applet.*;


public class Esfera {
    
    private float x, y;

    private float velX;
    private float velY;
    
    /** Creates a new instance of Esfera */
    public Esfera() {
        x = y = 100;
        velX = velY = 0;
    }
    
    /** Gets for the Sphere atributs */
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    
    /** Refresh all the sphere contents,
     *  geting the new volocity and axis
     */
    public void refresh(boolean keyVector[]) {
        
        if(keyVector[Main.UP])      velY -= Main.ACELER;
        if(keyVector[Main.DOWN])    velY += Main.ACELER;
        if(keyVector[Main.LEFT])    velX -= Main.ACELER;
        if(keyVector[Main.RIGHT])   velX += Main.ACELER;
        if(keyVector[Main.ESCAPE]) { x = y = 0;
        }
        //System.out.println(velX + "\t" + velY);
        
        //Refresh new positions
        x += velX;
        y += velY;
        
        //Active "Atrito" constant
        velX *= Main.ATRITO;
        velY *= Main.ATRITO;
    }
    
}
