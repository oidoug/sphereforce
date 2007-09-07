/*
 * Pedra.java
 *
 * Created on September 6, 2007, 9:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package labirinto.core;

/**
 *
 * @author r
 */
import labirinto.*;
import java.awt.*;
import java.applet.*;
/**
 *
 * @author r
 */
public class Pedra {
    
    private Image pedraImage;
    private int x, y;
    private float raio;
    
    /** Creates a new instance of Buraco */
    public Pedra(Image bah, int posx, int posy) {
        x = posx;
        y = posy;
        pedraImage = bah;
        
        raio = 10;
    }
    public void setRaio(float valor){
        raio = valor;
    }

    public float getRaio(){
        return raio;
    }
    public void setXY(int valorx, int valory){
        x = valorx;
        y = valory;
    }
    public float getX(){
        return x;
    }
    
    public float getY(){
        return y;
    }
        
    public void paint(Graphics g) {
        g.drawImage(pedraImage, (int) x, (int) y, null);
    }
    
    public boolean colideCom(Parede parede) {
        if (    (y >= parede.getY() - 2*raio) && 
                (y <= parede.getY() + parede.getAbsTamanhoH()) &&
                (x >= parede.getX() -2*raio ) && 
                (x <= parede.getX() + parede.getAbsTamanhoW()  )     )  
            return true;
        else
            return false;
    }
    
    public boolean colideCom(Marca marca) {
        if (    (y >= marca.getY() - 2*raio) && 
                (y <= marca.getY() + marca.getH()) &&
                (x >= marca.getX() -2*raio ) && 
                (x <= marca.getX() + marca.getW()  )     )  
            return true;
        else
            return false;
    }
}