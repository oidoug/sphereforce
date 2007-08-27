/*
 * Esfera.java
 *
 * Created on April 22, 2007, 11:55 AM
 */

package labirinto.core;

import labirinto.*;
import java.awt.*;
import java.applet.*;


public class Esfera {
    
    private float x, y;

    private float velX;
    private float velY;
    
    private Image sphereImage;
    
    private Conection conn;
    
    /** Creates a new instance of Esfera */
    public Esfera(Image bah) {
        x = y = 100;
        velX = velY = 0;
        
        sphereImage = bah;
        Main.loading.addImage(sphereImage, 0);
        
        conn = new Conection();
    }
    
   
    /** Refresh all the sphere contents,
     *  geting the new volocity and axis
     */
    public void refresh(boolean keyVector[]) {
        
        if(keyVector[Main.UP])      velY -= Main.ACELER;
        if(keyVector[Main.DOWN])    velY += Main.ACELER;
        if(keyVector[Main.LEFT])    velX -= Main.ACELER;
        if(keyVector[Main.RIGHT])   velX += Main.ACELER;
        
        //Refresh new positions
        x += velX;
        y += velY;
        
        //Active "Atrito" constant
        velX *= Main.ATRITO;
        velY *= Main.ATRITO;
    }
    
    /** Refresh all the sphere content based on data received from socket
     *  connection
     */
    public void refresh() {
        refresh(conn.getKeys());
    }
    
    public void paint(Graphics g) {
        g.drawImage(sphereImage, (int) x, (int) y, null);
    }
    
}
