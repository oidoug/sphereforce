/*
 * Esfera.java
 *
 * Created on April 22, 2007, 11:55 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package labirinto;

import java.awt.*;
import java.applet.*;

/**
 *
 * @author das
 */
public class Esfera {
    
    private int x, y;

    private double velX;
    private double velY;
    
    /** Creates a new instance of Esfera */
    public Esfera() {
        x = y = 100;
        velX = velY = 0;
    }
    
    /** Gets for the Sphere atributs */
    public int getX() {
        return x;
    }
    public int getY() {
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
        System.out.println(velX + "\t" + velY);

        //Active "Atrito" constant
        //velX *= Main.ATRITO;
        //velY *= Main.ATRITO;
        
        //Refresh new positions
        x += velX;
        y += velY;
        
    }
    
}
